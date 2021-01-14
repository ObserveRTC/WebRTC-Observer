/*
 * Copyright  2020 Balazs Kreith
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.observertc.webrtc.observer.repositories.hazelcast;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * Convinient way to provide all repositories from one
 * singletone
 */
@Singleton
public class RepositoryProvider {

	@Inject
	Provider<CallPeerConnectionsRepository> callPeerConnectionsRepositoryProvider;

	@Inject
	Provider<CallNamesRepository> callNamesRepositoryProvider;

	@Inject
	Provider<CallEntitiesRepository> callEntitiesRepositoryProvider;

	@Inject
	Provider<PeerConnectionsRepository> peerConnectionsRepositoryProvider;

	@Inject
	Provider<SynchronizationSourcesRepository> SSRCRepositoryProvider;

	@Inject
	Provider<CallSynchronizationSourcesRepository> callSynchronozationSourcesRepositoryProvider;

	@Inject
	Provider<ICEConnectionsRepository> iceConnectionsRepositoryProvider;

	@Inject
	Provider<MediaUnitPeerConnectionsRepository> mediaUnitPeerConnectionsRepositoryProvider;

	@Inject
	Provider<PeerConnectionICEConnectionsRepository> peerConnectionICEConnectionsRepositoryProvider;

	@PostConstruct
	void setup() {

	}

	public SynchronizationSourcesRepository getSSRCRepository() {
		return this.SSRCRepositoryProvider.get();
	}

	public CallEntitiesRepository getCallEntitiesRepository() {
		return this.callEntitiesRepositoryProvider.get();
	}

	public PeerConnectionsRepository getPeerConnectionsRepository() {
		return this.peerConnectionsRepositoryProvider.get();
	}

	public CallPeerConnectionsRepository getCallPeerConnectionsRepository() {
		return this.callPeerConnectionsRepositoryProvider.get();
	}

	public CallNamesRepository getCallNamesRepository() {
		return this.callNamesRepositoryProvider.get();
	}

	public CallSynchronizationSourcesRepository getCallSynchronizationSourcesRepository() {
		return this.callSynchronozationSourcesRepositoryProvider.get();
	}

	public PeerConnectionICEConnectionsRepository getPeerConnectionICEConnectionsRepository() {
		return this.peerConnectionICEConnectionsRepositoryProvider.get();
	}

	public ICEConnectionsRepository getICEConnectionsRepository() {
		return this.iceConnectionsRepositoryProvider.get();
	}

	public MediaUnitPeerConnectionsRepository getMediaUnitPeerConnectionsRepository() {
		return this.mediaUnitPeerConnectionsRepositoryProvider.get();
	}
}
