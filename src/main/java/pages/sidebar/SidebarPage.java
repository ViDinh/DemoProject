package pages.sidebar;

import basepage.WebBasePage;
import driver.web.DriverManager;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.common.CommonPage;

@Slf4j
public class SidebarPage extends WebBasePage {

  @FindBy(xpath = "//span[text()='Admin']")
  WebElement admin;

  @FindBy(xpath = "//span[text()='PIM']")
  WebElement PIM;

  @FindBy(xpath = "//span[text()='Leave']")
  WebElement leave;

  @FindBy(xpath = "//span[text()='Time']")
  WebElement time;

  @FindBy(xpath = "//span[text()='Recruitment']")
  WebElement recruitment;

  @FindBy(xpath = "//span[text()='My Info']")
  WebElement myInfo;

  @FindBy(xpath = "//span[text()='Performance']")
  WebElement performance;

  @FindBy(xpath = "//span[text()='Dashboard']")
  WebElement dashboard;

  @FindBy(xpath = "//span[text()='Directory']")
  WebElement directory;

  @FindBy(xpath = "//span[text()='Maintenance']")
  WebElement maintenance;

  @FindBy(xpath = "//span[text()='Claim']")
  WebElement claim;

  @FindBy(xpath = "//span[text()='Buzz']")
  WebElement buzz;

  private final CommonPage commonPage;
  public SidebarPage() {
    super();
    commonPage = new CommonPage();
    PageFactory.initElements(DriverManager.getDriver(), this);
  }

  @Step("Go to Admin page")
  public SidebarPage goToAdminPage() {
    clickElement(admin, "admin option in side bar");
    commonPage.waitSpinner();
    return this;
  }

  @Step("Go to Dashboard page")
  public SidebarPage goToDashboardPage() {
    clickElement(dashboard, "admin option in side bar");
    return this;
  }
}
