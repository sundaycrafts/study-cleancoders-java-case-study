package com.github.sundaycrafts.cleancoders.casestudy;

import com.github.sundaycrafts.cleancoders.casestudy.doubles.InMemoryCodecastGateway;
import com.github.sundaycrafts.cleancoders.casestudy.doubles.InMemoryLicenseGateway;
import com.github.sundaycrafts.cleancoders.casestudy.doubles.InMemoryUserGateway;

public class TestSetup {
  public static void setupContext() {
    Context.userGateway = new InMemoryUserGateway();
    Context.licenseGateway = new InMemoryLicenseGateway();
    Context.codecastGateway = new InMemoryCodecastGateway();
    Context.gateKeeper = new GateKeeper();
  }
}
