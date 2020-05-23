package com.github.sundaycrafts.cleancoders.casestudy;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PresentCodecastUseCaseTest {
  private User user;
  private Codecast codecast;
  private PresentCodecastUseCase useCase;

  @Before
  public void setUp() {
    Context.gateway = new MockGateway();
    user = Context.gateway.save(new User("User"));
    codecast = Context.gateway.save(new Codecast());
    useCase = new PresentCodecastUseCase();
  }

  @Test
  public void userWithoutViewLicense_cannotViewCodecast() {
    assertFalse(useCase.isLicensedToViewCodecast(user, codecast));
  }

  @Test
  public void userWithViewLicense_canViewCodecast() {
    License viewLicense = new License(user, codecast);
    Context.gateway.save(viewLicense);
    assertTrue(useCase.isLicensedToViewCodecast(user, codecast));
  }

  @Test
  public void userWithViewLicense_cannotViewOtherUsersCodecast() {
    User otherUser = Context.gateway.save(new User("otherUser"));
    License viewLicense = new License(user, codecast);
    Context.gateway.save(viewLicense);
    assertFalse(useCase.isLicensedToViewCodecast(otherUser, codecast));
  }

  @Test
  public void presentingNoCodecast() {
    Context.gateway.delete(codecast);
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
    assertEquals(0, presentableCodecasts.size());
  }

  @Test
  public void presentOneCodecast() {
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
    assertEquals(1, presentableCodecasts.size());
  }

  @Test
  public void presentedCodecastIsNotViewableIfNoLicense() {
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
    PresentableCodecast presentableCodecast = presentableCodecasts.get(0);
    assertFalse(presentableCodecast.isViewable);
  }

  @Test
  public void presentedCodecastIsViewableIfLicenseExists() {
    Context.gateway.save(new License(user, codecast));
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
    PresentableCodecast presentableCodecast = presentableCodecasts.get(0);
    assertTrue(presentableCodecast.isViewable);
  }
}
