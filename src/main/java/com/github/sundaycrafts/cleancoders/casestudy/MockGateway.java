package com.github.sundaycrafts.cleancoders.casestudy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public Codecast save(Codecast codecast) {
        this.codecasts.add((Codecast) establishId(codecast));
        return codecast;
    }

    public User save(User user) {
        users.add((User) establishId(user));
        return user;
    }

    public License save(License license) {
        licenses.add((License) establishId(license));
        return license;
    }

    private Entity establishId(Entity entity) {
        if(entity.getId() == null)
            entity.setId(UUID.randomUUID().toString());
        return entity;
    }

    public Optional<User> findUser(String username) {
        return users.stream().filter(user -> username.equals(user.getUsername())).findAny();
    }

    public Optional<Codecast> findCodecastByTitle(String codecastTitle) {
        return codecasts.stream()
                .filter(codecast -> codecast.getTitle().equals(codecastTitle)).findAny();
    }

    public List<License> findLicenseForUserAndCodecast(User user, Codecast codecast) {
        return licenses.stream().filter(license -> {
            if (license.getUser().isSame(user) && license.getCodecast().isSame(codecast))
                return true;
            return false;
        }).collect(Collectors.toList());
    }
}
