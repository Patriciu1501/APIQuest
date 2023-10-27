package bogatu.api.apiquest.controllers;

import bogatu.api.apiquest.dtos.User.UserRegistrationRequest;
import bogatu.api.apiquest.dtos.User.UserRegistrationResponse;
import bogatu.api.apiquest.services.User.UserServiceImpl;
import bogatu.api.apiquest.services.User.UserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final UserValidatorService validatorService;

    @PostMapping("/users")
    ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody @Validated UserRegistrationRequest request, Errors errors){
        validatorService.formatErrorsIfAny(errors);
        return new ResponseEntity<>(userService.registerUser(request), HttpStatus.CREATED);
    }

}
