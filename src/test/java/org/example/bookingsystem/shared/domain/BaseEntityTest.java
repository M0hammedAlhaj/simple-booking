package org.example.bookingsystem.shared.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BaseEntityTest {

    @Test
    void shouldNotBeEqual_whenComparedWithDifferentObjectType() {
        BaseEntity entity = new BaseEntity();
        Object other = new Object();

        assertNotEquals(entity, other);
    }

    @Test
    void shouldNotBeEqual_whenIdsAreDifferent() {
        BaseEntity entity1 = new BaseEntity();
        entity1.setId(UUID.randomUUID());

        BaseEntity entity2 = new BaseEntity();
        entity2.setId(UUID.randomUUID());

        assertNotEquals(entity1, entity2);
    }

    @Test
    void shouldBeEqual_whenIdsAreSame() {
        UUID id = UUID.randomUUID();

        BaseEntity entity1 = new BaseEntity();
        entity1.setId(id);

        BaseEntity entity2 = new BaseEntity();
        entity2.setId(id);

        assertEquals(entity1, entity2);
    }
}
