package bogatu.api.apiquest.controllers;

import bogatu.api.apiquest.config.APIQuestAuthProvider;
import bogatu.api.apiquest.dtos.User.UserRegistrationRequest;
import bogatu.api.apiquest.dtos.User.UserRegistrationResponse;
import bogatu.api.apiquest.services.User.UserService;
import bogatu.api.apiquest.services.User.UserValidatorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
//    private final APIQuestAuthProvider authProvider;
    private final UserValidatorService validatorService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody @Validated UserRegistrationRequest request, Errors errors){
        validatorService.formatErrorsIfAny(errors);
        var requestWithEncodedPass = new UserRegistrationRequest(
                request.username(),
                passwordEncoder.encode(request.password()),
                request.email(),
                request.createdAt()
        );
        return new ResponseEntity<>(userService.registerUser(requestWithEncodedPass), HttpStatus.CREATED);
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

        return authentication != null ? ResponseEntity.ok("Logged in")
                : ResponseEntity.badRequest().body(mapForFailedScenario);
    }


}