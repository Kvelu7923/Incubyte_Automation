package core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class Reporter {
    private static ExtentReports extent;
    private static final Map<Long, ExtentTest> testMap = new HashMap<>();
    private static final ThreadLocal<String> screenshotFolder = new ThreadLocal<>();

    private Reporter() {} // Prevent instantiation

    public static synchronized void initReporter() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = "test-output/Execution_" + timestamp + "/";

            // Create execution folder
            new File(reportPath).mkdirs();
            screenshotFolder.set(reportPath + "screenshots/");
            new File(screenshotFolder.get()).mkdirs();

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath + "ExtentReport.html");
            spark.config().setDocumentTitle("Magento Automation Report");
            spark.config().setReportName("Test Execution Report");
            spark.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // System information
            extent.setSystemInfo("Organization", "Incubyte");
            extent.setSystemInfo("Automation Engineer", System.getProperty("user.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
    }

    public static synchronized void createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        testMap.put(Thread.currentThread().getId(), test);
    }

    public static synchronized void logStep(String description, Status status) {
        try {
            WebDriver driver = DriverManager.getDriver();
            String screenshotPath = captureScreenshot(driver, "step_" + System.currentTimeMillis());

            testMap.get(Thread.currentThread().getId()).log(
                    status,
                    description,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
            );
        } catch (Exception e) {
            testMap.get(Thread.currentThread().getId()).log(
                    status,
                    description + " (Screenshot failed: " + e.getMessage() + ")"
            );
        }
    }

    public static synchronized void logStep(String description, Status status, WebDriver driver) {
        try {
            String screenshotPath = captureScreenshot(driver, "step_" + System.currentTimeMillis());
            testMap.get(Thread.currentThread().getId()).log(
                    status,
                    description,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
            );
        } catch (Exception e) {
            testMap.get(Thread.currentThread().getId()).log(
                    status,
                    description + " (Screenshot failed: " + e.getMessage() + ")"
            );
        }
    }

    private static String captureScreenshot(WebDriver driver, String screenshotName) throws IOException {
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destination = screenshotFolder.get() + screenshotName + ".png";
        FileUtils.copyFile(source, new File(destination));
        return destination;
    }

    public static synchronized void logInfo(String message) {
        testMap.get(Thread.currentThread().getId()).info(
                MarkupHelper.createLabel(message, ExtentColor.BLUE)
        );
    }

    public static synchronized void logWarning(String message) {
        testMap.get(Thread.currentThread().getId()).warning(
                MarkupHelper.createLabel(message, ExtentColor.ORANGE)
        );
    }

    public static synchronized void logError(String message) {
        testMap.get(Thread.currentThread().getId()).fail(
                MarkupHelper.createLabel(message, ExtentColor.RED)
        );
    }

    public static synchronized void flushReports() {
        extent.flush();
    }

    public static synchronized String getScreenshotFolder() {
        return screenshotFolder.get();
    }
}