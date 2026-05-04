package pages;

import org.openqa.selenium.WebDriver;

public class FreelancersPage extends Page {
    private static final String FREELANCERS_MARKER =
            "//*[contains(normalize-space(.), 'Фрилансеры') or contains(normalize-space(.), 'Удаленная работа') or contains(normalize-space(.), 'Разместить заказ')]";
    private static final String FREELANCER_CARD =
            "//*[contains(@class, 'pro') or contains(normalize-space(.), 'PRO') or contains(normalize-space(.), 'Портфолио')]";
    private static final String FIRST_FREELANCER =
            "(//a[contains(@href, '/users/') and string-length(normalize-space(.)) > 2])[1]";
    private static final String DESIGN_CATEGORY = "(//a[contains(normalize-space(.), 'Дизайн')])[1]";
    private static final String DESIGN_TEXT = "//*[contains(normalize-space(.), 'Дизайн')]";
    private static final String FREELANCER_NAME =
            "//*[self::h1 or self::h2][string-length(normalize-space(.)) > 2]";
    private static final String FREELANCER_INFO =
            "//*[contains(normalize-space(.), 'Портфолио') or contains(normalize-space(.), 'Специализа') or contains(normalize-space(.), 'Отзывы') or contains(normalize-space(.), 'Информация') or contains(normalize-space(.), 'PRO')]";
    private static final String OFFER_ORDER_BUTTON =
            "//*[self::a or self::button][contains(normalize-space(.), 'Предложить заказ') or contains(normalize-space(.), 'Заказать') or contains(normalize-space(.), 'Написать') or contains(normalize-space(.), 'Связаться')]";
    private static final String AUTH_REQUIRED =
            "//*[contains(normalize-space(.), 'Вход') or contains(normalize-space(.), 'Регистрация') or contains(normalize-space(.), 'Авториз') or contains(normalize-space(.), 'Зарегистр')]";

    public FreelancersPage(WebDriver driver) {
        super(driver);
    }

    public void openFreelancersPage() {
        open("/freelancers/");
    }

    public void checkFreelancersPageLoaded() {
        waitForElementPresent(FREELANCERS_MARKER);
        assertLocationContains("/freelancers/");
        assertElementPresent(FREELANCERS_MARKER);
    }

    public void openDesignCategory() {
        clickByXpath(DESIGN_CATEGORY);
    }

    public void checkDesignCategoryLoaded() {
        waitForElementPresent(DESIGN_TEXT);
        assertElementPresent(DESIGN_TEXT);
        assertElementPresent(FREELANCER_CARD);
    }

    public void openFirstFreelancerProfile() {
        openFreelancersPage();
        clickFirstAvailable(FIRST_FREELANCER, FREELANCER_CARD);
    }

    public void checkFreelancerProfileLoaded() {
        assertElementPresent(FREELANCER_NAME);
        assertElementPresent(FREELANCER_INFO);
    }

    public void offerOrderToFreelancer() {
        clickFirstAvailable(OFFER_ORDER_BUTTON, FREELANCER_INFO);
    }

    public void checkAuthorizationRequired() {
        assertElementPresent(AUTH_REQUIRED);
    }
}
