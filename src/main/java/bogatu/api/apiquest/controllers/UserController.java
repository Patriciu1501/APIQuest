package bogatu.api.apiquest.controllers;

import bogatu.api.apiquest.dtos.UserRegistrationRequest;
import bogatu.api.apiquest.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/users")
    ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request){
        userService.registerUser(request);
        return new ResponseEntity<>("Successfully registered", HttpStatus.CREATED);
    }


}
