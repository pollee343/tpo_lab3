package pages;

import org.openqa.selenium.WebDriver;

public class ContestsPage extends Page {
    private static final String CONTESTS_TEXT =
            "//*[contains(normalize-space(.), 'Конкурс') or contains(normalize-space(.), 'конкурс') or contains(normalize-space(.), 'Конкурсы')]";

    public ContestsPage(WebDriver driver) {
        super(driver);
    }

    public void checkContestsPageLoaded() {
        waitForLocationContains("/konkurs/");
        assertElementPresent(CONTESTS_TEXT);
    }
}
