package pages.login;

import basepage.WebBasePage;
import driver.web.DriverManager;
import lombok.extern.slf4j.Slf4j;
import model.ExtendCredential;
import model.enums.MessageEnum;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.function.Supplier;

@Slf4j
public class LoginPage extends WebBasePage {
  @FindBy(name = "username")
  WebElement txtUsername;

  @FindBy(xpath = "//input[@name='username']/parent::div/following-sibling::span")
  WebElement errorAtUsername;

  @FindBy(name = "password")
  WebElement txtPassword;

  @FindBy(xpath = "//input[@name='password']/parent::div/following-sibling::span")
  WebElement errorAtPassword;

  @FindBy(xpath = "//button[@type='submit']")
  WebElement btnLogin;

  @FindBy(xpath = "//div[@role='alert']//p")
  WebElement alertMessage;

  public LoginPage() {
    super();
    PageFactory.initElements(DriverManager.getDriver(), this);
  }

  public void inputToUsernameTextBox(String username) {
    log.info("Input to username textbox");
    if (username != null) {
      sendKeysToElement(txtUsername, "username text box", username);
    }
  }

  public String getErrorAtUsername() {
    log.info("Get error at username textbox");
    return getTextOfElement(errorAtUsername, "error below username text box");
  }

  public void inputToPasswordTextBox(String password) {
    log.info("Input to password textbox");
    if (password != null) {
      sendKeysToElement(txtPassword, "password text box", password);
    }
  }

  public String getErrorAtPassword() {
    log.info("Get error at password textbox");
    return getTextOfElement(errorAtPassword, "error below password text box");
  }

  public void clickToLoginButton() {
    log.info("Click login button");
    clickElement(btnLogin, "login button");
  }

  public String getAlertMessage() {
    log.info("Get alert message");
    return getTextOfElement(alertMessage, "alert message");
  }

  public void navigateAndLoginToSystem(String url, String username, String password) {
    log.info("Navigate and login to system");
    goToUrl(url);
    inputToUsernameTextBox(username);
    inputToPasswordTextBox(password);
    clickToLoginButton();
  }

  public void verifyLoginWithNegativeCase(ExtendCredential credential) {
    log.info("Verify login with negative case");
    if (credential.isWrongPassword() && credential.isWrongUsername()) {
      verifyEmptyFields(credential);
    } else if (credential.isWrongPassword() || credential.isWrongUsername()) {
      verifyInvalidCredentials();
    }
  }

  private void verifyEmptyFields(ExtendCredential credential) {
    verifyEmptyField(credential.getUsername(), this::getErrorAtUsername);
    verifyEmptyField(credential.getPassword(), this::getErrorAtPassword);
  }

  private void verifyEmptyField(String field, Supplier<String> errorGetter) {
    if (isNullOrEmpty(field)) {
      assertErrorMessage(errorGetter.get(), MessageEnum.REQUIRED.getValue());
    }
  }

  private void verifyInvalidCredentials() {
    assertErrorMessage(getAlertMessage(), MessageEnum.INVALID_CREDENTIALS.getValue());
  }

  private void assertErrorMessage(String actualMessage, String expectedMessage) {
    Assert.assertEquals(
        actualMessage, expectedMessage, MessageEnum.ERROR_MESSAGE_DOES_NOT_MATCH.getValue());
  }

  private boolean isNullOrEmpty(String str) {
    return str == null || str.isEmpty();
  }
}
