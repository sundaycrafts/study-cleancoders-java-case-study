package com.github.sundaycrafts.cleancoders.casestudy;

public class License {
    private User user;
    private Codecast codecast;
    private String id;

    public License(User user, Codecast codecast) {
        this.user = user;
        this.codecast = codecast;
    }

    public User getUser() {
        return user;
    }

    public Codecast getCodecast() {
        return codecast;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
