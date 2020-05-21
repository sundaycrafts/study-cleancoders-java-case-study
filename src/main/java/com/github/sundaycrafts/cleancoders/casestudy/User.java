package com.github.sundaycrafts.cleancoders.casestudy;

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
        return id.equals(user.id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
