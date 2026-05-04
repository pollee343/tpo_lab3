package pages;

import org.openqa.selenium.WebDriver;

public class HomePage extends Page {
    private static final String PAGE_BODY = "//body";
    private static final String PROJECTS_LINK = "//a[contains(normalize-space(.), 'Найти работу')]";
    private static final String FREELANCERS_LINK = "//a[contains(normalize-space(.), 'Фрилансеры')]";
    private static final String SERVICES_LINK = "//a[contains(normalize-space(.), 'Магазин услуг')]";
    private static final String CONTESTS_LINK = "//a[contains(normalize-space(.), 'Конкурсы')]";
    private static final String LOGIN_LINK = "//a[contains(normalize-space(.), 'Вход')]";
    private static final String REGISTRATION_LINK = "//a[contains(normalize-space(.), 'Регистрация')]";
    private static final String WORK_CATEGORY =
            "//*[contains(normalize-space(.), 'Программирование')"
                    + " or contains(normalize-space(.), 'Сайты')"
                    + " or contains(normalize-space(.), 'Дизайн')"
                    + " or contains(normalize-space(.), 'Тексты')"
                    + " or contains(normalize-space(.), 'Инжениринг')]";
    private static final String FREELANCER_CATEGORY =
            "//*[contains(normalize-space(.), 'Дизайн')"
                    + " or contains(normalize-space(.), 'Программирование')"
                    + " or contains(normalize-space(.), 'Тексты')"
                    + " or contains(normalize-space(.), 'Сайты')"
                    + " or contains(normalize-space(.), 'Mobile')]";
    private static final String FREELANCER_CARD =
            "//*[contains(normalize-space(.), 'PRO') or contains(normalize-space(.), 'Портфолио') or contains(normalize-space(.), 'Telegram')]";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openHomePage() {
        open("/");
    }

    public void checkHomePageLoaded() {
        waitForElementPresent(PAGE_BODY);
        assertTitleContains("FL.ru");
        assertElementPresent(PROJECTS_LINK);
        assertElementPresent(FREELANCERS_LINK);
        assertElementPresent(SERVICES_LINK);
        assertElementPresent(CONTESTS_LINK);
    }

    public void openProjects() {
        if (isElementPresent(PROJECTS_LINK)) {
            clickByXpath(PROJECTS_LINK);
        } else {
            open("/projects/");
        }
    }

    public void openFreelancers() {
        if (isElementPresent(FREELANCERS_LINK)) {
            clickByXpath(FREELANCERS_LINK);
        } else {
            open("/freelancers/");
        }
    }

    public void openServices() {
        if (isElementPresent(SERVICES_LINK)) {
            clickByXpath(SERVICES_LINK);
        } else {
            open("/uslugi-freelancera/");
        }
    }

    public void openContests() {
        if (isElementPresent(CONTESTS_LINK)) {
            clickByXpath(CONTESTS_LINK);
        } else {
            open("/konkurs/");
        }
    }

    public void openLogin() {
        clickByXpath(LOGIN_LINK);
    }

    public void checkLoginEntryVisible() {
        assertElementPresent(LOGIN_LINK);
    }

    public void checkRegistrationEntryVisible() {
        assertElementPresent(REGISTRATION_LINK);
    }

    public void checkWorkCategoryVisible() {
        assertElementPresent(WORK_CATEGORY);
    }

    public void checkDesignCategoryVisible() {
        assertElementPresent(FREELANCER_CATEGORY);
    }

    public void checkFreelancerCardsVisible() {
        assertElementPresent(FREELANCER_CARD);
    }
}
