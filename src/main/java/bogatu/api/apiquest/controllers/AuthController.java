package bogatu.api.apiquest.controllers;

import bogatu.api.apiquest.dtos.User.UserRegistrationRequest;
import bogatu.api.apiquest.dtos.User.UserRegistrationResponse;
import bogatu.api.apiquest.services.User.UserService;
import bogatu.api.apiquest.services.User.UserValidatorService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserValidatorService validatorService;


    @PostMapping("/register")
    ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody @Validated UserRegistrationRequest request, Errors errors){
        validatorService.formatErrorsIfAny(errors);
        return new ResponseEntity<>(userService.registerUser(request), HttpStatus.CREATED);
    }


    @GetMapping("/login")
    ResponseEntity<?> login(Authentication authentication){
        Map<String, String> mapForFailedScenario = Map.ofEntries(
                Map.entry(
                        "Attempt-date", LocalDateTime.now().format(DateTimeFormatter.ofPattern(
                                "dd MMM, 'at' HH:mm"
                        ))
                ),
                Map.entry("Message", "Failed to log in")
        );

        return authentication != null ? ResponseEntity.ok(authentication.getDetails())
                : ResponseEntity.badRequest().body(mapForFailedScenario);
    }


    @GetMapping("/refreshToken")
    ResponseEntity<?> refreshToken(Authentication authentication){
        return ResponseEntity.ok(authentication.getDetails());
    }

}