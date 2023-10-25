package bogatu.api.apiquest.services;

import bogatu.api.apiquest.exceptions.RequestValidationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserValidatorService {

    public void formatErrorsIfAny(Errors errors){
        Map<String, String> collectedErrors = errors.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        if(!collectedErrors.isEmpty()) throw new RequestValidationException(collectedErrors);
    }
}
