package com.github.sundaycrafts.cleancoders.casestudy;

import java.util.List;

public interface LicenseGateway {
  License save(License license);
  List<License> findLicenseForUserAndCodecast(User user, Codecast codecast);
}
