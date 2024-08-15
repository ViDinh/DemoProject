package basepage;

import constants.Constants;
import driver.web.DriverManager;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public class WebBasePage {

  protected WebDriver driver;
  
  public WebBasePage() {
    this.driver = DriverManager.getDriver();
  }

  public void goToUrl(String url){
    log.info("Go to url: {}", url);
    driver.get(url);
  }

  /* WebElement */
  public void clickElement(By by, String elementName, long... timeOut) {
    log.info("Click on element {}", elementName);
    waitElementClickable(by, elementName, timeOut);
    WebElement element = driver.findElement(by);
    element.click();
  }

  public void clickElement(WebElement element, String elementName, long... timeOut) {
    log.info("Click on element {}", elementName);
    waitElementClickable(element, elementName, timeOut);
    element.click();
  }

  public void sendKeysToElement(By by, String elementName, String valueToSend, long... timeOut) {
    log.info("Send keys '{}' on element {}", valueToSend, elementName);
    waitElementAvailable(by, elementName, timeOut);
    WebElement element = driver.findElement(by);
    element.clear();
    element.sendKeys(valueToSend);
  }

  public void sendKeysToElement(
      WebElement element, String elementName, String valueToSend, long... timeOut) {
    log.info("Send keys '{}' on element {}", valueToSend, elementName);
    waitElementClickable(element, elementName, timeOut);
    element.clear();
    element.sendKeys(valueToSend);
  }
  
  public String getTextOfElement(By by, String elementName, long... timeOut) {
    waitElementAvailable(by, elementName, timeOut);
    WebElement element = driver.findElement(by);
    return element.getText();
  }

  public String getTextOfElement(WebElement element, String elementName, long... timeOut) {
    waitElementAvailable(element, elementName, timeOut);
    return element.getText();
  }

  /* WebDriverWait */
  public void waitElementAvailable(By by, String elementName, long... timeOut) {
    log.info("Wait element {} is visible", elementName);
    long timeout = timeOut.length > 0 ? timeOut[0] : Constants.LONG_TIME_OUT;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    wait.until(ExpectedConditions.visibilityOfElementLocated(by));
  }

  public void waitElementAvailable(WebElement element, String elementName, long... timeOut) {
    log.info("Wait element {} is visible", elementName);
    long timeout = timeOut.length > 0 ? timeOut[0] : Constants.LONG_TIME_OUT;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    wait.until(ExpectedConditions.visibilityOf(element));
  }

  public void waitElementClickable(By by, String elementName, long... timeOut) {
    long timeout = timeOut.length > 0 ? timeOut[0] : Constants.LONG_TIME_OUT;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    try {
      wait.until(ExpectedConditions.elementToBeClickable(by));
    } catch (Exception e) {
      log.error("Element {} not found", elementName);
    }
  }

  public void waitElementClickable(WebElement element, String elementName, long... timeOut) {
    log.info("Wait element {} is clickable", elementName);
    long timeout = timeOut.length > 0 ? timeOut[0] : Constants.LONG_TIME_OUT;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    wait.until(ExpectedConditions.elementToBeClickable(element));
  }

  public void waitElementDisappear(WebElement element, String elementName, long... timeOut) {
    log.info("Wait element {} is invisible", elementName);
    long timeout = timeOut.length > 0 ? timeOut[0] : Constants.LONG_TIME_OUT;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    wait.until(ExpectedConditions.invisibilityOf(element));
  }

  public void waitElementDisappear(By by, String elementName, long... timeOut) {
    log.info("Wait element {} is invisible", elementName);
    long timeout = timeOut.length > 0 ? timeOut[0] : Constants.LONG_TIME_OUT;
    WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeout));
    wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
  }

  public void waitElementAppearAndThenDisappear(
          WebElement element, String elementName, long... timeout) {
    long defaultTimeout = timeout.length > 0 ? timeout[0] : Constants.SHORT_TIME_OUT;
    try {
      waitElementAvailable(element, elementName, defaultTimeout);
    } catch (Exception exception) {
      log.warn("Ignore {}", exception.getMessage());
    }
    waitElementDisappear(element, elementName, defaultTimeout);
  }

  public void waitElementAppearAndThenDisappear(By by, String elementName, long... timeout) {
    long defaultTimeout = timeout.length > 0 ? timeout[0] : Constants.SHORT_TIME_OUT;
    try {
      this.waitElementAvailable(by, elementName, defaultTimeout);
    } catch (Exception exception) {
      log.warn("Ignore {}", exception.getMessage());
    }
    waitElementDisappear(by, elementName, defaultTimeout);
  }

  public boolean isElementNotDisplayed(WebElement element) {
    return !element.isDisplayed();
  }
  
}
