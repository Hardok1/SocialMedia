package pl.edu.pwsztar.SocialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwsztar.SocialMedia.model.Account;
import pl.edu.pwsztar.SocialMedia.model.Relationship;

import java.util.List;
import java.util.Optional;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {

    Optional<Relationship> findByUserAAndUserB(Account userA, Account userB);

    Optional<Relationship> findByUserBAndUserA(Account userB, Account userA);

    List<Relationship> findAllByUserAAndStatus(Account userA, String status);

    List<Relationship> findAllByUserBAndStatus(Account userB, String status);

    Optional<Relationship> findByUserAAndUserBAndStatus(Account userA, Account userB, String status);

    Optional<Relationship> findByUserBAndUserAAndStatus(Account userB, Account userA, String status);
}
