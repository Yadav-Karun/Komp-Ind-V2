package listeners;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import core.TestBase;
import reporting.ClickReportManager;
import reporting.ScreenshotManager;

public class ClickScreenshotListener extends TestListenerAdapter {

    @Override
    public void onStart(ITestContext context) {
        ClickReportManager.startReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ClickReportManager.startTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ClickReportManager.removeTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            String screenshotPath = ScreenshotManager.capture(
                    TestBase.getDriver(),
                    result.getMethod().getMethodName() + "_Failure"
            );
            ClickReportManager.logFailure(screenshotPath);
        } catch (Exception e) {
            System.out.println("Failed to capture failure screenshot: " + e.getMessage());
        }
        ClickReportManager.removeTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ClickReportManager.removeTest();
    }

    @Override
    public void onFinish(ITestContext context) {
        ClickReportManager.flushReport();
    }
}
