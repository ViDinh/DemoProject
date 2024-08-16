package orangehrm.automation.tests.testscript;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import model.enums.ColumnName;
import model.enums.SortTypeEnum;
import orangehrm.automation.tests.basetest.WebBaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.admin.AdminPage;
import pages.common.CommonPage;
import pages.login.LoginPage;
import pages.sidebar.SidebarPage;

@Feature("Admin page")
@Slf4j
public class AdminTest extends WebBaseTest {
  private LoginPage loginPage;
  private SidebarPage sidebarPage;
  private AdminPage adminPage;
  private CommonPage commonPage;

  @BeforeClass
  public void initWebSteps() {
    loginPage = new LoginPage();
    sidebarPage = new SidebarPage();
    adminPage = new AdminPage();
    commonPage = new CommonPage();
  }

  @Test
  @Story("Verify sort by column name")
  public void verifySortByColumnName() {
    loginPage.navigateAndLoginToSystem(
            environmentSetting.get("url").toString(),
            environmentSetting.get("username").toString(),
            environmentSetting.get("password").toString());
    sidebarPage.goToAdminPage();
    adminPage.verifySortByColumnName(ColumnName.USERNAME, SortTypeEnum.ASC);
    adminPage.verifySortByColumnName(ColumnName.USERNAME, SortTypeEnum.DESC);
    adminPage.verifySortByColumnName(ColumnName.USER_ROLE, SortTypeEnum.ASC);
    adminPage.verifySortByColumnName(ColumnName.USER_ROLE, SortTypeEnum.DESC);
    adminPage.verifySortByColumnName(ColumnName.EMPLOYEE_NAME, SortTypeEnum.ASC);
    adminPage.verifySortByColumnName(ColumnName.EMPLOYEE_NAME, SortTypeEnum.DESC);
    adminPage.verifySortByColumnName(ColumnName.STATUS, SortTypeEnum.ASC);
    adminPage.verifySortByColumnName(ColumnName.STATUS, SortTypeEnum.DESC);
  }

  @AfterMethod(alwaysRun = true)
  public void clean(){
    log.info("Logout");
    commonPage.logout();
  }

}
