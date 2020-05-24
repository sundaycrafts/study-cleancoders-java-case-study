package com.github.sundaycrafts.cleancoders.casestudy.doubles;

import com.github.sundaycrafts.cleancoders.casestudy.Codecast;
import com.github.sundaycrafts.cleancoders.casestudy.License;
import com.github.sundaycrafts.cleancoders.casestudy.LicenseGateway;
import com.github.sundaycrafts.cleancoders.casestudy.User;

import java.util.List;
import java.util.stream.Collectors;

public class InMemoryLicenseGateway extends GatewayUtilities<License> implements LicenseGateway {
  public List<License> findLicenseForUserAndCodecast(User user, Codecast codecast) {
    return getEntities()
      .filter(license -> license.getUser().isSame(user)
        && license.getCodecast().isSame(codecast)).collect(Collectors.toList());
  }
}
