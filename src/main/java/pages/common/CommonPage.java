package pages.common;

import basepage.WebBasePage;
import driver.web.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CommonPage extends WebBasePage {
  @FindBy(xpath = "//div[@class='oxd-loading-spinner']")
  public WebElement loadingSpinner;

  public CommonPage() {
    super();
    PageFactory.initElements(DriverManager.getDriver(), this);
  }

  public void waitSpinner() {
    waitElementAppearAndThenDisappear(loadingSpinner, "loading spinner");
  }
}
