/*
 * This file is generated by jOOQ.
 */
package org.observertc.webrtc.service.jooq.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;
import org.observertc.webrtc.service.jooq.tables.Callpeerconnections;


/**
 * CallIDs
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CallpeerconnectionsRecord extends UpdatableRecordImpl<CallpeerconnectionsRecord> implements Record2<byte[], byte[]> {

    private static final long serialVersionUID = -1814703349;

    /**
     * Setter for <code>WebRTCObserver.CallPeerconnections.peerConnection</code>. The UUID of the peer connection the SSRC belongs to
     */
    public void setPeerconnection(byte[] value) {
        set(0, value);
    }

    /**
     * Getter for <code>WebRTCObserver.CallPeerconnections.peerConnection</code>. The UUID of the peer connection the SSRC belongs to
     */
    public byte[] getPeerconnection() {
        return (byte[]) get(0);
    }

    /**
     * Setter for <code>WebRTCObserver.CallPeerconnections.callID</code>. The UUID of the call the peer connection belongs to
     */
    public void setCallid(byte[] value) {
        set(1, value);
    }

    /**
     * Getter for <code>WebRTCObserver.CallPeerconnections.callID</code>. The UUID of the call the peer connection belongs to
     */
    public byte[] getCallid() {
        return (byte[]) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<byte[]> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<byte[], byte[]> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<byte[], byte[]> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<byte[]> field1() {
        return Callpeerconnections.CALLPEERCONNECTIONS.PEERCONNECTION;
    }

    @Override
    public Field<byte[]> field2() {
        return Callpeerconnections.CALLPEERCONNECTIONS.CALLID;
    }

    @Override
    public byte[] component1() {
        return getPeerconnection();
    }

    @Override
    public byte[] component2() {
        return getCallid();
    }

    @Override
    public byte[] value1() {
        return getPeerconnection();
    }

    @Override
    public byte[] value2() {
        return getCallid();
    }

    @Override
    public CallpeerconnectionsRecord value1(byte[] value) {
        setPeerconnection(value);
        return this;
    }

    @Override
    public CallpeerconnectionsRecord value2(byte[] value) {
        setCallid(value);
        return this;
    }

    @Override
    public CallpeerconnectionsRecord values(byte[] value1, byte[] value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached CallpeerconnectionsRecord
     */
    public CallpeerconnectionsRecord() {
        super(Callpeerconnections.CALLPEERCONNECTIONS);
    }

    /**
     * Create a detached, initialised CallpeerconnectionsRecord
     */
    public CallpeerconnectionsRecord(byte[] peerconnection, byte[] callid) {
        super(Callpeerconnections.CALLPEERCONNECTIONS);

        set(0, peerconnection);
        set(1, callid);
    }
}
