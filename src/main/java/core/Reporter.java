package core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class Reporter {
    private static ExtentReports extent;
    private static final Map<Long, ExtentTest> testMap = new HashMap<>();
    private static final Map<Long, String> testNameMap = new HashMap<>();
    private static String reportDirPath;

    private Reporter() {}

    public static synchronized void init() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            reportDirPath = "test-output/" + timestamp;
            File reportDir = new File(reportDirPath);
            reportDir.mkdirs();

            String reportPath = reportDirPath + "/ExtentReport.html";
            System.out.println("Creating report at: " + new File(reportPath).getAbsolutePath());

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Organization", "Incubyte");
            extent.setSystemInfo("Engineer", System.getProperty("user.name"));
        }
    }

    public static synchronized void createTest(String testName) {
        long threadId = Thread.currentThread().getId();
        if (extent != null) {
            testMap.put(threadId, extent.createTest(testName));
            testNameMap.put(threadId, testName);
        }
    }

    public static synchronized void logStep(String message, Status status) {
        long threadId = Thread.currentThread().getId();
        ExtentTest test = testMap.get(threadId);

        if (test == null) {
            createTest(testNameMap.getOrDefault(threadId, "Automation Test"));
            test = testMap.get(threadId);
        }

        if (test != null) {
            try {
                WebDriver driver = DriverManager.getDriver();
                String screenshot = captureScreenshotAsBase64(driver);
                test.log(status, message,
                        MediaEntityBuilder.createScreenCaptureFromBase64String(screenshot).build());
            } catch (Exception e) {
                test.log(status, message + " (Screenshot failed: " + e.getMessage() + ")");
            }
        }
    }

    private static String captureScreenshotAsBase64(WebDriver driver) {
        return Base64.getEncoder().encodeToString(
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
        );
    }

    public static synchronized void flush() {
        if (extent != null) {
            System.out.println("Flushing extent report...");
            extent.flush();
        } else {
            System.out.println("Extent was null at flush");
        }
    }

    // Optional: Getter for report folder path if needed elsewhere
    public static String getReportDirPath() {
        return reportDirPath;
    }
}
