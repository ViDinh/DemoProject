package pages.PIM;

import basepage.WebBasePage;
import constants.Constants;
import driver.web.DriverManager;
import io.qameta.allure.Step;
import model.employee.Employee;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.common.CommonPage;
import utils.RandomUtils;

public class AddEmployeePage extends WebBasePage {
  @FindBy(xpath = "//input[@type='file']")
  WebElement avatar;

 @FindBy(xpath = "//div[contains(@class,'employee-image')]/img[@alt='profile picture']")
  WebElement avatarImage;

  @FindBy(name = "firstName")
  WebElement txtFirstName;

  @FindBy(name = "middleName")
  WebElement txtMiddleName;

  @FindBy(name = "lastName")
  WebElement txtLastName;

  @FindBy(xpath = "//label[text()='Employee Id']/../following-sibling::div/input")
  WebElement txtEmployeeId;

  @FindBy(xpath = "//input[@type='checkbox']/parent::label")
  WebElement toggleCreateLoginDetails;

  @FindBy(xpath = "//label[text()='Username']/../following-sibling::div/input")
  WebElement txtUsername;

  @FindBy(xpath = "//label[text()='Enabled']")
  WebElement radioEnabled;

  @FindBy(xpath = "//label[text()='Disabled']")
  WebElement radioDisabled;

  @FindBy(xpath = "//label[text()='Password']/../following-sibling::div/input")
  WebElement txtPassword;

  @FindBy(xpath = "//label[text()='Confirm Password']/../following-sibling::div/input")
  WebElement txtConfirmPassword;

  @FindBy(xpath = "//button[text()=' Cancel ']")
  WebElement btnCancel;

  @FindBy(xpath = "//button[text()=' Save ']")
  WebElement btnSave;

  private CommonPage commonPage;
  public AddEmployeePage() {
    super();
    commonPage = new CommonPage();
    PageFactory.initElements(DriverManager.getDriver(), this);
  }

  @Step("Upload avatar")
  public void uploadAvatar(String filePath) {
    if (filePath==null || filePath.isEmpty()){
      return;
    }
    uploadFile(avatar, "avatar", Constants.getTestData() + "images/" +  filePath);
    commonPage.waitSpinner(2);
    verifyUploadImageSuccessfully();
  }

  @Step("Input to first name with value: {firstName}")
  public void inputFirstName(String firstName) {
    sendKeysToElement(txtFirstName, "first name text box", firstName);
  }

  @Step("Input to middle name with value: {middleName}")
  public void inputMiddleName(String middleName) {
    if (middleName == null || middleName.isEmpty()) {
      return;
    }
    sendKeysToElement(txtMiddleName, "middle name text box", middleName);
  }

  @Step("Input to last name with value: {lastName}")
  public void inputLastName(String lastName) {
    sendKeysToElement(txtLastName, "last name text box", lastName);
  }

  @Step("Input to employee id with value: {employeeId}")
  public void inputEmployeeId(Long employeeId) {
    clearText(txtEmployeeId, "employee id text box");
    sendKeysToElement(txtEmployeeId, "employee id text box", employeeId.toString());
  }

  @Step("Enable create login details")
  public void enableCreateLoginDetails() {
    clickElement(toggleCreateLoginDetails, "toggle create login details");
  }

  @Step("Input username text box with value: {username}")
  public void inputUsername(String username) {
    sendKeysToElement(txtUsername, "username text box", username);
  }

  @Step("Input password text box with value: {password}")
  public void inputPassword(String password) {
    sendKeysToElement(txtPassword, "password text box", password);
  }

  @Step("Input confirm password text box with value: {password}")
  public void inputConfirmPassword(String password) {
    sendKeysToElement(txtConfirmPassword, "confirm password text box", password);
  }

  @Step("Select status")
  public void selectStatus(boolean isActive) {
    if (Boolean.TRUE.equals(isActive)){
      clickElement(radioEnabled, "Enabled radio");
    } else {
      clickElement(radioDisabled, "Disabled radio");
    }
  }

  @Step("Click save button")
  public void clickSaveButton() {
    clickElement(btnSave, "Save button");
    commonPage.waitSpinner();
  }

  @Step("Input Credential Login")
  public void inputCredentialLogin(Employee employee) {
    if (!Boolean.TRUE.equals(employee.isCreateLoginCredential())){
      return;
    }
    enableCreateLoginDetails();
    inputUsername(employee.getUsername() + "_" + RandomUtils.generateRandomString(4));
    selectStatus(employee.isActive());
    inputPassword(employee.getPassword());
    inputConfirmPassword(employee.getPassword());
  }

  @Step("Verify avatar upload successfully")
  public void verifyUploadImageSuccessfully() {
    enhancedSoftAssert.assertTrue(waitForImageToBeUpdated(avatarImage, "avatar", Constants.DEFAULT_PHOTO_SRC), "Failed to upload images");
  }
}
