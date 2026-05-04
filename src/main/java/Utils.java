import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.PageLoadStrategy;

import java.time.Duration;

public class Utils {
    private static WebDriver sharedDriver;
    private static boolean shutdownHookRegistered;
    private WebDriver driver;

    public synchronized void setupDriver() {
        if (sharedDriver != null) {
            driver = sharedDriver;
            return;
        }

        String browser = System.getProperty("browser", "firefox").toLowerCase();

        if (browser.contains("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.setPageLoadStrategy(PageLoadStrategy.NONE);
            options.addArguments(
                    "--disable-notifications",
                    "--disable-popup-blocking",
                    "--disable-extensions",
                    "--disable-gpu",
                    "--blink-settings=imagesEnabled=false",
                    "--no-first-run",
                    "--no-default-browser-check",
                    "--ignore-certificate-errors",
                    "--start-maximized"
            );
            String chromeBinary = System.getProperty("chromeBinary");
            if (chromeBinary != null && !chromeBinary.isEmpty()) {
                options.setBinary(chromeBinary);
            }
            driver = new ChromeDriver(options);
        } else {
            FirefoxOptions options = new FirefoxOptions();
            options.setPageLoadStrategy(PageLoadStrategy.NONE);
            String firefoxBinary = System.getProperty("firefoxBinary", "/Applications/Firefox.app/Contents/MacOS/firefox");
            if (!firefoxBinary.isEmpty()) {
                options.setBinary(firefoxBinary);
            }
            driver = new FirefoxDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        sharedDriver = driver;
        registerShutdownHook();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void quitDriver() {
        quitSharedDriver();
    }

    private static synchronized void quitSharedDriver() {
        WebDriver driverToQuit = sharedDriver;
        sharedDriver = null;
        if (driverToQuit != null) {
            driverToQuit.quit();
        }
    }

    private static synchronized void registerShutdownHook() {
        if (shutdownHookRegistered) {
            return;
        }
        Runtime.getRuntime().addShutdownHook(new Thread(Utils::quitSharedDriver));
        shutdownHookRegistered = true;
    }
}
