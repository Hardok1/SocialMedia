package pl.edu.pwsztar.SocialMedia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwsztar.SocialMedia.model.Account;
import pl.edu.pwsztar.SocialMedia.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByOriginalPosterOrderByCreatedAtDesc(Pageable pageable, Account originalPoster);
}
