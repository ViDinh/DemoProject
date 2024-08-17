package model.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Search {
    private String username;

    @JsonProperty("user_role")
    private String userRole;

    @JsonProperty("employee_name")
    private String employeeName;

    private String status;
}
