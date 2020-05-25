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
import static org.junit.jupiter.api.Assertions.*;

public class CodecastPresentation {
  private final PresentCodecastUseCase useCase = new PresentCodecastUseCase();
  private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

  @Before
  public void setUp() {
    TestSetup.setupContext();
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
      Context.codecastGateway.save(codecast);
    });

    assertTrue(Context.codecastGateway.findAllCodecastsSortedChronologically().size() > 0);
  }

  @Given("no codecast")
  public void deleteAllCodecasts() {
    Context.codecastGateway
      .findAllCodecastsSortedChronologically()
      .forEach(codecast -> Context.codecastGateway.delete(codecast));

    assertEquals(0, Context.codecastGateway.findAllCodecastsSortedChronologically().size());
  }

  @Given("user {string}")
  public void addUser(String username) {
    Context.userGateway.save(new User(username));
  }

  @When("the user {string} logged in")
  public void loginUser(String username) throws RuntimeException {
    Optional<User> user = Context.userGateway.findUser(username);
    user.ifPresentOrElse(u -> {
      Context.gateKeeper.setLoggedInUser(u);
      assertEquals(Context.gateKeeper.getLoggedInUser(), u);
    }, () -> {
      throw new RuntimeException();
    });
  }

  @Then("cannot see any codecasts")
  public void countOfCodecastsPresented() {
    List<PresentableCodecast> presentations = useCase.presentCodecasts(Context.gateKeeper.getLoggedInUser());
    assertEquals(0, presentations.size());
  }

  @When("license for user {string} able to view {string}")
  public void createLicenceForViewing(String username, String codecastTitle) {
    Optional<User> user = Context.userGateway.findUser(username);
    if (user.isEmpty()) throw new RuntimeException(String.format("No user %s found", username));

    Optional<Codecast> codecast = Context.codecastGateway.findCodecastByTitle(codecastTitle);
    if (codecast.isEmpty()) throw new RuntimeException(String.format("No code cast for user %s", username));

    Context.licenseGateway.save(new License(VIEWING, user.get(), codecast.get()));
    assertTrue(useCase.isLicencedFor(VIEWING, user.get(), codecast.get()));
  }

  @When("license for user {string} able to download {string}")
  public void createLicenceForDownload(String username, String codecastTitle) {
    Optional<User> user = Context.userGateway.findUser(username);
    if (user.isEmpty()) throw new RuntimeException(String.format("No user %s found", username));

    Optional<Codecast> codecast = Context.codecastGateway.findCodecastByTitle(codecastTitle);
    if (codecast.isEmpty()) throw new RuntimeException(String.format("No code cast for user %s", username));

    Context.licenseGateway.save(new License(DOWNLOADING, user.get(), codecast.get()));
    assertTrue(useCase.isLicencedFor(DOWNLOADING, user.get(), codecast.get()));
  }

  @Then("lisence for user {string} able to download {string}")
  public void createLicenceForDownloading(String username, String codecastTitle) {
    Optional<User> user = Context.userGateway.findUser(username);
    if (user.isEmpty()) throw new RuntimeException(String.format("No user %s found", username));

    Optional<Codecast> codecast = Context.codecastGateway.findCodecastByTitle(codecastTitle);
    if (codecast.isEmpty()) throw new RuntimeException(String.format("No code cast for user %s", username));

    Context.licenseGateway.save(new License(DOWNLOADING, user.get(), codecast.get()));
    assertTrue(useCase.isLicencedFor(DOWNLOADING, user.get(), codecast.get()));
  }

  @Then("the user {string} can see codecasts in chronological order")
  public void theUserCanSeeLicensedCodecastSForThemInChronologicalOrder(String username, DataTable table) {
    User loggedInUser = Context.gateKeeper.getLoggedInUser();
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