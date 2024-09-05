package pages.PIM;

import basepage.WebBasePage;
import driver.web.DriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.common.CommonPage;

public class EmployeeListPage extends WebBasePage {
    @FindBy(xpath = "//button[text()=' Add ']")
    WebElement btnAdd;

    private CommonPage commonPage;
    public EmployeeListPage() {
        super();
        commonPage = new CommonPage();
        PageFactory.initElements(DriverManager.getDriver(), this);
    }

    @Step("Click to Add button")
    public void clickToAddButton() {
        clickElement(btnAdd, "Add button");
        commonPage.waitSpinner();
    }


}
