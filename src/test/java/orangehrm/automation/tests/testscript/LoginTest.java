package orangehrm.automation.tests.testscript;

import dataprovider.LoginProvider;
import lombok.extern.slf4j.Slf4j;
import model.login.ExtendCredential;
import orangehrm.automation.tests.basetest.WebBaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.login.LoginPage;

@Slf4j
public class LoginTest extends WebBaseTest {

  private LoginPage loginPage;

  @BeforeClass
  public void initWebSteps() {
    loginPage = new LoginPage();
  }

  @Test(dataProvider = "getLoginNegativeCases", dataProviderClass = LoginProvider.class)
  public void testNegativeLogin(ExtendCredential credential) {
    loginPage.navigateAndLoginToSystem(
        environmentSetting.get("url").toString(),
        credential.getUsername(),
        credential.getPassword());
    loginPage.verifyLoginWithNegativeCase(credential);
  }
}
