package org.observertc.webrtc.reporter.bigquery;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.observertc.webrtc.common.reports.InitiatedCallReport;
import org.observertc.webrtc.reporter.TimeConverter;

public class InitiatedCallEntry implements BigQueryEntry {

	public static InitiatedCallEntry from(InitiatedCallReport initiatedCallReport) {
		return new InitiatedCallEntry()
				.withObserverUUID(initiatedCallReport.observerUUID)
				.withCallUUID(initiatedCallReport.callUUID)
				.withInitiatedTimestamp(initiatedCallReport.initiated);
	}

	public static final String OBSERVER_UUID_FIELD_NAME = "observerUUID";
	public static final String CALL_UUID_FIELD_NAME = "callUUID";
	public static final String INITIATED_TIMESTAMP_FIELD_NAME = "initiated";

	private final Map<String, Object> values;

	public InitiatedCallEntry() {
		this.values = new HashMap<>();
	}

	public InitiatedCallEntry withObserverUUID(UUID value) {
		this.values.put(OBSERVER_UUID_FIELD_NAME, value.toString());
		return this;
	}

	public InitiatedCallEntry withCallUUID(UUID value) {
		this.values.put(CALL_UUID_FIELD_NAME, value.toString());
		return this;
	}

	public InitiatedCallEntry withInitiatedTimestamp(LocalDateTime value) {
		Long epoch = TimeConverter.GMTLocalDateTimeToEpoch(value);
		this.values.put(INITIATED_TIMESTAMP_FIELD_NAME, epoch);
		return this;
	}

	public UUID getObserverUUID() {
		String value = (String) this.values.get(OBSERVER_UUID_FIELD_NAME);
		if (value == null) {
			return null;
		}
		return UUID.fromString(value);
	}

	public UUID getPeerConnectionUUID() {
		String value = (String) this.values.get(CALL_UUID_FIELD_NAME);
		if (value == null) {
			return null;
		}
		return UUID.fromString(value);
	}

	public LocalDateTime getInitiatedTimestamp() {
		Long value = (Long) this.values.get(INITIATED_TIMESTAMP_FIELD_NAME);
		return TimeConverter.epochToGMTLocalDateTime(value);

	}

	public Map<String, Object> toMap() {
		return this.values;
	}

}