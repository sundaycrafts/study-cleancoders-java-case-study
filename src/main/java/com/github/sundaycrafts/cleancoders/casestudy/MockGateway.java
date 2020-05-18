package com.github.sundaycrafts.cleancoders.casestudy;

import java.util.ArrayList;
import java.util.List;

public class MockGateway implements Gateway {
    @Override
    public List<Codecast> findAllCodecasts() {
        return new ArrayList<Codecast>();
    }

    @Override
    public void delete(Codecast codecast) {

    }
}
