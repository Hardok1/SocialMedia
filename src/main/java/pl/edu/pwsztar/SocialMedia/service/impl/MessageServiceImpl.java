package pl.edu.pwsztar.SocialMedia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.SocialMedia.dto.MessageDTO;
import pl.edu.pwsztar.SocialMedia.model.Chat;
import pl.edu.pwsztar.SocialMedia.model.Message;
import pl.edu.pwsztar.SocialMedia.repository.AccountRepository;
import pl.edu.pwsztar.SocialMedia.repository.ChatRepository;
import pl.edu.pwsztar.SocialMedia.repository.MessageRepository;
import pl.edu.pwsztar.SocialMedia.service.MessageService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Service
public class MessageServiceImpl implements MessageService {

    final MessageRepository messageRepository;
    final AccountRepository accountRepository;
    final ChatRepository chatRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, AccountRepository accountRepository, ChatRepository chatRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
        this.chatRepository = chatRepository;
    }

    @Override
    public boolean addMessage(String name, String content) {
        try {
            messageRepository.save(Message.builder()
                    .createdAt(Calendar.getInstance(TimeZone.getTimeZone("Poland")))
                    .content(content)
                    .sender(accountRepository.findByLogin(name))
                    .build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<MessageDTO> getMessages(long id) {
        Chat one = chatRepository.getOne(id);
        List<Message> allByChat = messageRepository.findAllByChat(one);
        List<MessageDTO> messageDTOs = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Long id1 = one.getMember1().getId();
        Long id2 = one.getMember2().getId();
        String chatFcmId = id1 > id2 ? (id2 + "," + id1) : (id1 + "," + id2);
        for (Message message : allByChat) {
            messageDTOs.add(new MessageDTO(chatFcmId, message.getContent(), formatter.format(message.getCreatedAt().getTime()), message.getSender().getForename() + message.getSender().getSurname()));
        }
        return messageDTOs;
    }
}
