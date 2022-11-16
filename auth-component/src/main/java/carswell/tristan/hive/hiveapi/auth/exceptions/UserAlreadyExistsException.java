package carswell.tristan.hive.hiveapi.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(final String exception) {
        super(exception);
    }
}
