package com.github.sundaycrafts.cleancoders.casestudy;

import com.github.sundaycrafts.cleancoders.casestudy.User;

public class GateKeeper {
    private User loggedInUser;

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
