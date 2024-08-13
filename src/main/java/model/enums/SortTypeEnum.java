package model.enums;

public enum SortTypeEnum {
  ASC("asc"),
  DESC("desc"),
  DEFAULT(null);

  private final String type;

  SortTypeEnum(final String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return type;
  }
}
