package org.observertc.webrtc.observer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;
import com.hazelcast.nio.serialization.VersionedPortable;
import org.observertc.webrtc.observer.common.ObjectToString;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Objects;

@JsonIgnoreProperties(value = { "classId", "factoryId", "classVersion" })
public class PeerConnectionFilterDTO implements VersionedPortable {
    private static final int CLASS_VERSION = 1;

    public static PeerConnectionFilterDTO.Builder builder() {
        return new Builder();
    }

    @NotNull
    public String name;

    @NotNull
    public String serviceName;

    public String callName = null;
    public String marker = null;

    public CollectionFilterDTO SSRCs = new CollectionFilterDTO();
    // version 2
    public CollectionFilterDTO remoteIPs = new CollectionFilterDTO();

    @Override
    public String toString() {
        return ObjectToString.toString(this);
    }

    @Override
    public boolean equals(Object other) {
        if (Objects.isNull(other) || !this.getClass().getName().equals(other.getClass().getName())) {
            return false;
        }

        PeerConnectionFilterDTO otherDTO = (PeerConnectionFilterDTO) other;
        if (this.serviceName != otherDTO.serviceName) return false;
        if (!this.SSRCs.equals(otherDTO.SSRCs)) return false;
        return true;
    }


    @Override
    public int getClassVersion() {
        return CLASS_VERSION;
    }

    @Override
    public int getFactoryId() {
        return PortableDTOFactory.FACTORY_ID;
    }

    @Override
    public int getClassId() {
        return PortableDTOFactory.PEER_CONNECTION_FILTER_DTO_CLASS_ID;
    }

    @Override
    public void writePortable(PortableWriter writer) throws IOException {
        writer.writeUTF("name", this.name);
        writer.writeUTF("serviceName", this.serviceName);
        writer.writeUTF("callName", this.callName);
        writer.writeUTF("marker", this.marker);
        writer.writePortable("ssrcs", this.SSRCs);
        writer.writePortable("remoteIPs", this.remoteIPs);
    }

    @Override
    public void readPortable(PortableReader reader) throws IOException {
        this.name = reader.readUTF("name");
        this.serviceName = reader.readUTF("serviceName");
        this.callName = reader.readUTF("callName");
        this.marker = reader.readUTF("marker");
        this.SSRCs = reader.readPortable("ssrcs");
        this.remoteIPs = reader.readPortable("remoteIPs");
    }

    public static class Builder {
        private PeerConnectionFilterDTO result = new PeerConnectionFilterDTO();

        public Builder withServiceName(String value) {
            this.result.serviceName = value;
            return this;
        }

        public Builder withMarker(String value) {
            this.result.marker = value;
            return this;
        }

        public Builder withCallName(String value) {
            this.result.callName = value;
            return this;
        }

        public Builder withName(String value) {
            this.result.name = value;
            return this;
        }

        public Builder withRemoteIPs(CollectionFilterDTO value) {
            this.result.remoteIPs = value;
            return this;
        }

        public Builder withSSRCsCollectionFilter(CollectionFilterDTO value) {
            this.result.SSRCs = value;
            return this;
        }

        public PeerConnectionFilterDTO build() {
            return this.result;
        }
    }
}
