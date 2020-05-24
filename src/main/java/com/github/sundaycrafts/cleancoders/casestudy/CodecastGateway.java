package com.github.sundaycrafts.cleancoders.casestudy;

import java.util.List;
import java.util.Optional;

public interface CodecastGateway {
  List<Codecast> findAllCodecastsSortedChronologically();
  void delete(Codecast codecast);
  Codecast save(Codecast codecast);
  Optional<Codecast> findCodecastByTitle(String codecastTitle);
}
