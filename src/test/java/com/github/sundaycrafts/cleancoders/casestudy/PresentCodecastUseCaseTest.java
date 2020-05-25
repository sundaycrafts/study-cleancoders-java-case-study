package com.github.sundaycrafts.cleancoders.casestudy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.github.sundaycrafts.cleancoders.casestudy.License.LicenseType.*;
import static org.junit.jupiter.api.Assertions.*;

public class PresentCodecastUseCaseTest {
  private User user;
  private Codecast codecast;
  private PresentCodecastUseCase useCase;

  @BeforeEach
  public void setUp() {
    TestSetup.setupContext();
    user = Context.userGateway.save(new User("User"));
    codecast = Context.codecastGateway.save(new Codecast());
    useCase = new PresentCodecastUseCase();
  }

  @Test
  public void userWithoutViewLicense_cannotViewCodecast() {
    assertFalse(useCase.isLicencedFor(VIEWING, user, codecast));
  }

  @Test
  public void userWithViewLicense_canViewCodecast() {
    License viewLicense = new License(VIEWING, user, codecast);
    Context.licenseGateway.save(viewLicense);
    assertTrue(useCase.isLicencedFor(VIEWING, user, codecast));
  }

  @Test
  public void userWithViewLicense_cannotViewOtherUsersCodecast() {
    User otherUser = Context.userGateway.save(new User("otherUser"));
    License viewLicense = new License(VIEWING, user, codecast);
    Context.licenseGateway.save(viewLicense);
    assertFalse(useCase.isLicencedFor(VIEWING, otherUser, codecast));
  }

  @Test
  public void presentingNoCodecast() {
    Context.codecastGateway.delete(codecast);
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
    assertEquals(0, presentableCodecasts.size());
  }

  @Test
  public void presentOneCodecast() {
    codecast.setTitle("Some Title");
    Date now = new GregorianCalendar(2020, Calendar.MAY, 23).getTime();
    codecast.setPublicationDate(now);
    Context.codecastGateway.save(codecast);

    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
    PresentableCodecast presentableCodecast = presentableCodecasts.get(0);

    assertEquals(1, presentableCodecasts.size());
    assertEquals("Some Title", presentableCodecast.title);
    assertEquals("23/05/2020", presentableCodecast.publicationDate);
  }

  @Test
  public void presentedCodecastIsNotViewableIfNoLicense() {
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
    PresentableCodecast presentableCodecast = presentableCodecasts.get(0);
    assertFalse(presentableCodecast.isViewable);
  }

  @Test
  public void presentedCodecastIsViewableIfLicenseExists() {
    Context.licenseGateway.save(new License(VIEWING, user, codecast));
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
    PresentableCodecast presentableCodecast = presentableCodecasts.get(0);
    assertTrue(presentableCodecast.isViewable);
  }

  @Test
  public void presentedCodecastIsDownloadableIfDownloadLicenseExists() {
    Context.licenseGateway.save(new License(DOWNLOADING, user, codecast));
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
    PresentableCodecast downloadableCodecast = presentableCodecasts.get(0);
    assertTrue(downloadableCodecast.isDownloadable);
    assertFalse(downloadableCodecast.isViewable);
  }
}
