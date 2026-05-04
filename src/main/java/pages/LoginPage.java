package pages;

import org.openqa.selenium.WebDriver;

public class LoginPage extends Page {
    private static final String LOGIN_FORM = "//form";
    private static final String LOGIN_INPUT = "(//form//input[not(@type) or @type='text' or @type='email'])[1]";
    private static final String PASSWORD_INPUT = "//form//input[@type='password']";
    private static final String SUBMIT_BUTTON =
            "//form//*[self::button or self::input][@type='submit' or contains(normalize-space(.), 'Войти')]";
    private static final String INVALID_LOGIN_ERROR =
            "//*[contains(normalize-space(.), 'Невер') or contains(normalize-space(.), 'ошиб') or contains(normalize-space(.), 'Ошибка') or contains(normalize-space(.), 'неправил') or contains(normalize-space(.), 'заполн')]";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void openLoginPage() {
        open("/account/login/");
    }

    public void checkLoginFormLoaded() {
        waitForLocationContains("/account/login/");
        assertElementPresent(LOGIN_FORM);
        assertElementPresent(LOGIN_INPUT);
        assertElementPresent(PASSWORD_INPUT);
    }

    public void enterCredentials(String login, String password) {
        waitForElementPresent(LOGIN_FORM);
        typeByXpath(LOGIN_INPUT, login);
        typeByXpath(PASSWORD_INPUT, password);
    }

    public void submitLoginForm() {
        clickByXpath(SUBMIT_BUTTON);
    }

    public void checkInvalidLoginRejected() {
        sleep(2000);
        assertAnyElementPresent(INVALID_LOGIN_ERROR, LOGIN_FORM);
    }
}
