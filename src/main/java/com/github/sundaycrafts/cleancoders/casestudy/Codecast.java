package com.github.sundaycrafts.cleancoders.casestudy;

public class Codecast {
    private String title;
    private String publicationDate;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSame(Codecast codecast) {
        return true;
    }
}
