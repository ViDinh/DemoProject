package dataprovider;

import constants.Constants;
import model.admin.Search;
import org.testng.annotations.DataProvider;
import utils.JsonArrayConverter;

public class AdminProvider {
    
  private AdminProvider() {}

  @DataProvider
  public static Object[][] getSearchData() {
    return JsonArrayConverter.covertJsonFileToObjectArray(
        Constants.getDirectorySlash(Constants.TEST_DATA) + "SearchInAdminPage.json", Search.class);
  }
}
