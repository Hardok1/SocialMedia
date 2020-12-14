package pl.edu.pwsztar.SocialMedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.SocialMedia.dto.AccountDTO;
import pl.edu.pwsztar.SocialMedia.dto.AccountDetailsDTO;
import pl.edu.pwsztar.SocialMedia.dto.PublicAccountInfo;
import pl.edu.pwsztar.SocialMedia.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/account/")
public class AccountController {
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    @Autowired
    public AccountController(PasswordEncoder passwordEncoder, AccountService accountService) {
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
    }

    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody AccountDTO accountDTO) {
        accountDTO.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        if (accountService.createAccount(accountDTO)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("remove")
    public ResponseEntity<?> removeAccount(Authentication authentication) {
        if (accountService.removeAccount(authentication.getName())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping("edit")
    public ResponseEntity<?> editAccount(Authentication authentication, AccountDTO accountDTO) {
        if (accountService.editAccount(authentication.getName(), accountDTO)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("{id}")
    public ResponseEntity<AccountDetailsDTO> getAccountDetails(@PathVariable int id) {
        AccountDetailsDTO accountDetails = accountService.getAccountDetails((long) id);
        return new ResponseEntity<>(accountDetails, HttpStatus.OK);
    }

    @GetMapping("findByName")
    public ResponseEntity<List<PublicAccountInfo>> findAccountsByName(@RequestParam int page, @RequestParam String forename, @RequestParam String surname) {
        Pageable pageable = PageRequest.of(page, 10);
        return new ResponseEntity<>(accountService.findAccountsByName(pageable, forename, surname), HttpStatus.OK);
    }

    @GetMapping("findByCity")
    public ResponseEntity<List<PublicAccountInfo>> findAccountsByCity(@RequestParam int page, @RequestParam String city) {
        Pageable pageable = PageRequest.of(page, 10);
        return new ResponseEntity<>(accountService.findAccountsByCity(pageable, city), HttpStatus.OK);
    }

    @GetMapping("findByCountry")
    public ResponseEntity<List<PublicAccountInfo>> findAccountsByCountry(@RequestParam int page, @RequestParam String country) {
        Pageable pageable = PageRequest.of(page, 10);
        return new ResponseEntity<>(accountService.findAccountsByCountry(pageable, country), HttpStatus.OK);
    }

    @GetMapping("findByInterest")
    public ResponseEntity<List<PublicAccountInfo>> findAccountsByInterest(@RequestParam int page, @RequestParam String interest) {
        Pageable pageable = PageRequest.of(page, 10);
        return new ResponseEntity<>(accountService.findAccountsByInterest(pageable, interest), HttpStatus.OK);
    }

    @GetMapping("findByInterestAndCity")
    public ResponseEntity<List<PublicAccountInfo>> findAccountsByInterestAndCity(@RequestParam int page, @RequestParam String interest, @RequestParam String city) {
        Pageable pageable = PageRequest.of(page, 10);
        return new ResponseEntity<>(accountService.findAccountsByInterestAndCity(pageable, interest, city), HttpStatus.OK);
    }

    @GetMapping("findByInterestAndCountry")
    public ResponseEntity<List<PublicAccountInfo>> findAccountsByInterestAndCountry(@RequestParam int page, @RequestParam String interest, @RequestParam String country) {
        Pageable pageable = PageRequest.of(page, 10);
        return new ResponseEntity<>(accountService.findAccountsByInterestAndCountry(pageable, interest, country), HttpStatus.OK);
    }
}