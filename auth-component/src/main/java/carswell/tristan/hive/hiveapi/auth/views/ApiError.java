package carswell.tristan.hive.hiveapi.auth.views;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(description = "Represents a generic response for any handled API errors.")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApiError {

    @ApiModelProperty("Message to explain why the error occurred.")
    private String message;
}
