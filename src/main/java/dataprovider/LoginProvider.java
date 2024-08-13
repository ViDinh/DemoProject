package dataprovider;

import constants.Constants;
import model.ExtendCredential;
import org.testng.annotations.DataProvider;
import utils.JsonArrayConverter;

public class LoginProvider {

  private LoginProvider() {}

  @DataProvider
  public static Object[][] getLoginNegativeCases() {
    return JsonArrayConverter.covertJsonFileToObjectArray(
        Constants.getDirectorySlash(Constants.TEST_DATA) + "Login.json", ExtendCredential.class);
  }
}
