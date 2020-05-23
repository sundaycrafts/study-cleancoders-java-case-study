package com.github.sundaycrafts.cleancoders.casestudy;

import java.util.ArrayList;
import java.util.List;

public class PresentCodecastUseCase {
  public List<PresentableCodecast> presentCodecasts(User loggedInUser) {
    ArrayList<PresentableCodecast> presentableCodecasts = new ArrayList<>();
    List<Codecast> allCodecasts = Context.gateway.findAllCodecasts();
    allCodecasts.stream().forEach(codecast -> {
        PresentableCodecast cc = new PresentableCodecast();
        cc.title = codecast.getTitle();
        cc.publicationDate = codecast.getPublicationDate();
        cc.isViewable = isLicensedToViewCodecast(loggedInUser, codecast);
        presentableCodecasts.add(cc);
      }
    );
    return presentableCodecasts;
  }

  public boolean isLicensedToViewCodecast(User user, Codecast codecast) {
    List<License> licenses = Context.gateway.findLicenseForUserAndCodecast(user, codecast);
    return !licenses.isEmpty();
  }
}
