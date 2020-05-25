package com.github.sundaycrafts.cleancoders.casestudy.doubles;

import com.github.sundaycrafts.cleancoders.casestudy.Entity;

import java.util.*;
import java.util.stream.Stream;

public class GatewayUtilities<T extends Entity> {
  private final HashMap<String, T> entities;

  public GatewayUtilities() {
    this.entities = new HashMap<>();
  }

  public void delete(T entity) {
    entities.remove(entity.getId());
  }

  public T save(T entity) {
    if (entity.getId() == null)
      entity.setId(UUID.randomUUID().toString());
    entities.put(entity.getId(), cloneEntity(entity));
    return entity;
  }

  public Stream<T> getEntities() {
    return entities.values().stream().map(this::cloneEntity);
  }

  @SuppressWarnings("unchecked")
  private T cloneEntity(T entity) {
    try {
      return (T) entity.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      throw new UnCloneableEntity();
    }
  }

  private static class UnCloneableEntity extends RuntimeException {
  }
}
