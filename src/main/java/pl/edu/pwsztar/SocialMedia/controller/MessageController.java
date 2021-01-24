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
import pl.edu.pwsztar.SocialMedia.repository.AccountRepository;
import pl.edu.pwsztar.SocialMedia.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/message/")
public class MessageController {

    private final AccountRepository accountRepository;
    private final MessageService messageService;
    private final FcmController fcmController;

    @Autowired
    public MessageController(MessageService postService, FcmController fcmController, AccountRepository accountRepository) {
        this.messageService = postService;
        this.fcmController = fcmController;
        this.accountRepository = accountRepository;
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable int id, Authentication authentication) {
        return new ResponseEntity<>(messageService.getMessages((long) id, authentication.getName()), HttpStatus.OK);
    }

    @PostMapping("add/{id}")
    public ResponseEntity<?> addMessage(@PathVariable int id, Authentication authentication, @RequestBody MessageDTO messageDTO) {
        if (messageService.addMessage((long) id, authentication.getName(), messageDTO.getContent())) {
            Long id1 = accountRepository.findByLogin(authentication.getName()).getId();
            String chatFcmId = id1 > id ? (id + "_" + id1) : (id1 + "_" + id);
            fcmController.send(chatFcmId, messageDTO.getContent());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

}
