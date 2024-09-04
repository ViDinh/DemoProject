package driver.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Driver {

  private static Driver instance;

  private Driver() {}

  public static synchronized Driver getInstance() {
    if (instance == null) {
      instance = new Driver();
    }
    return instance;
  }

  public void initWebDriver(String browserName) {
    Browser browser = Browser.fromValues(browserName);
    switch (browser) {
      case FIREFOX -> DriverManager.setDriver(new FirefoxDriver());
      case HEADLESS_CHROME -> initHeadlessChromeWebDriver();
      default -> DriverManager.setDriver(new ChromeDriver());
    }
    WebDriver driver = DriverManager.getDriver();
    driver.manage().window().maximize();
  }

  private void initHeadlessChromeWebDriver() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless=new");
    options.addArguments("--remote-allow-origins=*");
    options.addArguments("--headless");
    options.addArguments("window-size=1920x1080");
    options.addArguments("--no-sandbox");
    options.addArguments("--disable-dev-shm-usage");
    DriverManager.setDriver(new ChromeDriver(options));
  }
}
