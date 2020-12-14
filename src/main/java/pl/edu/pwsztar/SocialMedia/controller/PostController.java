package pl.edu.pwsztar.SocialMedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.SocialMedia.dto.PostDTO;
import pl.edu.pwsztar.SocialMedia.dto.PostDetailsDTO;
import pl.edu.pwsztar.SocialMedia.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/post/")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("getAccountPosts/{id}")
    public ResponseEntity<List<PostDTO>> getAccountPosts(@PathVariable int id, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return new ResponseEntity<>(postService.getAccountPosts(pageable, (long) id), HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<PostDetailsDTO> getPostDetails(@PathVariable int id) {
        return new ResponseEntity<>(postService.getPost((long) id), HttpStatus.OK);
    }

//    @GetMapping("test")
//    public ResponseEntity<?> testAdd(){
//        if (postService.addPost("tester1", "i love testing")){
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>(HttpStatus.CONFLICT);
//    }

    @PostMapping("add")
    public ResponseEntity<?> addPost(Authentication authentication, @RequestBody PostDTO postDTO){
        if (postService.addPost(authentication.getName(), postDTO.getContent())){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<?> editPost(Authentication authentication, @RequestBody PostDTO postDTO, @PathVariable int id){
        if (postService.editPost(authentication.getName(), (long) id, postDTO.getContent())){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deletePost(Authentication authentication, @PathVariable int id){
        if (postService.deletePost(authentication.getName(),(long) id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

}
