package carswell.tristan.hive.hiveapi.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException(final String exception) {
        super(exception);
    }
}
