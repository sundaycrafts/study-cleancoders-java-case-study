package com.github.sundaycrafts.cleancoders.casestudy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.github.sundaycrafts.cleancoders.casestudy.License.LicenseType.*;
import static org.junit.jupiter.api.Assertions.*;

public class PresentCodecastUseCaseTest {
  private User user;
  private PresentCodecastUseCase useCase;

  @BeforeEach
  public void setUp() {
    TestSetup.setupContext();
    user = Context.userGateway.save(new User("User"));
    useCase = new PresentCodecastUseCase();
  }

  @Nested
  class GivenNoCodecasts {
    @Test
    public void presentingNoCodecast() {
      List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
      assertEquals(0, presentableCodecasts.size());
    }
  }

  @Nested
  class GivenOneCodecast {
    private Codecast codecast;

    @BeforeEach
    void setupCodecast() {
      codecast = Context.codecastGateway.save(new Codecast());
    }

    @Test
    public void OneIsPresented() {
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
    public void presentedCodecastShowsNotViewable() {
      List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
      PresentableCodecast presentableCodecast = presentableCodecasts.get(0);
      assertFalse(presentableCodecast.isViewable);
    }

    @Nested
    class GivenOneViewingLicenseForTheUser {
      @BeforeEach
      void setupLicense() {
        License viewLicense = new License(VIEWING, user, codecast);
        Context.licenseGateway.save(viewLicense);
      }

      @Test
      void licensedUserCanViewCodecast() {
        assertTrue(useCase.isLicencedFor(VIEWING, user, codecast));
      }

      @Test
      public void unlicensedUserCannotViewOtherUsersCodecast() {
        User otherUser = Context.userGateway.save(new User("otherUser"));
        assertFalse(useCase.isLicencedFor(VIEWING, otherUser, codecast));
      }

      @Test
      public void presentedCodecastIsViewable() {
        Context.licenseGateway.save(new License(VIEWING, user, codecast));
        List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
        PresentableCodecast presentableCodecast = presentableCodecasts.get(0);
        assertTrue(presentableCodecast.isViewable);
      }
    }

    @Nested
    class GivenOneDownloadLicense {
      @BeforeEach
      void setupLicense() {
        License downloadLicense = new License(DOWNLOADING, user, codecast);
        Context.licenseGateway.save(downloadLicense);
      }

      @Test
      public void presentedCodecastIsDownloadable() {
        Context.licenseGateway.save(new License(DOWNLOADING, user, codecast));
        List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
        PresentableCodecast downloadableCodecast = presentableCodecasts.get(0);
        assertTrue(downloadableCodecast.isDownloadable);
        assertFalse(downloadableCodecast.isViewable);
      }
    }
  }
}
