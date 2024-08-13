package driver.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public final class DriverManager {

  private DriverManager() {}

  private static final ThreadLocal<WebDriver> webDriver = ThreadLocal.withInitial(() -> null);

  public static WebDriver getDriver() {
    WebDriver driver = webDriver.get();
    if (driver == null) {
      throw new WebDriverException("Driver has not been set for this thread.");
    }
    return driver;
  }

  public static void setDriver(WebDriver driver) {
    if (driver == null) {
      throw new WebDriverException("Driver cannot be null");
    }
    webDriver.set(driver);
  }

  public static void quitDriver() {
    WebDriver driver = webDriver.get();
    driver.quit();
    webDriver.remove();
  }
}
