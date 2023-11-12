package bogatu.api.apiquest.controllers;

import bogatu.api.apiquest.services.User.UserService;
import com.sun.source.tree.BinaryTree;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


// make these endpoints available only for users with score >= 10
@RestController
@RequestMapping(BSTGameController.BST_GAME_PATH)
@RequiredArgsConstructor
@PreAuthorize("authentication.details >= 10")
public class BSTGameController {

    private final UserService userService;
    public static final String BST_GAME_PATH = DefaultAPIs.DEFAULT_APIS_PATH + "/BST";

    static class BST{

        static class Node{

            private final int value;
            private final int level;
            private Node left;
            private Node right;

            public Node(int value, Node left, Node right, int level){
                this.value = value;
                this.left = left;
                this.right = right;
                this.level = level;
            }
        }

        private Node head;

        public void addNode(int value){
            if(this.head == null) head = new Node(value, null, null, 0);
            else addNode(head, null, false, value);
        }


        public String getTree(){
            if(head == null) return "";

            // I've used this to make the level detection easier
            Node lastAppended = null;
            StringBuilder storeBT = new StringBuilder();

            Queue<Node> queue = new ArrayDeque<>();

            queue.offer(this.head);

            while(!queue.isEmpty()){
                Node removed = queue.poll();

                if(lastAppended != null && removed.level > lastAppended.level) storeBT.append("\n");

                storeBT.append(removed.value).append(" ");

                lastAppended = removed;

                if(removed.left != null) queue.offer(removed.left);
                if(removed.right != null) queue.offer(removed.right);
            }

            return storeBT.toString();
        }




        private void addNode(Node curr, Node prev, boolean goLeft, int value){
            if(curr == null) {
                Node toInsert = new Node(value, null, null, prev.level + 1);
                if(goLeft) prev.left = toInsert;
                else prev.right = toInsert;

                return;
            }

            if(value > curr.value) addNode(curr.right, curr, false, value);
            else addNode(curr.left, curr, true, value);

        }
    }

    private final BST binaryTree = new BST();


    @GetMapping
    ResponseEntity<?> getBinaryTree(Authentication authentication){
        userService.increaseScore(authentication, DefaultAPIs.BST_PRINT_SCORE);

        Map<String, String> body = new LinkedHashMap<>();

        var levels = binaryTree.getTree();
        AtomicInteger level = new AtomicInteger(0);

        Arrays.stream(levels.split("\n"))
                .forEach(s -> body.put("Level " + level.getAndIncrement(), s.stripTrailing()));

        return ResponseEntity.ok(levels.isEmpty() ? "Empty BT" : body);
    }


    @PostMapping
    ResponseEntity<?> addNode(@RequestParam int node, Authentication authentication){
        userService.increaseScore(authentication, DefaultAPIs.BST_ADD_SCORE);
        binaryTree.addNode(node);
        return ResponseEntity.ok("Added " + node);
    }

}
