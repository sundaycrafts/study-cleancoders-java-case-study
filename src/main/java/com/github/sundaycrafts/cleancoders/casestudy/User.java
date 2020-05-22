package com.github.sundaycrafts.cleancoders.casestudy;

import java.util.Objects;

public class User extends Entity {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
