package core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Reporter {
    private static ExtentReports extent;
    private static final Map<Long, ExtentTest> testMap = new ConcurrentHashMap<>();
    private static String reportFolder;

    private Reporter() {} // Prevent instantiation

    public static synchronized void init() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            reportFolder = "test-output/Execution_" + timestamp + "/";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportFolder + "ExtentReport.html");
            spark.config().setDocumentTitle("Magento Automation Report");
            spark.config().setReportName("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // System info
            extent.setSystemInfo("Organization", "Incubyte");
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
    }

    public static synchronized void createTest(String testName) {
        testMap.put(Thread.currentThread().getId(), extent.createTest(testName));
    }

    public static synchronized void logStep(String message, Status status) {
        try {
            // Capture screenshot as Base64
            byte[] screenshotBytes = DriverManager.captureScreenshot();
            String screenshotBase64 = Base64.getEncoder().encodeToString(screenshotBytes);

            // Log to ExtentReport with screenshot
            testMap.get(Thread.currentThread().getId()).log(
                    status,
                    message,
                    MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build()
            );
        } catch (Exception e) {
            testMap.get(Thread.currentThread().getId()).log(
                    status,
                    message + " | Screenshot failed: " + e.getMessage()
            );
        }
    }

    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}