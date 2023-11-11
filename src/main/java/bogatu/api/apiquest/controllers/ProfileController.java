package bogatu.api.apiquest.controllers;

import bogatu.api.apiquest.dtos.API.APIDto;
import bogatu.api.apiquest.dtos.User.UserInfo;
import bogatu.api.apiquest.services.API.APIService;
import bogatu.api.apiquest.services.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final APIService apiService;
    private final UserService userService;

    @GetMapping("/apis")
    public ResponseEntity<List<APIDto>> getMyAPIs(Authentication authentication){
        return new ResponseEntity<>(apiService.getMyAPIs(authentication), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<UserInfo> getMyProfile(Authentication authentication){
        return ResponseEntity.ok(userService.getMyProfile(authentication));
    }
}
