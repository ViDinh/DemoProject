package pages.home;

import basepage.WebBasePage;
import driver.web.DriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class HomePage extends WebBasePage {
  @FindBy(xpath = "//span[@class='oxd-userdropdown-tab']/img[@alt='profile picture']")
  WebElement profilePicture;

  public HomePage() {
    super();
    PageFactory.initElements(DriverManager.getDriver(), this);
  }

  public void verifyProfilePictureDisplayed() {
    waitElementAvailable(profilePicture, "profile picture");
    Assert.assertFalse(isElementNotDisplayed(profilePicture));
  }
}
