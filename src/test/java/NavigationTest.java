import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.ContestsPage;
import pages.HomePage;
import pages.ServicesPage;

public class NavigationTest {
    private Utils utils;
    private HomePage homePage;
    private ServicesPage servicesPage;
    private ContestsPage contestsPage;

    @Before
    public void setUp() {
        utils = new Utils();
        utils.setupDriver();
        homePage = new HomePage(utils.getDriver());
        servicesPage = new ServicesPage(utils.getDriver());
        contestsPage = new ContestsPage(utils.getDriver());
    }

    @After
    public void tearDown() {
        utils.quitDriver();
    }

    @Test
    public void tc07ServiceStoreOpens() {
        servicesPage.open("/uslugi-freelancera/");
        servicesPage.checkServicesPageLoaded();
    }

    @Test
    public void tc08ContestsOpen() {
        contestsPage.open("/konkurs/");
        contestsPage.checkContestsPageLoaded();
    }
}
