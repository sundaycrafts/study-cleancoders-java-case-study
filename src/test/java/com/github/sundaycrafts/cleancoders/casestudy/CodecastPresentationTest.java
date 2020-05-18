package com.github.sundaycrafts.cleancoders.casestudy;

import org.junit.Test;

import java.util.List;

public class CodecastPresentationTest {
    @Test
    public boolean clearCodecasts() {
        List<Codecast> codecasts = database.findAllCodecasts();
        codecasts.forEach(codecast -> database.delete(codecast));
        return true;
    }
}
