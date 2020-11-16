package pl.edu.pwsztar.SocialMedia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.SocialMedia.model.Account;
import pl.edu.pwsztar.SocialMedia.model.Relationship;
import pl.edu.pwsztar.SocialMedia.repository.AccountRepository;
import pl.edu.pwsztar.SocialMedia.repository.RelationshipRepository;
import pl.edu.pwsztar.SocialMedia.service.RelationshipService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RelationshipServiceImpl implements RelationshipService {

    private final String STATUS_PENDING = "p";
    private final String STATUS_FRIENDS = "f";

    final RelationshipRepository relationshipRepository;
    final AccountRepository accountRepository;

    @Autowired
    public RelationshipServiceImpl(RelationshipRepository relationshipRepository, AccountRepository accountRepository) {
        this.relationshipRepository = relationshipRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean sendFriendRequest(String login, Long userB) {
        try {
            Relationship relationship = new Relationship();
            relationship.setStatus(STATUS_PENDING);
            relationship.setUserA(accountRepository.findByLogin(login));
            relationship.setUserB(accountRepository.getOne(userB));
            relationshipRepository.save(relationship);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean acceptFriendRequest(Long relationshipId) {
        Optional<Relationship> relationship = relationshipRepository.findById(relationshipId);
        if (relationship.isPresent()){
            relationship.get().setStatus(STATUS_FRIENDS);
            relationshipRepository.save(relationship.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean removeRelationship(Long relationshipId) {
        Optional<Relationship> relationship = relationshipRepository.findById(relationshipId);
        if (relationship.isPresent()){
            relationshipRepository.delete(relationship.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean isFriend(String login, Long userB) {
        Account accountA = accountRepository.findByLogin(login);
        Account accountB = accountRepository.getOne(userB);
        Optional<Relationship> relationship1 = relationshipRepository.findByUserAAndUserBAndStatus(accountA, accountB, STATUS_FRIENDS);
        if (relationship1.isPresent()){
            return true;
        } else {
            Optional<Relationship> relationship2 = relationshipRepository.findByUserBAndUserAAndStatus(accountB, accountA, STATUS_FRIENDS);
            return relationship2.isPresent();
        }
    }

    @Override
    public List<Account> getFriendsList(String login) {
        Account account = accountRepository.findByLogin(login);
        List<Relationship> relationships1 = relationshipRepository.findAllByUserAAndStatus(account, STATUS_FRIENDS);
        List<Relationship> relationships2 = relationshipRepository.findAllByUserBAndStatus(account, STATUS_FRIENDS);
        List<Account> friends = new ArrayList<>();
        for (Relationship relationship: relationships1) {
            friends.add(relationship.getUserB());
        }
        for (Relationship relationship: relationships2) {
            friends.add(relationship.getUserA());
        }
        return friends;
    }

    @Override
    public List<Account> getReceivedFriendRequests(String login) {
        Account account = accountRepository.findByLogin(login);
        List<Relationship> relationships = relationshipRepository.findAllByUserBAndStatus(account, STATUS_PENDING);
        List<Account> requests = new ArrayList<>();
        for (Relationship relationship: relationships) {
            requests.add(relationship.getUserA());
        }
        return requests;
    }

    @Override
    public List<Account> getSentFriendRequests(String login) {
        Account account = accountRepository.findByLogin(login);
        List<Relationship> relationships = relationshipRepository.findAllByUserAAndStatus(account, STATUS_FRIENDS);
        List<Account> requests = new ArrayList<>();
        for (Relationship relationship: relationships) {
            requests.add(relationship.getUserB());
        }
        return requests;
    }
}
