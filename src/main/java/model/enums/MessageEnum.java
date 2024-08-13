package model.enums;

import lombok.Getter;

@Getter
public enum MessageEnum {
  ERROR_MESSAGE_DOES_NOT_MATCH("Error does not match"),
  REQUIRED("Required"),
  INVALID_CREDENTIALS("Invalid credentials");

  private final String value;

  MessageEnum(String value) {
    this.value = value;
  }

  public static MessageEnum fromValue(String input) {
    for (MessageEnum b : MessageEnum.values()) {
      if (b.value.equals(input)) {
        return b;
      }
    }
    return null;
  }
}
