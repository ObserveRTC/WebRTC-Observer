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

import org.observertc.webrtc.observer.ObserverHazelcast;
import org.observertc.webrtc.observer.models.WeakLockEntity;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;

/**
 * Call to PC keys
 */
@Singleton
public class WeakLocksRepository extends MapRepositoryAbstract<String, WeakLockEntity> {

	private static final String HAZELCAST_MAP_KEY = "WebRTCObserverWeakLocks";

	public WeakLocksRepository(ObserverHazelcast observerHazelcast) {
		super(observerHazelcast, HAZELCAST_MAP_KEY);
	}

	public WeakLockEntity saveIfAbsent(@NotNull String key, @NotNull WeakLockEntity entity) {
		return this.getEntities().putIfAbsent(key, entity);
	}
}
