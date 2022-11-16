package carswell.tristan.hive.hiveapi.auth.controllers.api;


import carswell.tristan.hive.hiveapi.auth.controllers.AuthenticationControllerApi;
import carswell.tristan.hive.hiveapi.auth.models.users.User;
import carswell.tristan.hive.hiveapi.auth.requests.AuthRequest;
import carswell.tristan.hive.hiveapi.auth.requests.UserAccountRequest;
import carswell.tristan.hive.hiveapi.auth.services.AuthService;
import carswell.tristan.hive.hiveapi.auth.views.Credentials;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationControllerApi {

    private final AuthService authService;

    @Override
    public final ResponseEntity<Credentials> authenticate(@RequestBody @Valid final AuthRequest request) {
        final var credentials = authService.authenticate(request);
        return ResponseEntity.ok(credentials);
    }

    /***
     * REST API end-point responsible for registering a new account for the Hive system.
     *
     * @param accountRequest represents account request DTO
     * @return returns http response message
     */
    @Override
    public ResponseEntity<User> registerUserAccount(@Valid @RequestBody final UserAccountRequest accountRequest) {
        return ResponseEntity.ok(authService.registerUserAccount(accountRequest));
    }
}
