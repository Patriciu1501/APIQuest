package bogatu.api.apiquest.controllers;

import bogatu.api.apiquest.dtos.User.*;
import bogatu.api.apiquest.entities.User;
import bogatu.api.apiquest.services.User.UserService;
import bogatu.api.apiquest.services.User.UserServiceImpl;
import bogatu.api.apiquest.services.User.UserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.beans.beancontext.BeanContext;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserValidatorService validatorService;


    @PostMapping
    ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody @Validated UserRegistrationRequest request, Errors errors){
        validatorService.formatErrorsIfAny(errors);
        return new ResponseEntity<>(userService.registerUser(request), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    ResponseEntity<UserUpdateDTO> updateUser(@RequestBody @Validated UserUpdateDTO request, Errors errors,
                                                       @PathVariable int id){
        validatorService.formatErrorsIfAny(errors);
        return new ResponseEntity<>(userService.updateUser(request, id), HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<UserInfo>> getAllUsers(@RequestParam(defaultValue = "-1") int pageNumber){
        return new ResponseEntity<>(userService.getAllUsers(pageNumber), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserInfo> getUser(@PathVariable int id){
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.FOUND);
    }
}
