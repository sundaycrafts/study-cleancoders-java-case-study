package com.github.sundaycrafts.cleancoders.casestudy;

import java.util.List;
import java.util.Optional;

public interface Gateway {
  List<Codecast> findAllCodecasts();

  void delete(Codecast codecast);

  Codecast save(Codecast codecast);

  User save(User user);

  License save(License license);

  Optional<User> findUser(String username);

  Optional<Codecast> findCodecastByTitle(String codecastTitle);

  List<License> findLicenseForUserAndCodecast(User user, Codecast codecast);
}
