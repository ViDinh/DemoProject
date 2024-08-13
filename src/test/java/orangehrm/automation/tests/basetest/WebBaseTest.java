package orangehrm.automation.tests.basetest;

import allure.AllureEnvironmentWriter;
import constants.Constants;
import driver.web.Driver;
import driver.web.DriverManager;
import environment.Environment;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import utils.FileUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static constants.Constants.getDirectorySlash;

public class WebBaseTest {

  protected static Map<String, Object> environmentSetting =
      Collections.synchronizedMap(new HashMap<>());
  public static final String ALLURE_RESULT_FOLDER = "allure-results";

  @BeforeSuite(alwaysRun = true)
  @Parameters({"env", "browser"})
  public void cleanDirectoryAndLoadData(String environment, String browser) {
    String env = Environment.getEnvironment(environment);
    FileUtils.clearDirectory(getDirectorySlash(ALLURE_RESULT_FOLDER));
    environmentSetting = Environment.loadEnvironmentSetting(
            Constants.ENVIRONMENT_PATH + getDirectorySlash(env + ".yaml"));
    AllureEnvironmentWriter.writeEnvironmentInfo(browser, env);
  }

  @BeforeTest(alwaysRun = true)
  @Parameters("browser")
  public void initDriver(String browserName) {
    Driver.getInstance().initWebDriver(environmentSetting.get("url").toString(), browserName);
  }

  @AfterTest(alwaysRun = true)
  public void removeDriver() {
    DriverManager.quitDriver();
  }
}
