package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString(callSuper = true)
public class ExtendCredential extends Credential implements Serializable {
  @JsonProperty("is_wrong_password")
  private boolean isWrongPassword;

  @JsonProperty("is_wrong_username")
  private boolean isWrongUsername;
}
