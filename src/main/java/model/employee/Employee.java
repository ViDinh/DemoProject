package model.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Employee {
    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("middle_name")
    private String middleName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("employee_id")
    private String employeeId;

    @JsonProperty("emp_picture")
    private String empPicture;

    @JsonProperty("is_create_login_credential")
    private boolean isCreateLoginCredential;

    private String username;
    private String password;

    @JsonProperty("is_active")
    private boolean isActive;

}
