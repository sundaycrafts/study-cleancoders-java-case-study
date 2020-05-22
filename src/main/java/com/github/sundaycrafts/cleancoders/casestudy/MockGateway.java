package com.github.sundaycrafts.cleancoders.casestudy;

import com.google.common.collect.Lists;

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

    public void save(Codecast codecast) {
        this.codecasts.add(codecast);
    }

    public User save(User user) {
        establishId(user);
        users.add(user);
        return user;
    }

    public void save(License license) {
        establishId(license);
        licenses.add(license);
    }

    private void establishId(User user) {
        if(user.getId() == null)
            user.setId(UUID.randomUUID().toString());
    }

    private void establishId(License license) {
        if(license.getId() == null)
            license.setId(UUID.randomUUID().toString());
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
