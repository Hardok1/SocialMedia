package pl.edu.pwsztar.SocialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwsztar.SocialMedia.model.Interest;

public interface InterestRepository extends JpaRepository<Interest, Long> {

}
