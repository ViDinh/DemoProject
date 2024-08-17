package softassert;

import driver.web.DriverManager;
import listener.ExceptionCollector;
import listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.testng.Reporter;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

@Slf4j
public class EnhancedSoftAssert extends SoftAssert {
    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        log.error(" ---------------- FAILED! ---------------- ");
        super.onAssertFailure(assertCommand, ex);
        ExceptionCollector collector = ExceptionCollector.getInstance();
        collector.addExceptionToTestResult(Reporter.getCurrentTestResult(), ex);
        Listener.saveScreenshotPNG(DriverManager.getDriver());
    }
}
