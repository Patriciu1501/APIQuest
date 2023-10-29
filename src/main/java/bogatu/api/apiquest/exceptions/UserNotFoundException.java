package bogatu.api.apiquest.exceptions;

import lombok.Getter;


public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }

}
