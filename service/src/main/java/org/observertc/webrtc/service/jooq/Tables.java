/*
 * This file is generated by jOOQ.
 */
package org.observertc.webrtc.service.jooq;


import org.observertc.webrtc.service.jooq.tables.Callpeerconnections;
import org.observertc.webrtc.service.jooq.tables.Observerorganisations;
import org.observertc.webrtc.service.jooq.tables.Observers;
import org.observertc.webrtc.service.jooq.tables.Organisations;
import org.observertc.webrtc.service.jooq.tables.Peerconnectionssrcs;
import org.observertc.webrtc.service.jooq.tables.Users;


/**
 * Convenience access to all tables in WebRTCObserver
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * CallIDs
     */
    public static final Callpeerconnections CALLPEERCONNECTIONS = Callpeerconnections.CALLPEERCONNECTIONS;

    /**
     * An associative table to map Observers to Evaluators
     */
    public static final Observerorganisations OBSERVERORGANISATIONS = Observerorganisations.OBSERVERORGANISATIONS;

    /**
     * Observers
     */
    public static final Observers OBSERVERS = Observers.OBSERVERS;

    /**
     * Organizations
     */
    public static final Organisations ORGANISATIONS = Organisations.ORGANISATIONS;

    /**
     * SSRCMap
     */
    public static final Peerconnectionssrcs PEERCONNECTIONSSRCS = Peerconnectionssrcs.PEERCONNECTIONSSRCS;

    /**
     * Users
     */
    public static final Users USERS = Users.USERS;
}
