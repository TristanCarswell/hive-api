package carswell.tristan.hive.hiveapi.auth.controllers;


import carswell.tristan.hive.hiveapi.auth.config.SpringFoxConfig;
import carswell.tristan.hive.hiveapi.auth.models.users.User;
import carswell.tristan.hive.hiveapi.auth.requests.AuthRequest;
import carswell.tristan.hive.hiveapi.auth.requests.UserAccountRequest;
import carswell.tristan.hive.hiveapi.auth.views.Credentials;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/api")
@Api(tags = {SpringFoxConfig.AUTH_TAG})
public interface AuthenticationControllerApi {

    @PostMapping(
            path = "authenticate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation("Attempts to authenticate the provided credentials with a registered hive account.")
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Successfully authenticated the provided details and generated an authentication token."
            ),
            @ApiResponse(
                    code = 401,
                    message = "The credentials provided could not be authenticated with a registered account."
            ),
            @ApiResponse(
                    code = 400,
                    message = "The credentials provided are missing vital information to complete the request."
            )
    })
    ResponseEntity<Credentials> authenticate(
            @ApiParam("The provided credentials to authenticate.")
            @RequestBody @Valid final AuthRequest request
    );

    @PostMapping(
            path = "register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiOperation("Attempts to register a new Hive account with the provided User and Company details.")
    @ApiResponses({
            @ApiResponse(
                    code = 200,
                    message = "Successfully registered a new Hive account with the details provided."
            ),
            @ApiResponse(
                    code = 400,
                    message = "The details provided are missing vital information to complete the request."
            ),
            @ApiResponse(
                    code = 422,
                    message = "The Email Address of the User belongs to an existing registered account."
            )
    })
    ResponseEntity<User> registerUserAccount(
            @ApiParam("The User and Company details to attempt to register a new account")
            @Valid @RequestBody final UserAccountRequest accountRequest
    );

}
