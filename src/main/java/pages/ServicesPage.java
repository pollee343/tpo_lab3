package pages;

import org.openqa.selenium.WebDriver;

public class ServicesPage extends Page {
    private static final String SERVICES_TEXT =
            "//*[contains(normalize-space(.), 'Услуги') or contains(normalize-space(.), 'услуг') or contains(normalize-space(.), 'Магазин услуг')]";

    public ServicesPage(WebDriver driver) {
        super(driver);
    }

    public void checkServicesPageLoaded() {
        waitForLocationContains("/uslugi-freelancera/");
        assertElementPresent(SERVICES_TEXT);
    }
}
