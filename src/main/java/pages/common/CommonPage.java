package pages.common;

import basepage.WebBasePage;
import driver.web.DriverManager;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CommonPage extends WebBasePage {
  @FindBy(xpath = "//div[@class='oxd-loading-spinner']")
  public WebElement loadingSpinner;

  @FindBy(xpath = "//span[@class='oxd-userdropdown-tab']")
  public WebElement userDropdown;

  @FindBy(xpath = "//a[text()='Logout']")
  public WebElement logout;

  By optionInDropdown(String value) {
    return By.xpath(String.format("//div[@role='option']/span[text()='%s']", value));
  }

  public CommonPage() {
    super();
    PageFactory.initElements(DriverManager.getDriver(), this);
  }

  public void waitSpinner() {
    waitElementAppearAndThenDisappear(loadingSpinner, "loading spinner");
  }

  @Step("Logout")
  public void logout(){
    clickElement(userDropdown, "User dropdown");
    clickElement(logout, "Logout");
  }

  public void selectOptionInDropdown(WebElement dropdown, String dropdownName, String valueToSelect){
    clickElement(dropdown, dropdownName);
    clickElement(optionInDropdown(valueToSelect), "Option " + valueToSelect);
  }

  public boolean isNullOrEmpty(String str) {
    return str == null || str.isEmpty();
  }

  public <T> List<T> getRowValues(List<WebElement> elements, Class<T> clzz) {
    return elements.stream()
            .map(this::extractRowData)
            .map(x-> createSearchObject(x, clzz))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .toList();
  }

  private List<String> extractRowData(WebElement row) {
    String rowText = getTextOfElement(row, "");
    return Arrays.asList(rowText.split("\n"));
  }

  private <T> Optional<T> createSearchObject(List<String> rowData, Class<T> clzz) {
    try {
      return Optional.of(ObjectMapper.fromList(rowData, clzz));
    } catch (Exception e) {
      log.error("Failed to create Search object from row data: {}", rowData, e);
      return Optional.empty();
    }
  }
}
