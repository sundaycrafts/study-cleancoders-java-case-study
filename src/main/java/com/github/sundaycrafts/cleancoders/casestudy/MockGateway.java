package com.github.sundaycrafts.cleancoders.casestudy;

import java.util.ArrayList;

public class MockGateway implements Gateway {
    private ArrayList<Codecast> codecasts;

    public MockGateway() {
        this.codecasts = new ArrayList<Codecast>();
    }

    public ArrayList<Codecast> findAllCodecasts() {
        return this.codecasts;
    }

    public void delete(Codecast codecast) {
        codecasts.remove(codecast);
    }

    public void save(Codecast codecast) {
        this.codecasts.add(codecast);
    }
}
