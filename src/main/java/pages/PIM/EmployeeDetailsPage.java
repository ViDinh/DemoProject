package pages.PIM;

import basepage.WebBasePage;
import constants.Constants;
import driver.web.DriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.common.CommonPage;

public class EmployeeDetailsPage extends WebBasePage {
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

  @FindBy(xpath = "//button[text()=' Cancel ']")
  WebElement btnCancel;

  @FindBy(xpath = "//button[text()=' Save ']")
  WebElement btnSave;

  private CommonPage commonPage;
  public EmployeeDetailsPage() {
    super();
    commonPage = new CommonPage();
    PageFactory.initElements(DriverManager.getDriver(), this);
  }

  @Step("Verify avatar")
  public void verifyAvatar(String images) {
    if (images == null || images.isEmpty()){
      return;
    }
    enhancedSoftAssert.assertTrue(waitForImageToBeUpdated(avatarImage, "avatar", Constants.DEFAULT_PHOTO_SRC), "Failed to upload images");
  }

  @Step("Get text in first name text box")
  private String getTextInFirstName() {
    return getAttributeOfElement(txtFirstName, "first name text box", "value");
  }

  @Step("Verify text in first name text box")
  public void verifyTextInFirstName(String firstName) {
    commonPage.waitSpinner();
    enhancedSoftAssert.assertEquals(
        getTextInFirstName(), firstName, "Text in first name text box does not match");
  }

  @Step("Get text in middle name text box")
  private String getTextInMiddleName() {
    return getAttributeOfElement(txtMiddleName, "middle name text box", "value");
  }

  @Step("Verify text in middle name text box")
  public void verifyTextInMiddleName(String middleName) {
    middleName = middleName == null ? "" : middleName;
    enhancedSoftAssert.assertEquals(
        getTextInMiddleName(), middleName, "Text in middle name text box does not match");
  }

  @Step("Get text in last name text box")
  private String getTextInLastName() {
    return getAttributeOfElement(txtLastName, "last name text box", "value");
  }

  @Step("Verify text in last name text box")
  public void verifyTextInLastName(String lastName) {
    enhancedSoftAssert.assertEquals(
        getTextInLastName(), lastName, "Text in last name text box does not match");
  }

  @Step("Get text in employee id name text box")
  private String getTextInEmployeeId() {
    return getAttributeOfElement(txtEmployeeId, "employee id text box", "value");
  }

  @Step("Verify text in employee id text box")
  public void verifyTextInEmployeeId(Long employeeId) {
    enhancedSoftAssert.assertEquals(
        getTextInEmployeeId(), employeeId.toString(), "Text in employee id text box does not match");
  }

  @Step("Click save button")
  public void clickSaveButton() {
    clickElement(btnSave, "Save button");
  }
}
