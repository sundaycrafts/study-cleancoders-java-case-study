package com.github.sundaycrafts.cleancoders.casestudy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.github.sundaycrafts.cleancoders.casestudy.License.LicenseType.DOWNLOADING;
import static com.github.sundaycrafts.cleancoders.casestudy.License.LicenseType.VIEWING;

public class PresentCodecastUseCase {
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

  public List<PresentableCodecast> presentCodecasts(User loggedInUser) {
    List<Codecast> allCodecasts = Context.codecastGateway.findAllCodecastsSortedChronologically();

    ArrayList<PresentableCodecast> presentableCodecasts = new ArrayList<>();
    allCodecasts.forEach(codecast -> presentableCodecasts.add(formatCodecast(loggedInUser, codecast)));

    return presentableCodecasts;
  }

  private PresentableCodecast formatCodecast(User loggedInUser, Codecast codecast) {
    PresentableCodecast pcc = new PresentableCodecast();
    pcc.title = codecast.getTitle();
    pcc.publicationDate = dateFormat.format(codecast.getPublicationDate());
    pcc.isViewable = isLicencedFor(VIEWING, loggedInUser, codecast);
    pcc.isDownloadable = isLicencedFor(DOWNLOADING, loggedInUser, codecast);
    return pcc;
  }

  public boolean isLicencedFor(License.LicenseType licenseType, User user, Codecast codecast) {
    List<License> licenses = Context.licenseGateway.findLicenseForUserAndCodecast(user, codecast);
    return licenses.stream().anyMatch(l -> l.getType() == licenseType);
  }
}
