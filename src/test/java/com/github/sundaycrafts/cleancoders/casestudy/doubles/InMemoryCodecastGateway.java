package com.github.sundaycrafts.cleancoders.casestudy.doubles;

import com.github.sundaycrafts.cleancoders.casestudy.Codecast;
import com.github.sundaycrafts.cleancoders.casestudy.CodecastGateway;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryCodecastGateway extends GatewayUtilities<Codecast> implements CodecastGateway {
  public List<Codecast> findAllCodecastsSortedChronologically() {
    return getEntities().sorted(Comparator.comparing(Codecast::getPublicationDate)).collect(Collectors.toList());
  }

  public Optional<Codecast> findCodecastByTitle(String codecastTitle) {
    return getEntities().filter(codecast -> codecast.getTitle().equals(codecastTitle)).findAny();
  }
}
