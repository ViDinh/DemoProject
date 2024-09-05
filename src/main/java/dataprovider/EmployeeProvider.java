package dataprovider;

import constants.Constants;
import model.employee.Employee;
import org.testng.annotations.DataProvider;
import utils.JsonArrayConverter;

public class EmployeeProvider {

  private EmployeeProvider() {}

  @DataProvider
  public static Object[][] getCreateEmployeeData() {
    return JsonArrayConverter.covertJsonFileToObjectArray(
        Constants.getDirectorySlash(Constants.TEST_DATA) + "employee/AddEmployee.json", Employee.class);
  }
}
