package pl.edu.pwsztar.SocialMedia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.SocialMedia.dto.AccountCredentialsDTO;
import pl.edu.pwsztar.SocialMedia.dto.AccountDTO;
import pl.edu.pwsztar.SocialMedia.exception.InvalidCredentialsException;
import pl.edu.pwsztar.SocialMedia.exception.LoginAlreadyExistException;
import pl.edu.pwsztar.SocialMedia.model.Account;
import pl.edu.pwsztar.SocialMedia.model.Interest;
import pl.edu.pwsztar.SocialMedia.repository.AccountRepository;
import pl.edu.pwsztar.SocialMedia.service.AccountService;

import java.util.Calendar;
import java.util.TimeZone;

@Service
public class AccountServiceImpl implements AccountService {

    final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean createAccount(AccountDTO accountDTO) throws LoginAlreadyExistException {
        if (accountRepository.existsByLogin(accountDTO.getLogin())) return false;
        Account account = new Account(accountDTO);
        account.setCreatedAt(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
        accountRepository.save(account);
        return true;
    }

    @Override
    public boolean deleteAccount(String login) {
        if (accountRepository.existsByLogin(login)) {
            accountRepository.deleteByLogin(login);
            return true;
        }
        return false;
    }

    @Override
    public boolean editAccount(String login, AccountDTO newInfo) {
        try {
            Account account = accountRepository.findByLogin(login);
            account.setForename(newInfo.getForename());
            account.setSurname(newInfo.getSurname());
            account.setCity(newInfo.getCity());
            account.setCountry(newInfo.getCountry());
            account.setPassword(newInfo.getPassword());
            account.setInterest(newInfo.getInterests());
            accountRepository.save(account);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Account logIn(AccountCredentialsDTO accountCredentialsDTO) throws InvalidCredentialsException {
        Account account = accountRepository.findByLogin(accountCredentialsDTO.getLogin());
        if (account != null && account.getPassword().equals(accountCredentialsDTO.getPassword())) {
            return account;
        }
        throw new InvalidCredentialsException();
    }

    @Override
    public Page<Account> findAccountsByName(Pageable pageable, String forename, String surname) {
        return accountRepository.findAllByForenameAndSurname(forename, surname);
    }

    @Override
    public Page<Account> findAccountsByCity(Pageable pageable, String city) {
        return accountRepository.findAllByCity(pageable, city);
    }

    @Override
    public Page<Account> findAccountsByCountry(Pageable pageable, String country) {
        return accountRepository.findAllByCountry(pageable, country);
    }

    @Override
    public Page<Account> findAccountsByInterest(Pageable pageable, Interest interest) {
        return accountRepository.findAllByInterest(pageable, interest);
    }

    @Override
    public Page<Account> findAccountsByInterestAndCity(Pageable pageable, Interest interest, String city) {
        return accountRepository.findAllByInterestAndCity(pageable, interest, city);
    }

    @Override
    public Page<Account> findAccountsByInterestAndCountry(Pageable pageable, Interest interest, String country) {
        return accountRepository.findAllByInterestAndCountry(pageable, interest, country);
    }
}
