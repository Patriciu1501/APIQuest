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
                        .message(ErrorMessage.INVALID_DATA_MESSAGE)
                        .details(Set.of(Map.entry("Email", exception.getMessage())))
                        .build(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<ErrorMessage> handleReqValidationException(RequestValidationException exception){
        return new ResponseEntity<>(
                ErrorMessage.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(ErrorMessage.INVALID_DATA_MESSAGE)
                        .details(exception.getFieldDetails().entrySet())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

}
