package com.github.sundaycrafts.cleancoders.casestudy;

import org.junit.*;
import java.util.List;
import static org.junit.Assert.*;

public class CodecastPresentationTest {
    @Before
    public void setUp() {
        Context.gateway = new MockGateway();
    }

    @Test
    public void clearCodecasts() {
        List<Codecast> codecasts = Context.gateway.findAllCodecasts();
        codecasts.forEach(codecast -> Context.gateway.delete(codecast));
        assertTrue(true);
    }
}
