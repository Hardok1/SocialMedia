package pl.edu.pwsztar.SocialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwsztar.SocialMedia.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
