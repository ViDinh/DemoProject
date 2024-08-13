package model.enums;

import lombok.Getter;

@Getter
public enum ColumnName {
  USERNAME("Username", 2),
  USER_ROLE("User Role", 3),
  EMPLOYEE_NAME("Employee Name", 4),
  STATUS("Status", 5);
  private final String columnName;
  private final int columnPosition;

  ColumnName(String columnName, int columnPosition) {
    this.columnName = columnName;
    this.columnPosition = columnPosition;
  }

  public static ColumnName fromValue(String input) {
    for (ColumnName b : ColumnName.values()) {
      if (b.columnName.equals(input)) {
        return b;
      }
    }
    return null;
  }
}
