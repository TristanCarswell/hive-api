package carswell.tristan.hive.hiveapi.auth.mappers;


import carswell.tristan.hive.hiveapi.auth.models.users.User;
import carswell.tristan.hive.hiveapi.auth.requests.UserAccountRequest;
import carswell.tristan.hive.hiveapi.auth.views.BasicUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserManagementMapper {

    @Mapping(target = "emailAddress", source = "username")
    List<BasicUser> usersToBasicUsers(final List<User> users);

    BasicUser userToBasicUser(final User user);

    @Mapping(target = "username", source = "emailAddress")
    User updateUserEntity(UserAccountRequest userAccountRequest, @MappingTarget User user);
}
