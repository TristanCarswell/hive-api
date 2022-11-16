package carswell.tristan.hive.hiveapi.auth.views;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "Represents a simple registered user with minimal information.")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BasicUser {

    @ApiModelProperty("Full Name of the registered account.")
    private String fullName;
    @ApiModelProperty("Contact Number of the registered account.")
    private String contactNumber;
    @ApiModelProperty("Username/Email Address of the registered account.")
    private String emailAddress;
    @ApiModelProperty("Role of the registered account.")
    private String userRole;
    @ApiModelProperty("Status of the registered account.")
    private String status;

}
