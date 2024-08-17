package listener;

import driver.web.DriverManager;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

@Slf4j
public class Listener implements ITestListener {

  @Override
  public void onTestStart(ITestResult result) {
    log.info("Test Started: {}", result.getName());
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    log.info("Test Passed: {}", result.getName());
  }

  @Override
  public void onTestFailure(ITestResult result) {
    log.error("Test Failed: {}", result.getName());
    saveScreenshotPNG(DriverManager.getDriver());
  }

  @Attachment(value = "Screenshot", type = "image/png")
  public static byte[] saveScreenshotPNG(WebDriver driver) {
    return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
  }
}
