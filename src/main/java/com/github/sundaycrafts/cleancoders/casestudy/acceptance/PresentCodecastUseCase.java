package com.github.sundaycrafts.cleancoders.casestudy.acceptance;

import com.github.sundaycrafts.cleancoders.casestudy.Codecast;
import com.github.sundaycrafts.cleancoders.casestudy.User;

import java.util.ArrayList;
import java.util.List;

public class PresentCodecastUseCase {
    public List<PresentableCodecast> presentCodecasts(User loggedInUser) {
        return new ArrayList<PresentableCodecast>();
    }

    public boolean isLicensedToViewCodecast(User user, Codecast codecast) {
        return user.isLicensedToView(codecast);
    }
}
