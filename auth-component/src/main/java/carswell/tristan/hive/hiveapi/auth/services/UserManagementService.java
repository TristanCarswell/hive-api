package carswell.tristan.hive.hiveapi.auth.services;


import carswell.tristan.hive.hiveapi.auth.exceptions.UserDoesNotExistException;
import carswell.tristan.hive.hiveapi.auth.mappers.UserManagementMapper;
import carswell.tristan.hive.hiveapi.auth.models.users.User;
import carswell.tristan.hive.hiveapi.auth.repositories.UserRepository;
import carswell.tristan.hive.hiveapi.auth.requests.UserAccountRequest;
import carswell.tristan.hive.hiveapi.auth.views.BasicUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional
public class UserManagementService {

    private final UserRepository userRepository;
    private final UserManagementMapper userManagementMapper;

    private static final String COMPANY_DOES_NOT_EXIST = "Company does not exist: %s";
    private static final String USER_DOES_NOT_EXIST = "User does not exist: %s";


    public List<BasicUser> getAllUsers() throws Exception {

        // get list of users by company id.
        final var userList = userRepository.findAll();
        return userManagementMapper.usersToBasicUsers(userList);
    }

    public void deleteUser(final Long userId) throws UserDoesNotExistException {

        // check if user exist in database.
        final var userDetails = findUser(userId);

        userRepository.deleteById(userDetails.getId());
    }

    public User updateUser(
            final Long userId,
            final UserAccountRequest request) throws UserDoesNotExistException {

        // check if user account exist is the database.
        final var user = findUser(userId);

        final var updatedUser = userManagementMapper.updateUserEntity(request, user);
        return userRepository.save(updatedUser);
    }

    // check if user does not exist.
    private User findUser(final Long userId) throws UserDoesNotExistException {
        return userRepository.findById(userId)
                .orElseThrow(
                        () -> new UserDoesNotExistException(String.format(USER_DOES_NOT_EXIST, userId))
                );
    }
}
