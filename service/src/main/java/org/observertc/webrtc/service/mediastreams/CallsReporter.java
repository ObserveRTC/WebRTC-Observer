package org.observertc.webrtc.service.mediastreams;

import io.micronaut.context.annotation.Prototype;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.Punctuator;
import org.javatuples.Pair;
import org.observertc.webrtc.common.reports.DetachedPeerConnectionReport;
import org.observertc.webrtc.common.reports.FinishedCallReport;
import org.observertc.webrtc.common.reports.InitiatedCallReport;
import org.observertc.webrtc.common.reports.JoinedPeerConnectionReport;
import org.observertc.webrtc.service.dto.webextrapp.RTCStats;
import org.observertc.webrtc.service.micrometer.ObserverSSRCPeerConnectionSampleProcessReporter;
import org.observertc.webrtc.service.model.CallPeerConnectionsEntry;
import org.observertc.webrtc.service.model.PeerConnectionSSRCsEntry;
import org.observertc.webrtc.service.repositories.CallPeerConnectionsRepository;
import org.observertc.webrtc.service.repositories.PeerConnectionSSRCsRepository;
import org.observertc.webrtc.service.samples.MediaStreamKey;
import org.observertc.webrtc.service.samples.ObserveRTCMediaStreamStatsSample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Prototype
public class CallsReporter implements Punctuator {

	private static final Logger logger = LoggerFactory.getLogger(CallsReporter.class);

	private ProcessorContext context;

	private final PeerConnectionSSRCsRepository peerConnectionSSRCsRepository;
	private final CallPeerConnectionsRepository callPeerConnectionsRepository;
	private final Map<MediaStreamKey, Pair<LocalDateTime, LocalDateTime>> updates;
	private int peerConnectionMaxIdleTimeInS = 30; // default value

	//	private final Map<SSRCMapEntry, LocalDateTime> ssrcMapEntries;
	public CallsReporter(PeerConnectionSSRCsRepository peerConnectionSSRCsRepository,
						 CallPeerConnectionsRepository callPeerConnectionsRepository,
						 ObserverSSRCPeerConnectionSampleProcessReporter observerSSRCPeerConnectionSampleProcessReporter) {
		this.peerConnectionSSRCsRepository = peerConnectionSSRCsRepository;
		this.callPeerConnectionsRepository = callPeerConnectionsRepository;
		this.updates = new HashMap<>();
	}


	public void init(ProcessorContext context, MediaStreamEvaluatorConfiguration.CallReportsConfiguration configuration) {
		this.peerConnectionMaxIdleTimeInS = configuration.peerConnectionMaxIdleTimeInS;
		this.context = context;

	}

	/**
	 * Assumption: RTCStats cannot be null!
	 *
	 * @param peerConnectionUUID
	 * @param sample
	 */
	public void add(UUID peerConnectionUUID, ObserveRTCMediaStreamStatsSample sample) {
		RTCStats rtcStats = sample.rtcStats;
		if (rtcStats.getSsrc() == null) {
			logger.warn("Null SSRC appeared");
			return;
		}
		Long SSRC = sample.rtcStats.getSsrc().longValue();
		UUID observerUUID = sample.observerUUID;
		MediaStreamKey mediaStreamKey = MediaStreamKey.of(observerUUID, peerConnectionUUID, SSRC);

		Pair<LocalDateTime, LocalDateTime> sampled = this.updates.getOrDefault(mediaStreamKey, Pair.with(sample.sampled, sample.sampled));
		sampled.setAt1(sample.sampled);
		this.updates.put(mediaStreamKey, sampled);
	}

	/**
	 * This is the trigger method initiate the process of cleaning the calls
	 *
	 * @param timestamp
	 */
	@Override
	public void punctuate(long timestamp) {
		this.identifyCalls();
//		this.cleanCalls();

	}


	private void cleanCalls() {
		LocalDateTime threshold = LocalDateTime.now(ZoneOffset.UTC).minus(this.peerConnectionMaxIdleTimeInS, ChronoUnit.SECONDS);
		Iterable<PeerConnectionSSRCsEntry> expiredPeerConnectionSSRCsEntries = this.peerConnectionSSRCsRepository.findExpiredPeerConnections(threshold);
		Iterator<PeerConnectionSSRCsEntry> it = expiredPeerConnectionSSRCsEntries.iterator();
		Set<UUID> checkedPeerConnections = new HashSet<>();
		for (; it.hasNext(); ) {
			PeerConnectionSSRCsEntry entry = it.next();
			UUID peerConnectionUUID = entry.peerConnectionUUID;
			if (checkedPeerConnections.contains(peerConnectionUUID)) {
				continue;
			}
			checkedPeerConnections.add(peerConnectionUUID);
			Iterable<PeerConnectionSSRCsEntry> allStreams = this.peerConnectionSSRCsRepository.findEntries(peerConnectionUUID);
			Iterator<PeerConnectionSSRCsEntry> allStreamsIt = allStreams.iterator();
			boolean activeStream = false;
			for (; allStreamsIt.hasNext(); ) {
				PeerConnectionSSRCsEntry streamEntry = allStreamsIt.next();
				if (threshold.compareTo(streamEntry.updated) < 0) {
					activeStream = true;
					break;
				}
			}
			if (!activeStream) {
				this.reportExpiredPeerConnection(peerConnectionUUID, entry.observerUUID, entry.updated);
			}
		}
		this.peerConnectionSSRCsRepository.deleteAll(expiredPeerConnectionSSRCsEntries);
	}


	private void reportExpiredPeerConnection(UUID peerConnectionUUID, UUID observerUUID, LocalDateTime lastSampled) {
		AtomicBoolean callHasOtherPeers = new AtomicBoolean(false);
		AtomicReference<UUID> callUUIDHolder = new AtomicReference<>(null);
		this.callPeerConnectionsRepository.findPeers(peerConnectionUUID, callPeerConnectionsEntry -> {
			callUUIDHolder.set(callPeerConnectionsEntry.callUUID);
			if (!callPeerConnectionsEntry.peerConnectionUUID.equals(peerConnectionUUID)) {
				callHasOtherPeers.set(true);
			}
		});
		if (callHasOtherPeers.get() == false) {
			FinishedCallReport finishedCallReport = FinishedCallReport.of(observerUUID, callUUIDHolder.get(), lastSampled);
			this.context.forward(observerUUID, finishedCallReport);
		}
		DetachedPeerConnectionReport detachedPeerConnectionReport = DetachedPeerConnectionReport.of(observerUUID, callUUIDHolder.get(),
				peerConnectionUUID, lastSampled);
		this.context.forward(observerUUID, detachedPeerConnectionReport);
		CallPeerConnectionsEntry deleteCandidate = CallPeerConnectionsEntry.of(peerConnectionUUID, callUUIDHolder.get(), null);
		this.callPeerConnectionsRepository.delete(deleteCandidate);
	}

	/**
	 * Updates the table for SSRC to peer connections and
	 * returns with peerConnectionUUID, ObserverUUID entries for which
	 * peerConnectoin a callUUID is missing
	 *
	 * @return
	 */
	private void identifyCalls() {
		if (this.updates.size() < 1) {
			return;
		}
		Map<UUID, Pair<UUID, LocalDateTime>> updatedPeerConnections = new HashMap<>();
		Iterable<PeerConnectionSSRCsEntry> entities = () ->
				this.updates.entrySet().stream().map(entry -> {
					PeerConnectionSSRCsEntry mappedEntry = new PeerConnectionSSRCsEntry();
					MediaStreamKey mediaStreamKey = entry.getKey();
					Pair<LocalDateTime, LocalDateTime> sampled = entry.getValue();
					LocalDateTime firstUpdate = sampled.getValue0();
					LocalDateTime lastUpdate = sampled.getValue1();
					mappedEntry.observerUUID = mediaStreamKey.observerUUID;
					mappedEntry.peerConnectionUUID = mediaStreamKey.peerConnectionUUID;
					mappedEntry.SSRC = mediaStreamKey.SSRC;
					mappedEntry.updated = lastUpdate;
					updatedPeerConnections.put(mappedEntry.peerConnectionUUID, Pair.with(mappedEntry.observerUUID, firstUpdate));
					return mappedEntry;
				}).iterator();
		this.peerConnectionSSRCsRepository.saveAll(entities);

		this.peerConnectionSSRCsRepository.findCallUUIDs(updatedPeerConnections.keySet(),
				callPeerConnectionsEntry -> {
					if (callPeerConnectionsEntry.callUUID != null) {
						updatedPeerConnections.remove(callPeerConnectionsEntry.peerConnectionUUID);
						return;
					}
					UUID peerConnectionUUID = callPeerConnectionsEntry.peerConnectionUUID;
					Pair<UUID, LocalDateTime> pair = updatedPeerConnections.get(peerConnectionUUID);
					UUID observerUUID = pair.getValue0();
					LocalDateTime firstSampled = pair.getValue1();
					reportNewPeerConnection(peerConnectionUUID, observerUUID, firstSampled);
				});
	}

	private void reportNewPeerConnection(UUID peerConnectionUUID, UUID observerUUID, LocalDateTime firstSampled) {
		AtomicReference<UUID> callUUIDHolder = new AtomicReference<>(null);
		Set<UUID> peers = new HashSet<>();
		this.peerConnectionSSRCsRepository.findPeers(peerConnectionUUID, peerUUID -> {
			peers.add(peerUUID);
		});
		AtomicReference<LocalDateTime> firstSampleHolder = new AtomicReference<>(null);
		this.peerConnectionSSRCsRepository.findCallUUIDs(peers, callPeerConnectionsEntry -> {
			if (callPeerConnectionsEntry.updated != null) {
				if (firstSampleHolder.get() == null) {
					firstSampleHolder.set(callPeerConnectionsEntry.updated);
				} else if (callPeerConnectionsEntry.updated.compareTo(firstSampleHolder.get()) < 0) {
					firstSampleHolder.set(callPeerConnectionsEntry.updated);
				}
			}

			if (callPeerConnectionsEntry.callUUID == null) {
				return;
			}
			UUID callUUIDCandidate = callPeerConnectionsEntry.callUUID;
			if (callUUIDHolder.get() == null) {
				callUUIDHolder.set(callUUIDCandidate);
				return;
			}
			UUID selectedCallUUID = callUUIDHolder.get();
			if (!selectedCallUUID.equals(callUUIDCandidate)) {
				logger.warn("Different CallUUID is found ({}, {}) for peers belongs to the same Observer, SSRC", callUUIDCandidate,
						selectedCallUUID);
			}
		});
		UUID callUUID = callUUIDHolder.get();
		if (callUUID == null) {
			callUUID = UUID.randomUUID();
			CallPeerConnectionsEntry callPeerConnectionsEntry = CallPeerConnectionsEntry.of(peerConnectionUUID, callUUID,
					firstSampleHolder.get());
			InitiatedCallReport initiatedCallReport = InitiatedCallReport.of(observerUUID, callUUID, firstSampleHolder.get());
			this.callPeerConnectionsRepository.save(callPeerConnectionsEntry);
			this.context.forward(observerUUID, initiatedCallReport);
		}
		JoinedPeerConnectionReport joinedPeerConnectionReport = JoinedPeerConnectionReport.of(observerUUID, callUUID, peerConnectionUUID, firstSampled);
		this.context.forward(observerUUID, joinedPeerConnectionReport);
	}
}
