package com.github.sundaycrafts.cleancoders.casestudy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockGateway implements Gateway {
    private List<Codecast> codecasts;
    private List<User> users;

    public MockGateway() {
        this.codecasts = new ArrayList<Codecast>();
        this.users = new ArrayList<User>();
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

    public Optional<User> findUser(String username) {
        return users.stream().filter(user -> username.equals(user.getUsername())).findAny();
    }
}
