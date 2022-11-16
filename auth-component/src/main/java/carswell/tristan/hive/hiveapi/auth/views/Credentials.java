package carswell.tristan.hive.hiveapi.auth.views;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "Represents the authenticated credentials of a registered account.")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Credentials {

    @ApiModelProperty("Email address of the authenticated registered account.")
    private String username;
    @ApiModelProperty("Unique token for the authenticated registered account's session.")
    private String token;
}
