import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.HomePage;

public class HomePageTest {
    private Utils utils;
    private HomePage homePage;

    @Before
    public void setUp() {
        utils = new Utils();
        utils.setupDriver();
        homePage = new HomePage(utils.getDriver());
    }

    @After
    public void tearDown() {
        utils.quitDriver();
    }

    @Test
    public void tc01HomePageLoads() {
        homePage.openHomePage();
        homePage.checkHomePageLoaded();
    }
}
