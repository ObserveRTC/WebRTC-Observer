package org.observertc.webrtc.service.reportsink.bigquery;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.observertc.webrtc.common.reports.JoinedPeerConnection;

public class JoinedPeerConnectionEntry implements BigQueryEntry {

	public static JoinedPeerConnectionEntry from(JoinedPeerConnection joinedPeerConnection) {
		return new JoinedPeerConnectionEntry()
				.withObserverUUID(joinedPeerConnection.getObserverUUID())
				.withPeerConnectionUUID(joinedPeerConnection.getPeerConnectionUUID())
				.withCallUUID(joinedPeerConnection.getCallUUID())
				.withJoinedTimestamp(joinedPeerConnection.getTimestamp());
	}

	private static final String OBSERVER_UUID_FIELD_NAME = "observerUUID";
	private static final String PEER_CONNECTION_UUID_FIELD_NAME = "peerConnectionUUID";
	private static final String CALL_UUID_FIELD_NAME = "callUUID";
	private static final String JOINED_TIMESTAMP_FIELD_NAME = "joined";

	private final Map<String, Object> values;

	public JoinedPeerConnectionEntry() {
		this.values = new HashMap<>();
	}

	public JoinedPeerConnectionEntry withObserverUUID(UUID value) {
		this.values.put(OBSERVER_UUID_FIELD_NAME, value.toString());
		return this;
	}

	public JoinedPeerConnectionEntry withPeerConnectionUUID(UUID value) {
		this.values.put(PEER_CONNECTION_UUID_FIELD_NAME, value.toString());
		return this;
	}

	public JoinedPeerConnectionEntry withCallUUID(UUID value) {
		this.values.put(CALL_UUID_FIELD_NAME, value.toString());
		return this;
	}

	public JoinedPeerConnectionEntry withJoinedTimestamp(LocalDateTime value) {
		ZoneId zoneId = ZoneId.systemDefault();
		Long epoch = value.atZone(zoneId).toEpochSecond();
		this.values.put(JOINED_TIMESTAMP_FIELD_NAME, epoch);
		return this;
	}

	public UUID getObserverUUID() {
		String value = (String) this.values.get(OBSERVER_UUID_FIELD_NAME);
		if (value == null) {
			return null;
		}
		return UUID.fromString(value);
	}


	public UUID getCallUUID() {
		String value = (String) this.values.get(CALL_UUID_FIELD_NAME);
		if (value == null) {
			return null;
		}
		return UUID.fromString(value);
	}

	public UUID getPeerConnectionUUID() {
		String value = (String) this.values.get(PEER_CONNECTION_UUID_FIELD_NAME);
		if (value == null) {
			return null;
		}
		return UUID.fromString(value);
	}

	public LocalDateTime getJoinedTimestamp() {
		Long value = (Long) this.values.get(JOINED_TIMESTAMP_FIELD_NAME);
		if (value == null) {
			return null;
		}
		ZoneId zoneId = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), zoneId);
	}

	public Map<String, Object> toMap() {
		return this.values;
	}
}