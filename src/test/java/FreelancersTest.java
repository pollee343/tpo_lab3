import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.FreelancersPage;
import pages.HomePage;

public class FreelancersTest {
    private Utils utils;
    private HomePage homePage;
    private FreelancersPage freelancersPage;

    @Before
    public void setUp() {
        utils = new Utils();
        utils.setupDriver();
        homePage = new HomePage(utils.getDriver());
        freelancersPage = new FreelancersPage(utils.getDriver());
    }

    @After
    public void tearDown() {
        utils.quitDriver();
    }

    @Test
    public void tc05FreelancersCatalogOpens() {
        homePage.openHomePage();
        homePage.openFreelancers();
        freelancersPage.checkFreelancersPageLoaded();
    }

    @Test
    public void tc06FreelancersCategoryCanBeOpened() {
        freelancersPage.openFreelancersPage();
        freelancersPage.openDesignCategory();
        freelancersPage.checkDesignCategoryLoaded();
    }
}
