package orangehrm.automation.tests.testscript;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import model.enums.ColumnName;
import model.enums.SortTypeEnum;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.admin.AdminPage;
import pages.login.LoginPage;
import pages.sidebar.SidebarPage;

import orangehrm.automation.tests.basetest.WebBaseTest;

@Feature("Admin page")
@Slf4j
public class AdminTest extends WebBaseTest {
  private LoginPage loginPage;
  private SidebarPage sidebarPage;
  private AdminPage adminPage;

  @BeforeClass
  public void initWebSteps() {
    loginPage = new LoginPage();
    sidebarPage = new SidebarPage();
    adminPage = new AdminPage();
  }

  @Test
  @Story("Verify sort by column name")
  public void verifySortByColumnName() {
    loginPage.navigateAndLoginToSystem(
        environmentSetting.get("username").toString(),
        environmentSetting.get("password").toString());
    sidebarPage.goToAdminPage();
    adminPage.verifySortByColumnName(ColumnName.USERNAME, SortTypeEnum.ASC);
  }
}
