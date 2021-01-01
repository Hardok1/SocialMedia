package pl.edu.pwsztar.SocialMedia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.SocialMedia.dto.AccountDTO;
import pl.edu.pwsztar.SocialMedia.dto.AccountDetailsDTO;
import pl.edu.pwsztar.SocialMedia.dto.PublicAccountInfo;
import pl.edu.pwsztar.SocialMedia.model.Account;
import pl.edu.pwsztar.SocialMedia.model.Interest;
import pl.edu.pwsztar.SocialMedia.repository.AccountRepository;
import pl.edu.pwsztar.SocialMedia.repository.InterestRepository;
import pl.edu.pwsztar.SocialMedia.service.AccountService;

import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    final AccountRepository accountRepository;
    final InterestRepository interestRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, InterestRepository interestRepository) {
        this.accountRepository = accountRepository;
        this.interestRepository = interestRepository;
    }


    private Set<Interest> convertInterestsListFromNames(Set<String> interests) {
        Set<Interest> interestsList = new HashSet<>();
        for (String interest : interests) {
            interestsList.add(interestRepository.findByName(interest));
        }
        return interestsList;
    }

    private Interest convertNameToInterest(String interest){
        return interestRepository.findByName(interest);
    }

    @Override
    public boolean createAccount(AccountDTO accountDTO) {
        if (accountRepository.existsByLogin(accountDTO.getLogin())) return false;
        Account account = new Account(accountDTO);
        account.setInterest(convertInterestsListFromNames(accountDTO.getInterests()));
        account.setCreatedAt(Calendar.getInstance(TimeZone.getTimeZone("Poland")));
        accountRepository.save(account);
        return true;
    }

    @Override
    public boolean removeAccount(String login) {
        try {
            accountRepository.delete(accountRepository.findByLogin(login));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editAccount(String login, AccountDTO newInfo) {
        try {
            Account account = accountRepository.findByLogin(login);
            if (!(null == newInfo.getForename() || newInfo.getForename().isEmpty())) {
                account.setForename(newInfo.getForename());
            }
            if (!(null == newInfo.getSurname() || newInfo.getSurname().isEmpty())) {
                account.setSurname(newInfo.getSurname());
            }
            if (!(null == newInfo.getCity() || newInfo.getCity().isEmpty())) {
                account.setCity(newInfo.getCity());
            }
            if (!(null == newInfo.getCountry() || newInfo.getCountry().isEmpty())) {
                account.setCountry(newInfo.getCountry());
            }
            if (!(null == newInfo.getPassword() || newInfo.getPassword().isEmpty())) {
                account.setPassword(newInfo.getPassword());
            }
            if (!(null == newInfo.getInterests() || newInfo.getInterests().isEmpty())) {
                account.setInterest(convertInterestsListFromNames(newInfo.getInterests()));
            }
            accountRepository.save(account);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Long getAccountId(String login) {
        return accountRepository.findByLogin(login).getId();
    }

    @Override
    public AccountDetailsDTO getAccountDetails(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()){
            AccountDetailsDTO accountDetails = new AccountDetailsDTO(account.get());
            Set<String> interests = new HashSet<>();
            for (Interest interest: account.get().getInterest()){
                interests.add(interest.getName());
            }
            accountDetails.setInterests(interests);
            return accountDetails;
        }
        return new AccountDetailsDTO();
    }

    @Override
    public AccountDetailsDTO getMyAccountDetails(String name) {
        return getAccountDetails(accountRepository.findByLogin(name).getId());
    }

    private List<PublicAccountInfo> convertAccountToPublicAccountInfo(List<Account> accounts) {
        List<PublicAccountInfo> accountsList = new ArrayList<>();
        for (Account account : accounts) {
            accountsList.add(new PublicAccountInfo(account.getId(), account.getForename(), account.getSurname(), account.getCountry(), account.getCity()));
        }
        return accountsList;
    }

    @Override
    public List<PublicAccountInfo> findAccountsByName(Pageable pageable, String forename, String surname) {
        return convertAccountToPublicAccountInfo(accountRepository.findAllByForenameAndSurname(pageable, forename, surname).getContent());
    }

    @Override
    public List<PublicAccountInfo> findAccountsByCity(Pageable pageable, String city) {
        return convertAccountToPublicAccountInfo(accountRepository.findAllByCity(pageable, city).getContent());
    }

    @Override
    public List<PublicAccountInfo> findAccountsByCountry(Pageable pageable, String country) {
        return convertAccountToPublicAccountInfo(accountRepository.findAllByCountry(pageable, country).getContent());
    }

    @Override
    public List<PublicAccountInfo> findAccountsByInterest(Pageable pageable, String interest) {
        return convertAccountToPublicAccountInfo(accountRepository.findAllByInterest(pageable, convertNameToInterest(interest)).getContent());
    }

    @Override
    public List<PublicAccountInfo> findAccountsByInterestAndCity(Pageable pageable, String interest, String city) {
        return convertAccountToPublicAccountInfo(accountRepository.findAllByInterestAndCity(pageable, convertNameToInterest(interest), city).getContent());
    }

    @Override
    public List<PublicAccountInfo> findAccountsByInterestAndCountry(Pageable pageable, String interest, String country) {
        return convertAccountToPublicAccountInfo(accountRepository.findAllByInterestAndCountry(pageable, convertNameToInterest(interest), country).getContent());
    }
}
