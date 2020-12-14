package pl.edu.pwsztar.SocialMedia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwsztar.SocialMedia.model.Account;
import pl.edu.pwsztar.SocialMedia.model.Interest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByLogin(@NotBlank String login);

    boolean existsByLogin(@NotBlank String login);

    Page<Account> findAllByForenameAndSurname(Pageable pageable, String forename, String surname);

    Page<Account> findAllByCity(Pageable pageable, @NotBlank String city);

    Page<Account> findAllByCountry(Pageable pageable, @NotBlank String country);

    Page<Account> findAllByInterest(Pageable pageable, @NotNull Interest interest);

    Page<Account> findAllByInterestAndCity(Pageable pageable, @NotNull Interest interest, @NotNull String city);

    Page<Account> findAllByInterestAndCountry(Pageable pageable, @NotNull Interest interest, @NotNull String city);
}
