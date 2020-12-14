package pl.edu.pwsztar.SocialMedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.SocialMedia.dto.CommentDTO;
import pl.edu.pwsztar.SocialMedia.service.CommentService;

@RestController
@RequestMapping("/comment/")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("add/{id}")
    public ResponseEntity<?> addComment(Authentication authentication, @PathVariable int id, @RequestBody CommentDTO commentDTO) {
        if (commentService.addComment(authentication.getName(), (long) id, commentDTO.getContent())) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<?> editComment(Authentication authentication, @PathVariable int id, @RequestBody CommentDTO commentDTO) {
        if (commentService.editComment(authentication.getName(), (long) id, commentDTO.getContent())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteComment(Authentication authentication, @PathVariable int id) {
        if (commentService.deleteComment(authentication.getName(), (long) id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
