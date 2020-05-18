package com.github.sundaycrafts.cleancoders.casestudy;

import java.util.List;

public interface Gateway {
    List<Codecast> findAllCodecasts();
    void delete(Codecast codecast);
}
