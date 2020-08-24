/*
 * This file is generated by jOOQ.
 */
package org.observertc.webrtc.observer.jooq.tables.pojos;


import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * A table to track the active streams
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Activestreams implements Serializable {

    private static final long serialVersionUID = -749952226;

    private final byte[] observeruuid;
    private final Long   ssrc;
    private final byte[] calluuid;

    public Activestreams(Activestreams value) {
        this.observeruuid = value.observeruuid;
        this.ssrc = value.ssrc;
        this.calluuid = value.calluuid;
    }

    public Activestreams(
        byte[] observeruuid,
        Long   ssrc,
        byte[] calluuid
    ) {
        this.observeruuid = observeruuid;
        this.ssrc = ssrc;
        this.calluuid = calluuid;
    }

    @NotNull
    @Size(max = 16)
    public byte[] getObserveruuid() {
        return this.observeruuid;
    }

    @NotNull
    public Long getSsrc() {
        return this.ssrc;
    }

    @NotNull
    @Size(max = 16)
    public byte[] getCalluuid() {
        return this.calluuid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Activestreams (");

        sb.append("[binary...]");
        sb.append(", ").append(ssrc);
        sb.append(", ").append("[binary...]");

        sb.append(")");
        return sb.toString();
    }
}
