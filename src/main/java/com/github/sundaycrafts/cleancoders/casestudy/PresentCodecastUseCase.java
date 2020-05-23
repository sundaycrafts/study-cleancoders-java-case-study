package com.github.sundaycrafts.cleancoders.casestudy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PresentCodecastUseCase {
  private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

  public List<PresentableCodecast> presentCodecasts(User loggedInUser) {
    ArrayList<PresentableCodecast> presentableCodecasts = new ArrayList<>();
    List<Codecast> allCodecasts = Context.gateway.findAllCodecastsSortedChronologically();
    allCodecasts.forEach(codecast -> {
        PresentableCodecast pcc = new PresentableCodecast();
        pcc.title = codecast.getTitle();
        pcc.publicationDate = dateFormat.format(codecast.getPublicationDate());
        pcc.isViewable = isLicensedToViewCodecast(loggedInUser, codecast);
        presentableCodecasts.add(pcc);
      }
    );
    return presentableCodecasts;
  }

  public boolean isLicensedToViewCodecast(User user, Codecast codecast) {
    List<License> licenses = Context.gateway.findLicenseForUserAndCodecast(user, codecast);
    return !licenses.isEmpty();
  }
}
