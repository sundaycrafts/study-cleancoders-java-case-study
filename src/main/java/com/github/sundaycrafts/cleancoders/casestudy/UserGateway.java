package com.github.sundaycrafts.cleancoders.casestudy;

import java.util.Optional;

public interface UserGateway {
  User save(User user);
  Optional<User> findUser(String username);
}
