package bogatu.api.apiquest.controllers;

import bogatu.api.apiquest.dtos.API.APIDto;
import bogatu.api.apiquest.entities.User;
import bogatu.api.apiquest.mappers.APIMapper;
import bogatu.api.apiquest.services.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/defaultApis")
@RequiredArgsConstructor
public class DefaultAPIs {

    public static final String[] apis = {"RandomNumber", "PositiveAffirmation"};

    private final UserService userService;

    @GetMapping("/randomNumber")
    public ResponseEntity<?> randomNumber(@RequestParam(required = false) Integer min,
                                          @RequestParam(required = false) Integer max,
                                          Authentication authentication){
        userService.increaseScore(authentication);

        Map<String, ?> body = Map.ofEntries(
                Map.entry("API", apis[0]),
                Map.entry("result", min == null ? new Random().ints().map(i -> i / 100).findFirst().orElseThrow()
                        : new Random().ints(min, max).findFirst().orElseThrow())
        );

        return ResponseEntity.ok(body);
    }


    @GetMapping("/positiveAffirmation")
    public ResponseEntity<?> positiveAffirmation(Authentication authentication){
        userService.increaseScore(authentication);

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


    @RestController
    @RequestMapping("/api/defaultApis/binaryTree")
    static class BinaryTreeGame{

        static class BinaryTree{

            static class Node{
                int value;
                Node left;
                Node right;

                public Node(int value, Node left, Node right){
                    this.value = value;
                    this.left = left;
                    this.right = right;
                }
            }

            private Node head;

            public boolean addNode(int value){
                return false;
            }


            private void addNode(Node head, int value){
                if(head == null) return;


            }
        }

        private final BinaryTree binaryTree = new BinaryTree();


        // define request mappings for binary tree operations

    }
}
