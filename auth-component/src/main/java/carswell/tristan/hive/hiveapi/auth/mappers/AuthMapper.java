package carswell.tristan.hive.hiveapi.auth.mappers;


import carswell.tristan.hive.hiveapi.auth.models.users.User;
import carswell.tristan.hive.hiveapi.auth.requests.UserAccountRequest;
import carswell.tristan.hive.hiveapi.auth.views.Credentials;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    Credentials userToCredentials(final User user);

    @Mapping(target = "email", source = "emailAddress")
    User userAccountRequestToUser(final UserAccountRequest userAccountRequest);
}
