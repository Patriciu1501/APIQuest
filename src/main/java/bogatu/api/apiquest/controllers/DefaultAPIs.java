package bogatu.api.apiquest.controllers;

import bogatu.api.apiquest.dtos.API.APIDto;
import bogatu.api.apiquest.entities.User;
import bogatu.api.apiquest.mappers.APIMapper;
import bogatu.api.apiquest.services.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping(DefaultAPIs.DEFAULT_APIS_PATH)
@RequiredArgsConstructor
public class DefaultAPIs {

    public static final String DEFAULT_APIS_PATH = "/api/defaultApis";
    public static final String[] apis = {"RandomNumber", "PositiveAffirmation"};
    public static final int RANDOM_NUMBER_SCORE = 1;
    public static final int POSITIVE_AFFIRMATION_SCORE = 1;
    public static final int BST_PRINT_SCORE = 1;
    public static final int BST_ADD_SCORE = 2;


    private final UserService userService;

    @GetMapping("/randomNumber")
    public ResponseEntity<?> randomNumber(@RequestParam(required = false) Integer min,
                                          @RequestParam(required = false) Integer max,
                                          Authentication authentication){
        userService.increaseScore(authentication, RANDOM_NUMBER_SCORE);

        Map<String, ?> body = Map.ofEntries(
                Map.entry("API", apis[0]),
                Map.entry("result", min == null ? new Random().ints().map(i -> i / 100).findFirst().orElseThrow()
                        : new Random().ints(min, max).findFirst().orElseThrow())
        );

        return ResponseEntity.ok(body);
    }


    @GetMapping("/positiveAffirmation")
    public ResponseEntity<?> positiveAffirmation(Authentication authentication){
        userService.increaseScore(authentication, POSITIVE_AFFIRMATION_SCORE);

        String[] affirmations = {"You look great today", "Don't give up", "You remind me of the sun"};

        Map<String, String> body = Map.ofEntries(
                Map.entry("API", apis[1]),
                Map.entry("result", affirmations[new Random().nextInt(3)])
        );

        return ResponseEntity.ok(body);
    }


    public static List<APIDto> appendDefaultApis(User user, APIMapper apiMapper){
       return Stream.concat(user.getApiSet().stream().map(apiMapper::entityToDto),
                        Arrays.stream(DefaultAPIs.apis).map(
                                s -> new APIDto(s, "/api/defaultApis/" + s.substring(0, 1).toLowerCase() + s.substring(1), null, null)
                        ))
                .toList();
    }


}
