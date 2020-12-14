package pl.edu.pwsztar.SocialMedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.SocialMedia.dto.PublicAccountInfo;
import pl.edu.pwsztar.SocialMedia.service.RelationshipService;

import java.util.List;

@RestController
@RequestMapping("/relationship/")
public class RelationshipController {
    private final RelationshipService relationshipService;

    @Autowired
    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @GetMapping("test")
    public ResponseEntity<?> reltest(){
        //ExpiredJwtException
        if (relationshipService.acceptFriendRequest((long) 14)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("sendRequest/{id}")
    public ResponseEntity<?> sendFriendRequest(Authentication authentication, @PathVariable int id) {
        if (relationshipService.sendFriendRequest(authentication.getName(), (long) id)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("acceptRequest/{id}")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable int id) {
        if (relationshipService.acceptFriendRequest((long) id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PostMapping("removeRelationship/{id}")
    public ResponseEntity<?> removeRelationship(@PathVariable int id) {
        if (relationshipService.removeRelationship((long) id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("checkFriendship/{id}")
    public ResponseEntity<?> checkFriendship(Authentication authentication, @PathVariable int id) {
        if (relationshipService.isFriend(authentication.getName(), (long) id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("getFriendsList")
    public ResponseEntity<List<PublicAccountInfo>> getFriendsList(Authentication authentication) {
        return new ResponseEntity<>(relationshipService.getFriendsList(authentication.getName()), HttpStatus.OK);
    }

    @GetMapping("getReceivedRequests")
    public ResponseEntity<List<PublicAccountInfo>> getReceivedRequests(Authentication authentication) {
        return new ResponseEntity<>(relationshipService.getReceivedFriendRequests(authentication.getName()), HttpStatus.OK);
    }

    @GetMapping("getSentRequests")
    public ResponseEntity<List<PublicAccountInfo>> getSentRequests(Authentication authentication) {
        return new ResponseEntity<>(relationshipService.getSentFriendRequests(authentication.getName()), HttpStatus.OK);
    }
}
