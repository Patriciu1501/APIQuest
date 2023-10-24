package bogatu.api.apiquest.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserValidatorService {

    public void formatError(Optional<Errors> errors){
        errors.ifPresent(e -> {
            String exceptionMessage = e.getAllErrors()
                    .stream()
                    .map(objErr -> objErr.getObjectName() + " " + objErr.getDefaultMessage())
                    .collect(Collectors.joining("\n"));

            throw new IllegalStateException(exceptionMessage);

            // TODO - Create custom exception for this scenario
        });
    }
}
