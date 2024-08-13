package pages.admin;

import basepage.WebBasePage;
import driver.web.DriverManager;
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

  public void verifySortByColumnName(ColumnName columnName, SortTypeEnum sortType) {
    log.info("Verify sort by column name");
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
    waitElementAppearAndThenDisappear(commonPage.loadingSpinner, "loading spinner");
    long size = totalListingRow.size();
    List<String> data = new ArrayList<>();
    if (size > 1) {
      for (int i = 2; i < size + 2; i++) {
        data.add(
            getTextOfElement(getLblCellValue(i, columnName.getColumnPosition()), "cell value"));
      }
      List<String> sort = new ArrayList<>(data);
      switch (sortType) {
        case ASC:
          Collections.sort(sort);
          break;
        case DESC:
          Collections.reverse(sort);
          break;
      }
      Assert.assertEquals(data, sort);
    }
  }
}
