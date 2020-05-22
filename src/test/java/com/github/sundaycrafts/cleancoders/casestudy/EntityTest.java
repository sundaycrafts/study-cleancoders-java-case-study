package com.github.sundaycrafts.cleancoders.casestudy;

import org.junit.Test;
import static org.junit.Assert.*;

public class EntityTest {
    @Test
    public void twoDifferentEntitysAreNotTheSame() throws Exception {
        Entity e1 = new Entity();
        Entity e2 = new Entity();

        e1.setId("e1ID");
        e1.setId("e2ID");

        assertFalse(e1.isSame(e2));
    }

    @Test
    public void oneEntityIsTheSameAsItself() throws Exception {
        Entity e1 = new Entity();
        e1.setId("e1ID");

        assertTrue(e1.isSame(e1));
    }

    @Test
    public void usesWithTheSameIdAreTheSame() throws Exception {
        Entity e1 = new Entity();
        Entity e2 = new Entity();
        e1.setId("e1ID");
        e2.setId("e1ID");

        assertTrue(e1.isSame(e2));
    }

    @Test
    public void EntitysWithNullAreNeverSame() throws Exception {
        Entity e1 = new Entity();
        Entity e2 = new Entity();
        assertFalse(e1.isSame(e2));
    }
}
