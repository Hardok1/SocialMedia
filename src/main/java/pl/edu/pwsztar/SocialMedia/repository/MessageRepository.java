package pl.edu.pwsztar.SocialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwsztar.SocialMedia.model.Chat;
import pl.edu.pwsztar.SocialMedia.model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByChat(Chat chat);
}
