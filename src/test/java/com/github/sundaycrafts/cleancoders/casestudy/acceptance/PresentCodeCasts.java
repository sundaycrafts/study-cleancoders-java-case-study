package com.github.sundaycrafts.cleancoders.casestudy.acceptance;

import com.github.sundaycrafts.cleancoders.casestudy.Codecast;
import com.github.sundaycrafts.cleancoders.casestudy.Context;
import com.github.sundaycrafts.cleancoders.casestudy.MockGateway;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class PresentCodeCasts {
    @Before
    public void setUp() {
        Context.gateway = new MockGateway();
    }

    @Given("User {string}")
    public void loginUser(String username) {
        assertTrue(false);
    }

    @Given("some codecasts are saved")
    public void addCodecasts(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);

        rows.forEach(row -> {
            Codecast codecast = new Codecast();
            codecast.setTitle(row.get("title"));
            codecast.setPublicationDate(row.get("publicationDate"));
            Context.gateway.save(codecast);
        });

        List<Codecast> codecasts = Context.gateway.findAllCodecasts();
        new ArrayList<Codecast>(codecasts).forEach(codecast -> Context.gateway.delete(codecast));

        assertTrue(Context.gateway.findAllCodecasts().size() == 0);
    }

    @When("the user {string} logged in")
    public void i_ask_whether_it_s_Friday_yet(String user) {
        assertTrue(false);
    }

    @Then("cannot see any codecasts")
    public void i_should_be_told() {
        assertTrue(false);
    }
}