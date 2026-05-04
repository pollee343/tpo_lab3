package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class Page {
    private static final String APPLICATION_URL = "https://www.fl.ru";
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(12);
    private static final long ACTION_DELAY_MS = Long.getLong("actionDelayMs", 0L);

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, WAIT_TIMEOUT);
    }

    public void open(String path) {
        String url = path.startsWith("http://") || path.startsWith("https://") ? path : APPLICATION_URL + path;

        WebDriverException lastError = null;
        for (int attempt = 1; attempt <= 2; attempt++) {
            try {
                driver.get(url);
                waitForElementPresent("//body");
                closeOverlays();
                actionDelay();
                return;
            } catch (WebDriverException e) {
                lastError = e;
                if (e instanceof NoSuchWindowException || isWindowClosed()) {
                    throw e;
                }
                sleep(1000L * attempt);
            }
        }
        throw lastError;
    }

    protected void clickByXpath(String xpath) {
        closeOverlays();
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        scrollTo(element);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException | TimeoutException e) {
            clickWithJavascript(element);
        }
        closeOverlays();
        actionDelay();
    }

    protected void typeByXpath(String xpath, String value) {
        closeOverlays();
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        scrollTo(element);
        element.clear();
        element.sendKeys(value);
        actionDelay();
    }

    protected void assertElementPresent(String xpath) {
        waitForElementPresent(xpath);
    }

    protected void assertAnyElementPresent(String... xpaths) {
        for (String xpath : xpaths) {
            if (isElementPresent(xpath)) {
                return;
            }
        }
        throw new AssertionError("None of expected XPath locators is present");
    }

    protected void assertLocationContains(String expectedPart) {
        String actualLocation = driver.getCurrentUrl();
        if (!actualLocation.contains(expectedPart)) {
            throw new AssertionError("Location must contain " + expectedPart + ", actual: " + actualLocation);
        }
    }

    protected void assertTitleContains(String expectedPart) {
        String actualTitle = driver.getTitle();
        if (!actualTitle.contains(expectedPart)) {
            throw new AssertionError("Title must contain " + expectedPart + ", actual: " + actualTitle);
        }
    }

    protected void waitForElementPresent(String xpath) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    protected boolean isElementPresent(String xpath) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void clickFirstAvailable(String... xpaths) {
        for (String xpath : xpaths) {
            if (isElementPresent(xpath)) {
                clickByXpath(xpath);
                return;
            }
        }
        throw new AssertionError("None of expected XPath locators is clickable");
    }

    protected void typeFirstAvailable(String value, boolean submit, String... xpaths) {
        for (String xpath : xpaths) {
            if (isElementPresent(xpath)) {
                typeByXpath(xpath, value);
                if (submit) {
                    driver.findElement(By.xpath(xpath)).sendKeys(Keys.ENTER);
                    actionDelay();
                }
                return;
            }
        }
        throw new AssertionError("None of expected XPath inputs is present");
    }

    protected void waitForLocationContains(String expectedPart) {
        wait.until(ExpectedConditions.urlContains(expectedPart));
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError("Test was interrupted", e);
        }
    }

    protected void closeOverlays() {
        String[] closeButtons = {
                "//*[self::button or self::a][contains(normalize-space(.), 'Соглашаюсь')]",
                "//*[self::button or self::a][contains(normalize-space(.), 'Согласен')]",
                "//*[self::button or self::a][contains(normalize-space(.), 'Согласиться')]",
                "//*[self::button or self::a][contains(normalize-space(.), 'Принимаю')]",
                "//*[contains(@class, 'cookie')]//*[self::button or self::a][contains(normalize-space(.), 'Принять') or contains(normalize-space(.), 'ОК') or contains(normalize-space(.), 'Хорошо') or contains(normalize-space(.), 'Понятно') or contains(normalize-space(.), 'Соглас')]",
                "//button[contains(@aria-label, 'Закрыть') or contains(@aria-label, 'Close')]",
                "//button[contains(normalize-space(.), 'Закрыть')]",
                "//button[contains(normalize-space(.), 'Понятно')]",
                "//button[contains(normalize-space(.), 'Принять')]",
                "//button[contains(normalize-space(.), 'Согласен')]",
                "//*[self::button or self::a][contains(@class, 'close') or contains(@class, 'Close')]"
        };

        for (String xpath : closeButtons) {
            driver.findElements(By.xpath(xpath)).stream()
                    .filter(WebElement::isDisplayed)
                    .findFirst()
                    .ifPresent(this::clickWithJavascript);
        }
    }

    private boolean isWindowClosed() {
        try {
            driver.getWindowHandle();
            return false;
        } catch (NoSuchWindowException e) {
            return true;
        }
    }

    private void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'center'});",
                element
        );
    }

    private void clickWithJavascript(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    private void actionDelay() {
        if (ACTION_DELAY_MS > 0) {
            sleep(ACTION_DELAY_MS);
        }
    }
}
