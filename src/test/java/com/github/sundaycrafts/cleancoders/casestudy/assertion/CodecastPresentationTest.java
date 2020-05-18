package com.github.sundaycrafts.cleancoders.casestudy.assertion;

import com.github.sundaycrafts.cleancoders.casestudy.Codecast;
import com.github.sundaycrafts.cleancoders.casestudy.Context;
import com.github.sundaycrafts.cleancoders.casestudy.MockGateway;
import org.junit.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CodecastPresentationTest {
    @Before
    public void setUp() {
        Context.gateway = new MockGateway();
    }

    @Test
    public void NoPresentCodecastAfterDeletedAllCodecasts() {
        List<Map<String , String>> codecastInfos = new ArrayList<Map<String, String>>() {
            {
                add(
                    new HashMap<String, String>() {
                        {
                            put("title", "A");
                            put("publicationDate", "01/01/2020");
                        }
                    }
                );
                add(
                    new HashMap<String, String>() {
                        {
                            put("title", "B");
                            put("publicationDate", "02/01/2020");
                        }
                    }
                );
                add(
                    new HashMap<String, String>() {
                        {
                            put("title", "C");
                            put("publicationDate", "03/01/2020");
                        }
                    }
                );
            }
        };

        codecastInfos.forEach(info -> {
            Codecast codecast = new Codecast();
            codecast.setTitle(info.get("title"));
            codecast.setPublicationDate(info.get("publicationDate"));

            Context.gateway.save(codecast);
        });

        List<Codecast> codecasts = Context.gateway.findAllCodecasts();
        new ArrayList<Codecast>(codecasts).forEach(codecast -> Context.gateway.delete(codecast));

        assertTrue(Context.gateway.findAllCodecasts().size() == 0);
    }
}
