import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.HomePage;
import pages.ProjectsPage;

public class ProjectsTest {
    private Utils utils;
    private HomePage homePage;
    private ProjectsPage projectsPage;

    @Before
    public void setUp() {
        utils = new Utils();
        utils.setupDriver();
        homePage = new HomePage(utils.getDriver());
        projectsPage = new ProjectsPage(utils.getDriver());
    }

    @After
    public void tearDown() {
        utils.quitDriver();
    }

    @Test
    public void tc02ProjectsListOpens() {
        projectsPage.openProjectsPage();
        projectsPage.checkProjectsPageLoaded();
    }

    @Test
    public void tc03ProjectsCategoryCanBeOpened() {
        projectsPage.openProjectsPage();
        projectsPage.checkProgrammingCategoryLoaded();
    }

    @Test
    public void tc04ProjectCardCanBeOpened() {
        projectsPage.openProjectsPage();
        projectsPage.openFirstProject();
        projectsPage.checkProjectDetailsLoaded();
    }
}
