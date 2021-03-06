/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package org.observertc.webrtc.schemas.reports;
/** Media device kind */
@org.apache.avro.specific.AvroGenerated
public enum MediaDeviceKind implements org.apache.avro.generic.GenericEnumSymbol<MediaDeviceKind> {
  VIDEO_INPUT, AUDIO_INPUT, AUDIO_OUTPUT, UNKNOWN, NULL  ;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"MediaDeviceKind\",\"namespace\":\"org.observertc.webrtc.schemas.reports\",\"doc\":\"Media device kind\",\"symbols\":[\"VIDEO_INPUT\",\"AUDIO_INPUT\",\"AUDIO_OUTPUT\",\"UNKNOWN\",\"NULL\"]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
}
