package pl.edu.pwsztar.SocialMedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwsztar.SocialMedia.model.Account;
import pl.edu.pwsztar.SocialMedia.model.Interest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByLogin(@NotBlank String login);

    boolean existsByLogin(@NotBlank String login);

    void deleteByLogin(String login);

    List<Account> findAllByCity(@NotBlank String city);

    List<Account> findAllByCountry(@NotBlank String country);

    List<Account> findAllByInterest(@NotNull Interest interest);
}
