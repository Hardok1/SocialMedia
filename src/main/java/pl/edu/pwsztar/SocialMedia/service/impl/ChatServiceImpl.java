package pl.edu.pwsztar.SocialMedia.service.impl;

import org.springframework.stereotype.Service;
import pl.edu.pwsztar.SocialMedia.model.Account;
import pl.edu.pwsztar.SocialMedia.model.Chat;
import pl.edu.pwsztar.SocialMedia.repository.AccountRepository;
import pl.edu.pwsztar.SocialMedia.repository.ChatRepository;
import pl.edu.pwsztar.SocialMedia.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

    ChatRepository chatRepository;
    AccountRepository accountRepository;

    @Override
    public boolean addChat(String login, Long id) {
        try {
            Account byLogin = accountRepository.findByLogin(login);
            Account one = accountRepository.getOne(id);
            Chat save = chatRepository.save(Chat.builder()
                    .member1(byLogin)
                    .member2(one)
                    .build());
//            Long id1 = byLogin.getId();
//            Long id2 = id;
//            String chatFcmId = id1 > id2 ? (id2 + "," + id1) : (id1 + "," + id2);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
