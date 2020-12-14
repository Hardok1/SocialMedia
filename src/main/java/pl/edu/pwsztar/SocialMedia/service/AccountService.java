package pl.edu.pwsztar.SocialMedia.service;

import org.springframework.data.domain.Pageable;
import pl.edu.pwsztar.SocialMedia.dto.AccountDTO;
import pl.edu.pwsztar.SocialMedia.dto.AccountDetailsDTO;
import pl.edu.pwsztar.SocialMedia.dto.PublicAccountInfo;

import java.util.List;

public interface AccountService {

    boolean createAccount(AccountDTO accountDTO);

    boolean removeAccount(String login);

    boolean editAccount(String login, AccountDTO newInfo);

    AccountDetailsDTO getAccountDetails(Long id);

    List<PublicAccountInfo> findAccountsByName(Pageable pageable, String forename, String surname);

    List<PublicAccountInfo> findAccountsByCity(Pageable pageable, String city);

    List<PublicAccountInfo> findAccountsByCountry(Pageable pageable, String country);

    List<PublicAccountInfo> findAccountsByInterest(Pageable pageable, String interest);

    List<PublicAccountInfo> findAccountsByInterestAndCity(Pageable pageable, String interest, String city);

    List<PublicAccountInfo> findAccountsByInterestAndCountry(Pageable pageable, String interest, String country);
}