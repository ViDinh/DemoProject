package pages.home;

import basepage.WebBasePage;
import driver.web.DriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends WebBasePage {
  @FindBy(xpath = "//span[@class='oxd-userdropdown-tab']/img[@alt='profile picture']")
  WebElement profilePicture;

  public HomePage() {
    super();
    PageFactory.initElements(DriverManager.getDriver(), this);
  }

  @Step("Verify profile picture is displayed")
  public void verifyProfilePictureDisplayed() {
    waitElementAvailable(profilePicture, "profile picture");
    enhancedSoftAssert.assertFalse(isElementNotDisplayed(profilePicture));
  }
}
