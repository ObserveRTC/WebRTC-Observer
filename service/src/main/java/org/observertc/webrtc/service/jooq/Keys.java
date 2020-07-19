/*
 * This file is generated by jOOQ.
 */
package org.observertc.webrtc.service.jooq;


import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;
import org.observertc.webrtc.service.jooq.tables.Callpeerconnections;
import org.observertc.webrtc.service.jooq.tables.Observerorganisations;
import org.observertc.webrtc.service.jooq.tables.Observers;
import org.observertc.webrtc.service.jooq.tables.Organisations;
import org.observertc.webrtc.service.jooq.tables.Peerconnectionssrcs;
import org.observertc.webrtc.service.jooq.tables.Reportedcalls;
import org.observertc.webrtc.service.jooq.tables.Reportedpeerconnections;
import org.observertc.webrtc.service.jooq.tables.Users;
import org.observertc.webrtc.service.jooq.tables.records.CallpeerconnectionsRecord;
import org.observertc.webrtc.service.jooq.tables.records.ObserverorganisationsRecord;
import org.observertc.webrtc.service.jooq.tables.records.ObserversRecord;
import org.observertc.webrtc.service.jooq.tables.records.OrganisationsRecord;
import org.observertc.webrtc.service.jooq.tables.records.PeerconnectionssrcsRecord;
import org.observertc.webrtc.service.jooq.tables.records.ReportedcallsRecord;
import org.observertc.webrtc.service.jooq.tables.records.ReportedpeerconnectionsRecord;
import org.observertc.webrtc.service.jooq.tables.records.UsersRecord;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>WebRTCObserver</code> schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<ObserversRecord, Integer> IDENTITY_OBSERVERS = Identities0.IDENTITY_OBSERVERS;
    public static final Identity<OrganisationsRecord, Integer> IDENTITY_ORGANISATIONS = Identities0.IDENTITY_ORGANISATIONS;
    public static final Identity<UsersRecord, Integer> IDENTITY_USERS = Identities0.IDENTITY_USERS;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<CallpeerconnectionsRecord> KEY_CALLPEERCONNECTIONS_PRIMARY = UniqueKeys0.KEY_CALLPEERCONNECTIONS_PRIMARY;
    public static final UniqueKey<ObserversRecord> KEY_OBSERVERS_PRIMARY = UniqueKeys0.KEY_OBSERVERS_PRIMARY;
    public static final UniqueKey<ObserversRecord> KEY_OBSERVERS_UUID = UniqueKeys0.KEY_OBSERVERS_UUID;
    public static final UniqueKey<OrganisationsRecord> KEY_ORGANISATIONS_PRIMARY = UniqueKeys0.KEY_ORGANISATIONS_PRIMARY;
    public static final UniqueKey<OrganisationsRecord> KEY_ORGANISATIONS_UUID = UniqueKeys0.KEY_ORGANISATIONS_UUID;
    public static final UniqueKey<OrganisationsRecord> KEY_ORGANISATIONS_NAME = UniqueKeys0.KEY_ORGANISATIONS_NAME;
    public static final UniqueKey<PeerconnectionssrcsRecord> KEY_PEERCONNECTIONSSRCS_PRIMARY = UniqueKeys0.KEY_PEERCONNECTIONSSRCS_PRIMARY;
    public static final UniqueKey<ReportedcallsRecord> KEY_REPORTEDCALLS_PRIMARY = UniqueKeys0.KEY_REPORTEDCALLS_PRIMARY;
    public static final UniqueKey<ReportedpeerconnectionsRecord> KEY_REPORTEDPEERCONNECTIONS_PRIMARY = UniqueKeys0.KEY_REPORTEDPEERCONNECTIONS_PRIMARY;
    public static final UniqueKey<UsersRecord> KEY_USERS_PRIMARY = UniqueKeys0.KEY_USERS_PRIMARY;
    public static final UniqueKey<UsersRecord> KEY_USERS_UUID = UniqueKeys0.KEY_USERS_UUID;
    public static final UniqueKey<UsersRecord> KEY_USERS_USERNAME = UniqueKeys0.KEY_USERS_USERNAME;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<ObserverorganisationsRecord, ObserversRecord> OBSERVERORGANISATIONS_IBFK_1 = ForeignKeys0.OBSERVERORGANISATIONS_IBFK_1;
    public static final ForeignKey<ObserverorganisationsRecord, OrganisationsRecord> OBSERVERORGANISATIONS_IBFK_2 = ForeignKeys0.OBSERVERORGANISATIONS_IBFK_2;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<ObserversRecord, Integer> IDENTITY_OBSERVERS = Internal.createIdentity(Observers.OBSERVERS, Observers.OBSERVERS.ID);
        public static Identity<OrganisationsRecord, Integer> IDENTITY_ORGANISATIONS = Internal.createIdentity(Organisations.ORGANISATIONS, Organisations.ORGANISATIONS.ID);
        public static Identity<UsersRecord, Integer> IDENTITY_USERS = Internal.createIdentity(Users.USERS, Users.USERS.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<CallpeerconnectionsRecord> KEY_CALLPEERCONNECTIONS_PRIMARY = Internal.createUniqueKey(Callpeerconnections.CALLPEERCONNECTIONS, "KEY_CallPeerconnections_PRIMARY", new TableField[] { Callpeerconnections.CALLPEERCONNECTIONS.PEERCONNECTIONUUID }, true);
        public static final UniqueKey<ObserversRecord> KEY_OBSERVERS_PRIMARY = Internal.createUniqueKey(Observers.OBSERVERS, "KEY_Observers_PRIMARY", new TableField[] { Observers.OBSERVERS.ID }, true);
        public static final UniqueKey<ObserversRecord> KEY_OBSERVERS_UUID = Internal.createUniqueKey(Observers.OBSERVERS, "KEY_Observers_uuid", new TableField[] { Observers.OBSERVERS.UUID }, true);
        public static final UniqueKey<OrganisationsRecord> KEY_ORGANISATIONS_PRIMARY = Internal.createUniqueKey(Organisations.ORGANISATIONS, "KEY_Organisations_PRIMARY", new TableField[] { Organisations.ORGANISATIONS.ID }, true);
        public static final UniqueKey<OrganisationsRecord> KEY_ORGANISATIONS_UUID = Internal.createUniqueKey(Organisations.ORGANISATIONS, "KEY_Organisations_uuid", new TableField[] { Organisations.ORGANISATIONS.UUID }, true);
        public static final UniqueKey<OrganisationsRecord> KEY_ORGANISATIONS_NAME = Internal.createUniqueKey(Organisations.ORGANISATIONS, "KEY_Organisations_name", new TableField[] { Organisations.ORGANISATIONS.NAME }, true);
        public static final UniqueKey<PeerconnectionssrcsRecord> KEY_PEERCONNECTIONSSRCS_PRIMARY = Internal.createUniqueKey(Peerconnectionssrcs.PEERCONNECTIONSSRCS, "KEY_PeerConnectionSSRCs_PRIMARY", new TableField[] { Peerconnectionssrcs.PEERCONNECTIONSSRCS.PEERCONNECTIONUUID, Peerconnectionssrcs.PEERCONNECTIONSSRCS.SSRC }, true);
        public static final UniqueKey<ReportedcallsRecord> KEY_REPORTEDCALLS_PRIMARY = Internal.createUniqueKey(Reportedcalls.REPORTEDCALLS, "KEY_ReportedCalls_PRIMARY", new TableField[] { Reportedcalls.REPORTEDCALLS.CALLUUID }, true);
        public static final UniqueKey<ReportedpeerconnectionsRecord> KEY_REPORTEDPEERCONNECTIONS_PRIMARY = Internal.createUniqueKey(Reportedpeerconnections.REPORTEDPEERCONNECTIONS, "KEY_ReportedPeerConnections_PRIMARY", new TableField[] { Reportedpeerconnections.REPORTEDPEERCONNECTIONS.PEERCONNECTIONUUID }, true);
        public static final UniqueKey<UsersRecord> KEY_USERS_PRIMARY = Internal.createUniqueKey(Users.USERS, "KEY_Users_PRIMARY", new TableField[] { Users.USERS.ID }, true);
        public static final UniqueKey<UsersRecord> KEY_USERS_UUID = Internal.createUniqueKey(Users.USERS, "KEY_Users_uuid", new TableField[] { Users.USERS.UUID }, true);
        public static final UniqueKey<UsersRecord> KEY_USERS_USERNAME = Internal.createUniqueKey(Users.USERS, "KEY_Users_username", new TableField[] { Users.USERS.USERNAME }, true);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<ObserverorganisationsRecord, ObserversRecord> OBSERVERORGANISATIONS_IBFK_1 = Internal.createForeignKey(Keys.KEY_OBSERVERS_PRIMARY, Observerorganisations.OBSERVERORGANISATIONS, "ObserverOrganisations_ibfk_1", new TableField[] { Observerorganisations.OBSERVERORGANISATIONS.OBSERVER_ID }, true);
        public static final ForeignKey<ObserverorganisationsRecord, OrganisationsRecord> OBSERVERORGANISATIONS_IBFK_2 = Internal.createForeignKey(Keys.KEY_ORGANISATIONS_PRIMARY, Observerorganisations.OBSERVERORGANISATIONS, "ObserverOrganisations_ibfk_2", new TableField[] { Observerorganisations.OBSERVERORGANISATIONS.ORGANISATION_ID }, true);
    }
}
