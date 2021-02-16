package org.observertc.webrtc.observer.repositories.tasks;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.observertc.webrtc.observer.entities.CallEntity;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@MicronautTest
class FindCallsByNameTaskTest {

    private static final EasyRandom generator = new EasyRandom();

    @Inject
    Provider<FindCallsByNameTask> findCallsByNameTaskProvider;

    @Inject
    TestUtils testUtils;

    @Test
    void shouldFoundCallByCallName_1() {
        CallEntity callEntity = testUtils.generateCallEntity();
        testUtils.insertCallEntity(callEntity);

        Map<UUID, CallEntity> map = findCallsByNameTaskProvider.get()
                .whereCallName(callEntity.call.serviceUUID, callEntity.call.callName)
                .execute()
                .getResult();

        Assertions.assertEquals(callEntity, map.get(callEntity.call.callUUID));
    }

    @Test
    void shouldFoundCallByCallName_2() {
        CallEntity callEntity = testUtils.generateCallEntity();
        testUtils.insertCallEntity(callEntity);

        Map<UUID, CallEntity> map = findCallsByNameTaskProvider.get()
                .execute(Map.of(callEntity.call.serviceUUID, Set.of(callEntity.call.callName)))
                .getResult();

        Assertions.assertEquals(callEntity, map.get(callEntity.call.callUUID));
    }


}