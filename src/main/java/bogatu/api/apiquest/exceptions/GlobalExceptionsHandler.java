package bogatu.api.apiquest.exceptions;

import bogatu.api.apiquest.errors.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorMessage> handleDuplicateException(DuplicateException exception){
        return new ResponseEntity<>(
                ErrorMessage.builder()
                        .httpStatus(HttpStatus.CONFLICT)
                        .details(Set.of(Map.entry("email", exception.getMessage())))
                        .build(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<ErrorMessage> handleReqValidationException(RequestValidationException exception){
        return new ResponseEntity<>(
                ErrorMessage
                        .builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .details(exception.getFieldDetails().entrySet())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException exception){
        return new ResponseEntity<>(
                ErrorMessage.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .details(Set.of(Map.entry("id",  exception.getMessage())))
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }

}
