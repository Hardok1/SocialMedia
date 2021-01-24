package pl.edu.pwsztar.SocialMedia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.SocialMedia.model.Account;
import pl.edu.pwsztar.SocialMedia.model.Chat;
import pl.edu.pwsztar.SocialMedia.repository.AccountRepository;
import pl.edu.pwsztar.SocialMedia.repository.ChatRepository;
import pl.edu.pwsztar.SocialMedia.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository, AccountRepository accountRepository) {
        this.chatRepository = chatRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean addChat(String login, Long id2) {
        try {
            Account member1 = accountRepository.findByLogin(login);
            Account member2 = accountRepository.getOne(id2);
            Long id1 = member1.getId();
            if (chatRepository.existsChatByMember1IdAndMember2Id(id1 > id2 ? id2 : id1, id1 > id2 ? id1 : id2)) {
                return true;
            }
            chatRepository.save(Chat.builder()
                    .member1(id1 > id2 ? member2 : member1)
                    .member2(id1 > id2 ? member1 : member2)
                    .build());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
