package bogatu.api.apiquest.controllers;

import bogatu.api.apiquest.dtos.UserRegistrationRequest;
import bogatu.api.apiquest.services.UserService;
import bogatu.api.apiquest.services.UserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserValidatorService validatorService;

    @PostMapping("/users")
    ResponseEntity<String> registerUser(@RequestBody @Validated UserRegistrationRequest request, Errors errors){
        validatorService.formatError(Optional.ofNullable(errors));
        userService.registerUser(request);
        return new ResponseEntity<>("Successfully registered", HttpStatus.CREATED);
    }


}
