package listener;

import driver.web.DriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {

  static final Logger log = LoggerFactory.getLogger(Listener.class);

  private static String getTestMethodName(ITestResult iTestResult) {
    return iTestResult.getMethod().getConstructorOrMethod().getName();
  }

  @Override
  public void onTestStart(ITestResult iTestResult) {
    log.info("---> {} Start!", getTestMethodName(iTestResult));
  }

  @Override
  public void onTestSuccess(ITestResult iTestResult) {
    log.info("---> {} Passed!", getTestMethodName(iTestResult));
  }

  @Override
  public void onTestFailure(ITestResult iTestResult) {
    log.error("---> {} Failed! Please read reasons below..", getTestMethodName(iTestResult));
    saveScreenshotPNG(DriverManager.getDriver());
  }

  @Attachment(value = "Screenshot", type = "image/png")
  public static byte[] saveScreenshotPNG(WebDriver driver) {
    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
  }
}
