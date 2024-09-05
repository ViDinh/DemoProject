package orangehrm.automation.tests.testscript;

import dataprovider.EmployeeProvider;
import io.qameta.allure.Story;
import lombok.extern.slf4j.Slf4j;
import model.employee.Employee;
import orangehrm.automation.tests.basetest.WebBaseTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.PIM.AddEmployeePage;
import pages.PIM.EmployeeDetailsPage;
import pages.PIM.EmployeeListPage;
import pages.common.CommonPage;
import pages.login.LoginPage;
import pages.sidebar.SidebarPage;
import utils.RandomUtils;

@Slf4j
public class EmployeeTest extends WebBaseTest {
  private LoginPage loginPage;
  private SidebarPage sidebarPage;
  private EmployeeListPage employeeListPage;
  private AddEmployeePage addEmployeePage;
  private EmployeeDetailsPage employeeDetailsPage;
  private CommonPage commonPage;
  private long employeeId;

  @BeforeClass
  public void initWebSteps() {
    loginPage = new LoginPage();
    sidebarPage = new SidebarPage();
    employeeListPage = new EmployeeListPage();
    addEmployeePage = new AddEmployeePage();
    employeeDetailsPage = new EmployeeDetailsPage();
    commonPage = new CommonPage();
  }

  @BeforeMethod
  public void loginToSystemAndGoToPIMPage() {
    loginPage.navigateAndLoginToSystem(
        environmentSetting.get("url").toString(),
        environmentSetting.get("username").toString(),
        environmentSetting.get("password").toString());
    sidebarPage.goToPIMPage();
    employeeListPage.clickToAddButton();
  }

  @Test(dataProvider = "getCreateEmployeeData", dataProviderClass = EmployeeProvider.class)
  @Story("Create employee")
  public void createEmployee(Employee employee) {
    employeeId = RandomUtils.generateFiveDigitNumber();
    addEmployeePage.uploadAvatar(employee.getEmpPicture());
    addEmployeePage.inputFirstName(employee.getFirstName());
    addEmployeePage.inputMiddleName(employee.getMiddleName());
    addEmployeePage.inputLastName(employee.getLastName());
    addEmployeePage.inputEmployeeId(employeeId);
    addEmployeePage.inputCredentialLogin(employee);
    addEmployeePage.clickSaveButton();
    employeeDetailsPage.verifyTextInFirstName(employee.getFirstName());
    employeeDetailsPage.verifyTextInMiddleName(employee.getMiddleName());
    employeeDetailsPage.verifyTextInLastName(employee.getLastName());
    employeeDetailsPage.verifyTextInEmployeeId(employeeId);
    employeeDetailsPage.verifyAvatar(employee.getEmpPicture());
  }

  @AfterMethod(alwaysRun = true)
  public void clean(){
    log.info("Logout");
    commonPage.logout();
  }
}
