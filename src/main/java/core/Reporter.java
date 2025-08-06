package core;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public final class Reporter {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    private static String reportDirPath;

    private Reporter() {}

    // Initialize report once before all tests
    public static synchronized void init() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            reportDirPath = "test-output/" + timestamp;
            File reportDir = new File(reportDirPath);
            reportDir.mkdirs();

            String reportPath = reportDirPath + "/ExtentReport.html";
            System.out.println("📄 Creating report at: " + new File(reportPath).getAbsolutePath());

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Organization", "Incubyte");
            extent.setSystemInfo("Engineer", "Kathirvel Arumugam");
        }
    }

    // Create a test for each scenario
    public static void createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        testThread.set(test);
    }

    // Log a step with screenshot
    public static void logStep(String message, Status status) {
        ExtentTest test = testThread.get();
        if (test != null) {
            try {
                WebDriver driver = DriverManager.getDriver();
                String base64Screenshot = captureScreenshotAsBase64(driver);
                test.log(status, message, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
            } catch (Exception e) {
                test.log(status, message + " (⚠ Screenshot failed: " + e.getMessage() + ")");
            }
        }
    }

    // Capture screenshot as base64
    private static String captureScreenshotAsBase64(WebDriver driver) {
        return Base64.getEncoder().encodeToString(
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
        );
    }

    // Flush report at the end
    public static synchronized void flush() {
        if (extent != null) {
            System.out.println("✅ Flushing extent report...");
            extent.flush();
        }
    }

    // Get report path if needed elsewhere
    public static String getReportDirPath() {
        return reportDirPath;
    }
}
