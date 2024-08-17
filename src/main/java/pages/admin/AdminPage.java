package pages.admin;

import basepage.WebBasePage;
import driver.web.DriverManager;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.admin.Search;
import model.enums.ColumnNameAdminPage;
import model.enums.SortTypeEnum;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.common.CommonPage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class AdminPage extends WebBasePage {

  By sortIconByHeader(String headerName) {
    return By.xpath(String.format("//div[text()='%s']//div/i", headerName));
  }

  @FindBy(xpath = "//div[contains(@class,'active')]//span[text()='Ascending']")
  WebElement ascending;

  @FindBy(xpath = "//div[contains(@class,'active')]//span[text()='Descending']")
  WebElement descending;

  @FindBy(xpath = "//div[@class='oxd-table-card']")
  List<WebElement> totalListingRow;

  @FindBy(xpath = "//label[text()='Username']/../following-sibling::div//input")
  WebElement txtUsername;

  @FindBy(xpath = "//label[text()='User Role']/../following-sibling::div//div[@class='oxd-select-wrapper']")
  WebElement userRoleDropdown;

  @FindBy(xpath = "//label[text()='Employee Name']/../following-sibling::div//input")
  WebElement txtEmployeeName;

  @FindBy(xpath = "//label[text()='Status']/../following-sibling::div//div[@class='oxd-select-wrapper']")
  WebElement statusDropdown;

  @FindBy(xpath = "//button[text()=' Search ']")
  WebElement btnSearch;

  @FindBy(xpath = "//div[@class='oxd-table-card']")
  List<WebElement> userRow;

  @FindBy(xpath = "//div[@class='oxd-table-card']")
  WebElement btnReset;

  By getLblCellValue(int row, int col) {
    return By.xpath(String.format("(//div[@role='row'])[%s]/div[%s]", row, col));
  }

  private final CommonPage commonPage;

  public AdminPage() {
    super();
    commonPage = new CommonPage();
    PageFactory.initElements(DriverManager.getDriver(), this);
  }

  @Step("Verify sort by column name {columnName} with sort type {sortType}")
  public void verifySortByColumnName(ColumnNameAdminPage columnNameAdminPage, SortTypeEnum sortType) {
    clickElement(
        sortIconByHeader(columnNameAdminPage.getColumnName()),
        "sort icon in column " + columnNameAdminPage.getColumnName());
    switch (sortType) {
      case ASC:
        clickElement(ascending, "ascending option");
        break;
      case DESC:
        clickElement(descending, "ascending option");
        break;
    }
    commonPage.waitSpinner();
    long size = totalListingRow.size();
    List<String> data = new ArrayList<>();
    if (size > 1) {
      for (int i = 2; i <= size + 1; i++) {
        data.add(
            getTextOfElement(
                getLblCellValue(i, columnNameAdminPage.getColumnPosition()),
                "cell value number " + (i - 1) + " in column " + columnNameAdminPage));
      }
      List<String> sortedList = new ArrayList<>(data);
      switch (sortType) {
        case DESC:
          sortedList.sort(Comparator.comparing(String::toLowerCase, Comparator.reverseOrder()));
          break;
        default:
          sortedList.sort(String::compareToIgnoreCase);
          break;
      }
      enhancedSoftAssert.assertEquals(data, sortedList);
    }
  }

  @Step("Input to username: {valueToSend}")
  public void inputToUserName(String valueToSend) {
    if (commonPage.isNullOrEmpty(valueToSend)) {
      return;
    }
    sendKeysToElement(txtUsername, "user name text box", valueToSend);
  }

  @Step("Select {userRole} in user role dropdown")
  public void selectUserRoleOption(String userRole) {
    if (commonPage.isNullOrEmpty(userRole)) {
      return;
    }
    commonPage.selectOptionInDropdown(userRoleDropdown, "user role dropdown", userRole);
  }

  @Step("Input to employee name: {valueToSend}")
  public void inputToEmployeeName(String valueToSend) {
    if (commonPage.isNullOrEmpty(valueToSend)) {
      return;
    }
    sendKeysToElement(txtEmployeeName, "employee name text box", valueToSend);
  }

  @Step("Select {status} in status dropdown")
  public void selectStatusOption(String status) {
    if (commonPage.isNullOrEmpty(status)) {
      return;
    }
    commonPage.selectOptionInDropdown(statusDropdown, "status dropdown", status);
  }

  @Step("Click to search button")
  public void clickSearchButton() {
    clickElement(btnSearch, "search button");
    commonPage.waitSpinner();
  }

  @Step("Click to reset button")
  public void clickResetButton() {
    clickElement(btnReset, "reset button");
    commonPage.waitSpinner();
  }

  @Step("Search users")
  public void searchUsers(Search search) {
    inputToUserName(search.getUsername());
    selectUserRoleOption(search.getUserRole());
    inputToEmployeeName(search.getEmployeeName());
    selectStatusOption(search.getStatus());
    clickSearchButton();
  }

  @Step("Get all rows value of users in admin page")
  public List<Search> getRowValues() {
    return commonPage.getRowValues(userRow, Search.class);
  }

  @Step("Verify result after search")
  public void verifyValueAfterSearch(Search search, List<Search> valueAfterSearch) {
    Set<String> lstUserName = valueAfterSearch.stream().map(Search::getUsername).collect(Collectors.toSet());
    Set<String> lstUserRole = valueAfterSearch.stream().map(Search::getUsername).collect(Collectors.toSet());
    Set<String> lstEmployeeName = valueAfterSearch.stream().map(Search::getEmployeeName).collect(Collectors.toSet());
    Set<String> lstStatus = valueAfterSearch.stream().map(Search::getStatus).collect(Collectors.toSet());

    String userName = search.getUsername();
    String userRole = search.getUserRole();
    String employeeName = search.getEmployeeName();
    String status = search.getStatus();

    verifyField(userName, lstUserName, "Username");
    verifyField(userRole, lstUserRole, "User role");
    verifyField(employeeName, lstEmployeeName, "Employee name");
    verifyField(status, lstStatus, "Status");
  }

  private void verifyField(String expectedValue, Set<String> actualValues, String fieldName) {
    log.info("Verify value of {}", fieldName);
    if (expectedValue != null && !expectedValue.isEmpty()) {
      boolean isAllMatch = actualValues.stream().allMatch(actual -> actual.equals(expectedValue));
      if (!isAllMatch){
        log.error("{} does not match expect: {} but actual: {}", fieldName, expectedValue, actualValues);
      }
      enhancedSoftAssert.assertTrue(isAllMatch, fieldName + " does not match");
    }
  }
}
