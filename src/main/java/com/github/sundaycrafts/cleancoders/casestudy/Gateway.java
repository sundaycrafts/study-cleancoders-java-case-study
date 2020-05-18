package com.github.sundaycrafts.cleancoders.casestudy;

import java.util.ArrayList;

public interface Gateway {
    ArrayList<Codecast> findAllCodecasts();
    void delete(Codecast codecast);

    void save(Codecast codecast);
}
