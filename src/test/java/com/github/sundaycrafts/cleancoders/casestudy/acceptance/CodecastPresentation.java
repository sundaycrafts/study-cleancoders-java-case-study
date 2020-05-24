package com.github.sundaycrafts.cleancoders.casestudy.acceptance;

import com.github.sundaycrafts.cleancoders.casestudy.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.github.sundaycrafts.cleancoders.casestudy.License.LicenseType.DOWNLOADING;
import static com.github.sundaycrafts.cleancoders.casestudy.License.LicenseType.VIEWING;
import static org.junit.Assert.*;

public class CodecastPresentation {
  private final PresentCodecastUseCase useCase = new PresentCodecastUseCase();
  private final GateKeeper gateKeeper = new GateKeeper();
  private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

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
      try {
        codecast.setPublicationDate(simpleDateFormat.parse(row.get("publicationDate")));
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
      Context.gateway.save(codecast);
    });

    assertTrue(Context.gateway.findAllCodecastsSortedChronologically().size() > 0);
  }

  @Given("no codecast")
  public void deleteAllCodecasts() {
    List<Codecast> codecasts = Context.gateway.findAllCodecastsSortedChronologically();
    new ArrayList<>(codecasts).forEach(codecast -> Context.gateway.delete(codecast));

    assertEquals(Context.gateway.findAllCodecastsSortedChronologically().size(), 0);
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
  public void createLicenceForViewing(String username, String codecastTitle) {
    Optional<User> user = Context.gateway.findUser(username);
    if (user.isEmpty()) throw new RuntimeException(String.format("No user %s found", username));

    Optional<Codecast> codecast = Context.gateway.findCodecastByTitle(codecastTitle);
    if (codecast.isEmpty()) throw new RuntimeException(String.format("No code cast for user %s", username));

    Context.gateway.save(new License(VIEWING, user.get(), codecast.get()));
    assertTrue(useCase.isLicencedFor(VIEWING, user.get(), codecast.get()));
  }

  @When("license for user {string} able to download {string}")
  public void createLicenceForDownload(String username, String codecastTitle) {
    Optional<User> user = Context.gateway.findUser(username);
    if (user.isEmpty()) throw new RuntimeException(String.format("No user %s found", username));

    Optional<Codecast> codecast = Context.gateway.findCodecastByTitle(codecastTitle);
    if (codecast.isEmpty()) throw new RuntimeException(String.format("No code cast for user %s", username));

    Context.gateway.save(new License(DOWNLOADING, user.get(), codecast.get()));
    assertTrue(useCase.isLicencedFor(DOWNLOADING, user.get(), codecast.get()));
  }

  @Then("lisence for user {string} able to download {string}")
  public void createLicenceForDownloading(String username, String codecastTitle) {
    Optional<User> user = Context.gateway.findUser(username);
    if (user.isEmpty()) throw new RuntimeException(String.format("No user %s found", username));

    Optional<Codecast> codecast = Context.gateway.findCodecastByTitle(codecastTitle);
    if (codecast.isEmpty()) throw new RuntimeException(String.format("No code cast for user %s", username));

    Context.gateway.save(new License(DOWNLOADING, user.get(), codecast.get()));
    assertTrue(useCase.isLicencedFor(DOWNLOADING, user.get(), codecast.get()));
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

    IntStream.range(0, rows.size()).forEach(i -> assertEquals(rows.get(i).get("title"), queryResponse.get(i).get("title")));
  }
}