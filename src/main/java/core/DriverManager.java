package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.ConcurrentHashMap;

public final class DriverManager {
    private static final ConcurrentHashMap<Long, WebDriver> driverMap = new ConcurrentHashMap<>();

    private DriverManager() {} // Prevent instantiation

    public static WebDriver getDriver() {
        long threadId = Thread.currentThread().getId();
        if (!driverMap.containsKey(threadId)) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            driverMap.put(threadId, new ChromeDriver(options));
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