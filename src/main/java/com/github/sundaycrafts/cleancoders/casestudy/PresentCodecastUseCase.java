package com.github.sundaycrafts.cleancoders.casestudy;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.sundaycrafts.cleancoders.casestudy.License.LicenseType.DOWNLOADING;
import static com.github.sundaycrafts.cleancoders.casestudy.License.LicenseType.VIEWING;

public class PresentCodecastUseCase {
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

  public List<PresentableCodecast> presentCodecasts(User loggedInUser) {
    return Context.codecastGateway.findAllCodecastsSortedChronologically().stream()
      .map(codecast -> formatCodecast(loggedInUser, codecast))
      .collect(Collectors.toList());
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
    return Context.licenseGateway.findLicenseForUserAndCodecast(user, codecast).stream()
      .anyMatch(l -> l.getType() == licenseType);
  }
}
