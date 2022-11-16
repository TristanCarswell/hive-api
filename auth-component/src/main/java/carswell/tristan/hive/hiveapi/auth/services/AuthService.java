package carswell.tristan.hive.hiveapi.auth.services;

import carswell.tristan.hive.hiveapi.auth.mappers.AuthMapper;
import carswell.tristan.hive.hiveapi.auth.config.JwtTokenUtil;
import carswell.tristan.hive.hiveapi.auth.exceptions.UserAlreadyExistsException;
import carswell.tristan.hive.hiveapi.auth.models.users.User;
import carswell.tristan.hive.hiveapi.auth.repositories.UserRepository;
import carswell.tristan.hive.hiveapi.auth.requests.AuthRequest;
import carswell.tristan.hive.hiveapi.auth.requests.UserAccountRequest;
import carswell.tristan.hive.hiveapi.auth.views.Credentials;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthMapper authMapper;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public final Credentials authenticate(final AuthRequest request) throws BadCredentialsException {
        final var authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmailAddress(),
                                request.getPassword()
                        )
                );

        final var user = (User) authenticate.getPrincipal();
        final var credentials = authMapper.userToCredentials(user);
        credentials.setToken(jwtTokenUtil.generateAccessToken(user));

        return credentials;
    }

    /***
     * Method is responsible for registering a new account for the hive system.
     *
     * @param userRequest represents account request DTO
     * @return success message
     */
    public User registerUserAccount(final UserAccountRequest userRequest) {
        // check if user exists
        final var username = userRequest.getEmailAddress();
        userRepository.findByUsername(username)
                .ifPresent(ignored -> {
                    throw new UserAlreadyExistsException(String.format("Username already exists: %s", username));
                });

        // Create new user's account
        final var userAccount = authMapper.userAccountRequestToUser(userRequest);
        userAccount.setPassword(encoder.encode(userRequest.getPassword()));
        return userRepository.save(userAccount);
    }
}
