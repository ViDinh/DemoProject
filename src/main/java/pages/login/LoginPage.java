package pages.login;

import basepage.WebBasePage;
import driver.web.DriverManager;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.login.ExtendCredential;
import model.enums.MessageEnum;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.common.CommonPage;

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

  private CommonPage commonPage;
  public LoginPage() {
    super();
    commonPage = new CommonPage();
    PageFactory.initElements(DriverManager.getDriver(), this);
  }

  @Step("Input to username text box")
  public void inputToUsernameTextBox(String username) {
    if (username != null) {
      sendKeysToElement(txtUsername, "username text box", username);
    }
  }

  @Step("Get error at username")
  public String getErrorAtUsername() {
    return getTextOfElement(errorAtUsername, "error below username text box");
  }

  @Step("Input to password text box")
  public void inputToPasswordTextBox(String password) {
    if (password != null) {
      sendKeysToElement(txtPassword, "password text box", password);
    }
  }

  @Step("Get error at password")
  public String getErrorAtPassword() {
    return getTextOfElement(errorAtPassword, "error below password text box");
  }

  @Step("Click to login button")
  public void clickToLoginButton() {
    clickElement(btnLogin, "login button");
  }

  @Step("Get alert at password")
  public String getAlertMessage() {
    return getTextOfElement(alertMessage, "alert message");
  }

  @Step("Navigate and login to login")
  public void navigateAndLoginToSystem(String url, String username, String password) {
    goToUrl(url);
    inputToUsernameTextBox(username);
    inputToPasswordTextBox(password);
    clickToLoginButton();
  }

  @Step("Verify login with negative case")
  public void verifyLoginWithNegativeCase(ExtendCredential credential) {
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
    if (commonPage.isNullOrEmpty(field)) {
      assertErrorMessage(errorGetter.get(), MessageEnum.REQUIRED.getValue());
    }
  }

  private void verifyInvalidCredentials() {
    assertErrorMessage(getAlertMessage(), MessageEnum.INVALID_CREDENTIALS.getValue());
  }

  private void assertErrorMessage(String actualMessage, String expectedMessage) {
    enhancedSoftAssert.assertEquals(
        actualMessage, expectedMessage, MessageEnum.ERROR_MESSAGE_DOES_NOT_MATCH.getValue());
  }

}
