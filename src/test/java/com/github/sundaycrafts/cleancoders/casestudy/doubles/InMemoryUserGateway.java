package com.github.sundaycrafts.cleancoders.casestudy.doubles;

import com.github.sundaycrafts.cleancoders.casestudy.User;
import com.github.sundaycrafts.cleancoders.casestudy.UserGateway;

import java.util.Optional;

public class InMemoryUserGateway extends GatewayUtilities<User> implements UserGateway {
  public Optional<User> findUser(String username) {
    return getEntities().filter(user -> username.equals(user.getUsername())).findAny();
  }
}
