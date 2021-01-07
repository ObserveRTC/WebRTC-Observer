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

package org.observertc.webrtc.observer.evaluators;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.observertc.webrtc.observer.models.PeerConnectionEntity;
import org.observertc.webrtc.observer.models.SynchronizationSourceEntity;
import org.observertc.webrtc.observer.repositories.hazelcast.PeerConnectionsRepository;
import org.observertc.webrtc.observer.repositories.hazelcast.SynchronizationSourcesRepository;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@MicronautTest
public class ActivePCsEvaluatorTest {

	static TestInputsGenerator generator = TestInputsGenerator.builder().build();

	@Inject
	Provider<ActivePCsEvaluator> subject;

	@Inject
	PeerConnectionsRepository peerConnectionsRepository;

	@Inject
	SynchronizationSourcesRepository synchronizationSourcesRepository;

	@Test
	public void shouldUpdateExistingPeerConnections() {
		// Given
		ActivePCsEvaluator evaluator = subject.get();
		SynchronizationSourceEntity ssrcEntity = generator.makeSynchronizationSourceEntity();
		PeerConnectionEntity pcEntity = generator.makePeerConnectionEntityFor(ssrcEntity);
		PCState pcState = generator.makePCStateFor(pcEntity, ssrcEntity);
		this.peerConnectionsRepository.save(pcEntity.peerConnectionUUID, pcEntity);
		this.synchronizationSourcesRepository.save(
				SynchronizationSourcesRepository.getKey(ssrcEntity.serviceUUID, ssrcEntity.SSRC),
				ssrcEntity
		);
		AtomicReference<Map<UUID, PCState>> newPcsHolder = new AtomicReference<>(null);

		// When
		evaluator.onNext(Map.of(pcState.peerConnectionUUID, pcState));
		evaluator.observableNewPeerConnections().subscribe(newPcsHolder::set);

		// Then
		Assertions.assertNull(newPcsHolder.get());
	}

	@Test
	public void shouldDetectNewPCs() {
		// Given
		ActivePCsEvaluator evaluator = subject.get();
		PCState pcState = generator.makePCState();
		AtomicReference<Map<UUID, PCState>> newPCsHolder = new AtomicReference<>(null);
		evaluator.observableNewPeerConnections().subscribe(newPCsHolder::set);

		// When
		evaluator.onNext(Map.of(pcState.peerConnectionUUID, pcState));

		// Then
		Assertions.assertNotNull(newPCsHolder.get());
		PCState actual = newPCsHolder.get().get(pcState.peerConnectionUUID);
		Assertions.assertNotNull(actual);
		Assertions.assertEquals(actual, pcState);
	}

}