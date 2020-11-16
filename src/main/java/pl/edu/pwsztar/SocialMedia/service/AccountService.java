package pl.edu.pwsztar.SocialMedia.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pwsztar.SocialMedia.dto.AccountCredentialsDTO;
import pl.edu.pwsztar.SocialMedia.dto.AccountDTO;
import pl.edu.pwsztar.SocialMedia.model.Account;
import pl.edu.pwsztar.SocialMedia.model.Interest;
import pl.edu.pwsztar.SocialMedia.model.Relationship;

import java.util.List;

public interface AccountService {
    boolean createAccount(AccountDTO accountDTO);
    boolean deleteAccount(String login);
    boolean editAccount(String login, AccountDTO newInfo);
    Account logIn(AccountCredentialsDTO accountCredentialsDTO);
    Page<Account> findAccountsByName(Pageable pageable, String forename, String surname);
    Page<Account> findAccountsByCity(Pageable pageable, String city);
    Page<Account> findAccountsByCountry(Pageable pageable, String country);
    Page<Account> findAccountsByInterest(Pageable pageable, Interest interest);
    Page<Account> findAccountsByInterestAndCity(Pageable pageable, Interest interest, String city);
    Page<Account> findAccountsByInterestAndCountry(Pageable pageable, Interest interest, String country);
}