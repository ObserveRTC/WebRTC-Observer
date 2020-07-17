package org.observertc.webrtc.service.mediastreams;//package com.observertc.gatekeeper.webrtcstat.processors.samples;

import java.util.UUID;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.observertc.webrtc.service.dto.webextrapp.RTCStatsType;
import org.observertc.webrtc.service.samples.ObserveRTCMediaStreamStatsSample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MediaStreamDemuxer {
	private static final int DEFAULT_OUTPUT_INDEX = 2;
	private static final int OUTBOUND_STREAM_OUTPUT_INDEX = 1;
	private static final int INBOUND_STREAM_OUTPUT_INDEX = 0;

	private static final Logger logger = LoggerFactory.getLogger(MediaStreamDemuxer.class);


	private final KStream<UUID, ObserveRTCMediaStreamStatsSample> source;
	private final KStream<UUID, ObserveRTCMediaStreamStatsSample> inboundStream;
	private final KStream<UUID, ObserveRTCMediaStreamStatsSample> defaultStream;
	private final KStream<UUID, ObserveRTCMediaStreamStatsSample> outboundStream;
	private final KStream<UUID, ObserveRTCMediaStreamStatsSample> remoteInboundStream;

	private boolean logSampleIsDroppedMessage(String field, UUID peerConnectionUUID, ObserveRTCMediaStreamStatsSample sample) {
		logger.warn("Sample is dropped due to missing field: {}. Key: {}, Value: {}",
				field, peerConnectionUUID, sample);
		return false;
	}

	public MediaStreamDemuxer(KStream<UUID, ObserveRTCMediaStreamStatsSample> source) {
		this.source = source.filter(new Predicate<UUID, ObserveRTCMediaStreamStatsSample>() {
			@Override
			public boolean test(UUID peerConnectionUUID, ObserveRTCMediaStreamStatsSample sample) {
				if (peerConnectionUUID == null) {
					return logSampleIsDroppedMessage("peerConnectionUUID key", peerConnectionUUID, sample);
				}
				if (sample == null) {
					return logSampleIsDroppedMessage("Sample value", peerConnectionUUID, sample);
				}
				if (sample.observerUUID == null) {
					return logSampleIsDroppedMessage("sample.observerUUID", peerConnectionUUID, sample);
				}
				if (sample.sampled == null) {
					return logSampleIsDroppedMessage("sample.sampled timestamp", peerConnectionUUID, sample);
				}
				if (sample.rtcStats == null) {
					return logSampleIsDroppedMessage("sample.rtcStats", peerConnectionUUID, sample);
				}
				if (sample.rtcStats.getSsrc() == null) {
					return logSampleIsDroppedMessage("sample.rtcStats.SSRC", peerConnectionUUID, sample);
				}
				return true;
			}
		});
//		Predicate<UUID, ObserveRTCMediaStreamStatsSample>[] predicates = this.getPredicates();
		this.inboundStream = this.source.filter(new Predicate<UUID, ObserveRTCMediaStreamStatsSample>() {
			@Override
			public boolean test(UUID key, ObserveRTCMediaStreamStatsSample value) {
				if (value == null || value.rtcStats == null || value.rtcStats.getType() == null) {
					return false;
				}
				return value.rtcStats.getType().equals(RTCStatsType.INBOUND_RTP);
			}
		});

		this.outboundStream = this.source.filter(new Predicate<UUID, ObserveRTCMediaStreamStatsSample>() {
			@Override
			public boolean test(UUID key, ObserveRTCMediaStreamStatsSample value) {
				if (value == null || value.rtcStats == null || value.rtcStats.getType() == null) {
					return false;
				}
				return value.rtcStats.getType().equals(RTCStatsType.OUTBOUND_RTP);
			}
		});

		this.remoteInboundStream = this.source.filter(new Predicate<UUID, ObserveRTCMediaStreamStatsSample>() {
			@Override
			public boolean test(UUID key, ObserveRTCMediaStreamStatsSample value) {
				if (value == null || value.rtcStats == null || value.rtcStats.getType() == null) {
					return false;
				}
				return value.rtcStats.getType().equals(RTCStatsType.REMOTE_INBOUND_RTP);
			}
		});

		this.defaultStream = this.source.filter(new Predicate<UUID, ObserveRTCMediaStreamStatsSample>() {
			@Override
			public boolean test(UUID key, ObserveRTCMediaStreamStatsSample value) {
				return true;
			}
		});
	}

	public KStream<UUID, ObserveRTCMediaStreamStatsSample> getDefaultOutputBranch() {
		return this.defaultStream;
	}


	public KStream<UUID, ObserveRTCMediaStreamStatsSample> getOutboundStreamBranch() {
		return this.outboundStream;
	}

	public KStream<UUID, ObserveRTCMediaStreamStatsSample> getInboundStreamBranch() {
		return this.inboundStream;
	}

	public KStream<UUID, ObserveRTCMediaStreamStatsSample> getRemoteInboundStreamBranch() {
		return this.remoteInboundStream;
	}


//	private Predicate<UUID, ObserveRTCMediaStreamStatsSample>[] getPredicates() {
//		Predicate<UUID, ObserveRTCMediaStreamStatsSample>[] result = (Predicate<UUID, ObserveRTCMediaStreamStatsSample>[]) new Predicate<
//				?, ?>[3];
//		result[DEFAULT_OUTPUT_INDEX] = new Predicate<UUID, ObserveRTCMediaStreamStatsSample>() {
//			@Override
//			public boolean test(UUID key, ObserveRTCMediaStreamStatsSample value) {
//				return true;
//			}
//		};
//
//		result[OUTBOUND_STREAM_OUTPUT_INDEX] = new Predicate<UUID, ObserveRTCMediaStreamStatsSample>() {
//			@Override
//			public boolean test(UUID key, ObserveRTCMediaStreamStatsSample value) {
//				if (value == null || value.rtcStats == null || value.rtcStats.getType() == null) {
//					return false;
//				}
//				return value.rtcStats.getType().equals(RTCStatsType.OUTBOUND_RTP);
//			}
//		};
//
//		result[INBOUND_STREAM_OUTPUT_INDEX] = new Predicate<UUID, ObserveRTCMediaStreamStatsSample>() {
//			@Override
//			public boolean test(UUID key, ObserveRTCMediaStreamStatsSample value) {
//				if (value == null || value.rtcStats == null || value.rtcStats.getType() == null) {
//					return false;
//				}
//				return value.rtcStats.getType().equals(RTCStatsType.INBOUND_RTP);
//			}
//		};
//		return (Predicate<UUID, ObserveRTCMediaStreamStatsSample>[]) result;
//	}


}