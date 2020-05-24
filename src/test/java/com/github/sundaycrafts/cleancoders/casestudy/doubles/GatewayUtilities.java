package com.github.sundaycrafts.cleancoders.casestudy.doubles;

import com.github.sundaycrafts.cleancoders.casestudy.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GatewayUtilities<T extends Entity> {
  private final List<T> entities;

  public GatewayUtilities() {
    this.entities = new ArrayList<>();
  }

  public void delete(T entity) {
    entities.remove(entity);
  }

  public T save(T entity) {
    entities.add(establishId(entity));
    return entity;
  }

  protected T establishId(T entity) {
    if (entity.getId() == null)
      entity.setId(UUID.randomUUID().toString());
    return entity;
  }

  @SuppressWarnings("unchecked")
  public Stream<T> getEntities() {
    return entities.stream().map(e -> {
      try {
        return (T) e.clone();
      } catch (CloneNotSupportedException cloneNotSupportedException) {
        throw new UnCloneableEntity();
      }
    });
  }

  private static class UnCloneableEntity extends RuntimeException {
  }
}
