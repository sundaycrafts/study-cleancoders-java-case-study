package com.github.sundaycrafts.cleancoders.casestudy;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockGateway implements Gateway {
    private ArrayList<Codecast> codecasts;
    private ArrayList<User> users;
    private ArrayList<License> licenses;

    public MockGateway() {
        this.codecasts = new ArrayList<Codecast>();
        this.users = new ArrayList<User>();
        this.licenses = new ArrayList<License>();
    }

    public List<Codecast> findAllCodecasts() {
        return this.codecasts;
    }

    public void delete(Codecast codecast) {
        codecasts.remove(codecast);
    }

    public void save(Codecast codecast) {
        this.codecasts.add(codecast);
    }

    public void save(User user) {
        users.add(user);
    }

    public void save(License license) {
        licenses.add(license);
    }

    public Optional<User> findUser(String username) {
        return users.stream().filter(user -> username.equals(user.getUsername())).findAny();
    }

    public Optional<Codecast> findCodecastByTitle(String codecastTitle) {
        return codecasts.stream()
                .filter(codecast -> codecast.getTitle().equals(codecastTitle)).findAny();
    }
}
