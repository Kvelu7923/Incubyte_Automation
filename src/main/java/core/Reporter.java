package core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Reporter {
    private static ExtentReports extent;
    private static final Map<Long, ExtentTest> extentTestMap = new HashMap<>();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static void initReporter() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            ExtentSparkReporter spark = new ExtentSparkReporter("test-output/Report_" + timestamp + ".html");
            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
    }

    public static void createTest(String testName) {
        ExtentTest extentTest = extent.createTest(testName);
        extentTestMap.put(Thread.currentThread().getId(), extentTest);
        test.set(extentTest);
    }

    public static void logStep(String description, Status status) {
        WebDriver driver = DriverManager.getDriver();
        byte[] screenshot = new SeleniumActions(driver).takeScreenshot();

        test.get().log(status, description,
                MediaEntityBuilder.createScreenCaptureFromBase64String(
                        new String(Base64.getEncoder().encode(screenshot))
                ).build()
        );
    }

    public static void flushReports() {
        extent.flush();
    }
}