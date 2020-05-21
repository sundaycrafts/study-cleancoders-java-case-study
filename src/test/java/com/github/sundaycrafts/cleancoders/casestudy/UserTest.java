package com.github.sundaycrafts.cleancoders.casestudy;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void twoDifferentUsersAreNotTheSame() throws Exception {
        User u1 = new User("u1");
        User u2 = new User("u2");

        u1.setId("u1ID");
        u1.setId("u2ID");

        assertFalse(u1.isSame(u2));
    }

    @Test
    public void oneUserIsTheSameAsItself() throws Exception {
        User u1 = new User("u1");
        u1.setId("u1ID");

        assertTrue(u1.isSame(u1));
    }

    @Test
    public void usesWithTheSameIdAreTheSame() throws Exception {
        User u1 = new User("u1");
        User u2 = new User("u2");
        u1.setId("u1ID");
        u2.setId("u1ID");

        assertTrue(u1.isSame(u2));

    }
}
