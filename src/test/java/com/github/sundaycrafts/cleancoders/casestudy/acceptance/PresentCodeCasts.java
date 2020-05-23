package com.github.sundaycrafts.cleancoders.casestudy.acceptance;

import com.github.sundaycrafts.cleancoders.casestudy.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class PresentCodeCasts {
  private final PresentCodecastUseCase useCase = new PresentCodecastUseCase();
  private final GateKeeper gateKeeper = new GateKeeper();

  @Before
  public void setUp() {
    Context.gateway = new MockGateway();
  }

  @Given("codecasts")
  public void putSomeCodecast(DataTable table) {
    List<Map<String, String>> rows = table.asMaps(String.class, String.class);

    rows.forEach(row -> {
      Codecast codecast = new Codecast();
      codecast.setTitle(row.get("title"));
      codecast.setPublicationDate(row.get("publicationDate"));
      Context.gateway.save(codecast);
    });

    assertTrue(Context.gateway.findAllCodecasts().size() > 0);
  }

  @Given("no codecast")
  public void deleteAllCodecasts() {
    List<Codecast> codecasts = Context.gateway.findAllCodecasts();
    new ArrayList<>(codecasts).forEach(codecast -> Context.gateway.delete(codecast));

    assertEquals(Context.gateway.findAllCodecasts().size(), 0);
  }

  @Given("user {string}")
  public void addUser(String username) {
    Context.gateway.save(new User(username));
  }

  @When("the user {string} logged in")
  public void loginUser(String username) throws RuntimeException {
    Optional<User> user = Context.gateway.findUser(username);
    user.ifPresentOrElse(u -> {
      gateKeeper.setLoggedInUser(u);
      assertEquals(gateKeeper.getLoggedInUser(), u);
    }, () -> {
      throw new RuntimeException();
    });
  }

  @Then("cannot see any codecasts")
  public void countOfCodecastsPresented() {
    List<PresentableCodecast> presentations = useCase.presentCodecasts(gateKeeper.getLoggedInUser());
    assertEquals(0, presentations.size());
  }

  @When("license for user {string} able to view {string}")
  public void createLicenceForSpecificCodecast(String username, String codecastTitle) {
    Optional<User> user = Context.gateway.findUser(username);
    if (user.isEmpty()) {
      throw new RuntimeException(String.format("No user %s found", username));
    }

    Optional<Codecast> codecast = Context.gateway.findCodecastByTitle(codecastTitle);
    if (codecast.isEmpty()) {
      throw new RuntimeException(String.format("No code cast for user %s", username));
    }

    Context.gateway.save(new License(user.get(), codecast.get()));
    assertTrue(useCase.isLicensedToViewCodecast(user.get(), codecast.get()));
  }

  @Then("the user {string} can see codecasts in chronological order")
  public void theUserCanSeeLicensedCodecastSForThemInChronologicalOrder(String username, DataTable table) {
    User loggedInUser = gateKeeper.getLoggedInUser();
    PresentCodecastUseCase useCase = new PresentCodecastUseCase();
    List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(loggedInUser);
    List<Map<String, String>> queryResponse = presentableCodecasts.stream().map(pcc -> new HashMap<String, String>() {{
      put("title", pcc.title);
      put("picture", pcc.title);
      put("description", pcc.title);
      put("viewable", pcc.isViewable ? "+" : "-");
      put("downloadable", "-");
    }}).collect(Collectors.toList());

    List<Map<String, String>> rows = table.asMaps(String.class, String.class);

    for (int i = 0; i < rows.size(); i++) {
      assertEquals(rows.get(i).get("title"), queryResponse.get(i).get("title"));
    }
  }
}