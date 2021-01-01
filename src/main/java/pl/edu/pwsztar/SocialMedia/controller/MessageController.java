package pl.edu.pwsztar.SocialMedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwsztar.SocialMedia.dto.MessageDTO;
import pl.edu.pwsztar.SocialMedia.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/message/")
public class MessageController {

    private final MessageService messageService;
    private final FcmController fcmController;

    @Autowired
    public MessageController(MessageService postService, FcmController fcmController) {
        this.messageService = postService;
        this.fcmController = fcmController;
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable int id) {
        return new ResponseEntity<>(messageService.getMessages((long) id), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> addMessage(Authentication authentication, @RequestBody MessageDTO messageDTO){
        if (messageService.addMessage(authentication.getName(), messageDTO.getContent())){
            fcmController.send(messageDTO.getFcmChatId(), messageDTO.getContent());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

}
