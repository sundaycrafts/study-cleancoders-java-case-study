package com.github.sundaycrafts.cleancoders.casestudy;

import org.junit.Test;

import static org.junit.Assert.*;

public class PresentCodecastUseCaseTest {
    @Test
    public void userWithoutViewLicense() throws Exception {
        User user = new User("User");
        Codecast codecast = new Codecast();

        PresentCodecastUseCase useCase = new PresentCodecastUseCase();

        assertFalse(useCase.isLicensedToViewCodecast(user, codecast));
    }
}
