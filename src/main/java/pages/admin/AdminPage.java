package pages.admin;

import basepage.WebBasePage;
import driver.web.DriverManager;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.enums.ColumnName;
import model.enums.SortTypeEnum;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import pages.common.CommonPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
  public void verifySortByColumnName(ColumnName columnName, SortTypeEnum sortType) {
    clickElement(
        sortIconByHeader(columnName.getColumnName()),
        "sort icon in column " + columnName.getColumnName());
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
            getTextOfElement(getLblCellValue(i, columnName.getColumnPosition()), "cell value number " + (i-1) + " in column " + columnName));
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
}
