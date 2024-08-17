package orangehrm.automation.tests.testscript;

import dataprovider.AdminProvider;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import model.admin.Search;
import model.enums.ColumnNameAdminPage;
import model.enums.SortTypeEnum;
import orangehrm.automation.tests.basetest.WebBaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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

  @BeforeMethod
  public void loginToSystemAndGoToAdminPage(){
    loginPage.navigateAndLoginToSystem(
            environmentSetting.get("url").toString(),
            environmentSetting.get("username").toString(),
            environmentSetting.get("password").toString());
    sidebarPage.goToAdminPage();
  }

  @Test
  @Story("Verify sort by column name")
  public void verifySortByColumnName() {
    adminPage.verifySortByColumnName(ColumnNameAdminPage.USERNAME, SortTypeEnum.ASC);
    adminPage.verifySortByColumnName(ColumnNameAdminPage.USERNAME, SortTypeEnum.DESC);
    adminPage.verifySortByColumnName(ColumnNameAdminPage.USER_ROLE, SortTypeEnum.ASC);
    adminPage.verifySortByColumnName(ColumnNameAdminPage.USER_ROLE, SortTypeEnum.DESC);
    adminPage.verifySortByColumnName(ColumnNameAdminPage.EMPLOYEE_NAME, SortTypeEnum.ASC);
    adminPage.verifySortByColumnName(ColumnNameAdminPage.EMPLOYEE_NAME, SortTypeEnum.DESC);
    adminPage.verifySortByColumnName(ColumnNameAdminPage.STATUS, SortTypeEnum.ASC);
    adminPage.verifySortByColumnName(ColumnNameAdminPage.STATUS, SortTypeEnum.DESC);
  }

  @Test(dataProvider = "getSearchData", dataProviderClass = AdminProvider.class)
  @Story("Verify search in admin page")
  public void verifySearch(Search search) {
    adminPage.searchUsers(search);
    List<Search> valueAfterSearch = adminPage.getRowValues();
    adminPage.verifyValueAfterSearch(search, valueAfterSearch);
  }

  @AfterMethod(alwaysRun = true)
  public void clean(){
    log.info("Logout");
    commonPage.logout();
  }

}
