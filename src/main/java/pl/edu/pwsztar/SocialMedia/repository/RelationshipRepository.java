package pl.edu.pwsztar.SocialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwsztar.SocialMedia.model.Account;
import pl.edu.pwsztar.SocialMedia.model.Relationship;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    Relationship findAllByUserA(Account userA);

    Relationship findAllByUserB(Account userB);

    Relationship findAllByStatus(String status);
}
