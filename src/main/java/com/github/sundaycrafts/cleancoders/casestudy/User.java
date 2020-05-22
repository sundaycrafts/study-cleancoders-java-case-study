package com.github.sundaycrafts.cleancoders.casestudy;

import java.util.Objects;

public class User {
    private String username;
    private String id;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public boolean isSame(User user) {
        return id != null && Objects.equals(id, user.id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
