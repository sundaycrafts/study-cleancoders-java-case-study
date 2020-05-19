package com.github.sundaycrafts.cleancoders.casestudy;

import java.util.List;
import java.util.Optional;

public interface Gateway {
    List<Codecast> findAllCodecasts();
    void delete(Codecast codecast);

    void save(Codecast codecast);

    void save(User user);

    Optional<User> findUser(String username);
}
