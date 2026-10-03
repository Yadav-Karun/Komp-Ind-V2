package reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ClickReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static void startReport() {
        String reportFolder = System.getProperty("user.dir") + "/extent-reports";
        new java.io.File(reportFolder).mkdirs();

        String reportPath = reportFolder + "/KOMP_Click_Execution_Report.html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("KOMP Click Execution Report");
        sparkReporter.config().setReportName("KOMP IND Report");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Project", "KOMP IND - Java");
    }

    public static void startTest(String testName) {
        test.set(extent.createTest(testName));
    }

    public static void logClick(String elementName, String screenshotPath) {
        if (test.get() != null) {
            test.get().info(
                "Click: " + elementName,
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
            );
        }
    }

    public static void logFailure(String screenshotPath) {
        if (test.get() != null) {
            test.get().fail(
                "Test failed",
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
            );
        }
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    public static void removeTest() {
        test.remove();
    }
}
