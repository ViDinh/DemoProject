package model.enums;

import lombok.Getter;

@Getter
public enum ColumnNameAdminPage {
  USERNAME("Username", 2),
  USER_ROLE("User Role", 3),
  EMPLOYEE_NAME("Employee Name", 4),
  STATUS("Status", 5);
  private final String columnName;
  private final int columnPosition;

  ColumnNameAdminPage(String columnName, int columnPosition) {
    this.columnName = columnName;
    this.columnPosition = columnPosition;
  }

  public static ColumnNameAdminPage fromValue(String input) {
    for (ColumnNameAdminPage b : ColumnNameAdminPage.values()) {
      if (b.columnName.equals(input)) {
        return b;
      }
    }
    return null;
  }
}
