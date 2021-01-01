package pl.edu.pwsztar.SocialMedia.service;

import pl.edu.pwsztar.SocialMedia.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    boolean addMessage(String name, String content);

    List<MessageDTO> getMessages(long id);
}
