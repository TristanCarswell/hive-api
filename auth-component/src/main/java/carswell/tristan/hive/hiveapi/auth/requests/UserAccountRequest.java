package carswell.tristan.hive.hiveapi.auth.requests;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@Validated
@ApiModel(description = "Represents a request to register a new Hive account.")
public class UserAccountRequest {

    @NotEmpty
    @ApiModelProperty(notes = "Full Name of the new account.", required = true)
    private String fullName;
    @NotEmpty
    @Email
    @ApiModelProperty(notes = "Email Address/Username of the new account.", required = true)
    private String emailAddress;
    @NotEmpty
    @ApiModelProperty(notes = "Password of the new account.", required = true)
    private String password;
}
