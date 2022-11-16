package carswell.tristan.hive.hiveapi.auth.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@ApiModel(description = "Represents a request to authenticate a registered account.")
@Getter
@Setter
@AllArgsConstructor
@Builder
@Validated
public class AuthRequest {

    @NotEmpty
    @Email
    @ApiModelProperty(notes = "Email Address/Username of the registered account", required = true)
    private String emailAddress;
    @NotEmpty
    @ApiModelProperty(notes = "Password of the registered account", required = true)
    private String password;

}
