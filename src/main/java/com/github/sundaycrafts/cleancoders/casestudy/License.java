package com.github.sundaycrafts.cleancoders.casestudy;

public class License extends Entity {
  public enum LicenseType {VIEWING, DOWNLOADING}

  private User user;
  private Codecast codecast;
  private LicenseType type;

  public License(LicenseType licenseType, User user, Codecast codecast) {
    this.type = licenseType;
    this.user = user;
    this.codecast = codecast;
  }

  public User getUser() {
    return user;
  }

  public Codecast getCodecast() {
    return codecast;
  }

  public LicenseType getType() {
    return type;
  }
}
