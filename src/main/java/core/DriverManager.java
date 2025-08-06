package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class DriverManager {
    private static final ConcurrentHashMap<Long, WebDriver> driverMap = new ConcurrentHashMap<>();

    private DriverManager() {}

    public static WebDriver getDriver() {
        long threadId = Thread.currentThread().getId();
        if (!driverMap.containsKey(threadId)) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-ads");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-extensions-except");
            options.addArguments("--disable-plugins");

            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            prefs.put("profile.default_content_settings.popups", 0);
            options.setExperimentalOption("prefs", prefs);

            WebDriver driver = new ChromeDriver(options);  // create ONE instance
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//            new WebDriverWait(driver, Duration.ofSeconds(10)).until(
//                    (ExpectedCondition<Boolean>) wd ->
//                            ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete")
//            );

            driverMap.put(threadId, driver); // store that same instance
        }
        return driverMap.get(threadId);
    }

    public static void quitDriver() {
        long threadId = Thread.currentThread().getId();
        WebDriver driver = driverMap.get(threadId);
        if (driver != null) {
            driver.quit();
            driverMap.remove(threadId);
        }
    }

    public static byte[] captureScreenshot() {
        WebDriver driver = getDriver();
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}