package pl.edu.pwsztar.SocialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwsztar.SocialMedia.model.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Chat findChatByMember1IdAndMember2Id(Long id1, Long id2);

    boolean existsChatByMember1IdAndMember2Id(Long id1, Long id2);
}
