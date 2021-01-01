package pl.edu.pwsztar.SocialMedia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.SocialMedia.dto.PublicAccountInfo;
import pl.edu.pwsztar.SocialMedia.dto.RelationshipStatusDTO;
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
    public boolean acceptFriendRequestById(Long relationshipId) {
        Optional<Relationship> relationship = relationshipRepository.findById(relationshipId);
        if (relationship.isPresent()){
            relationship.get().setStatus(STATUS_FRIENDS);
            relationshipRepository.save(relationship.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean acceptFriendRequestByUser(String login, int id) {
        Account accountA = accountRepository.findByLogin(login);
        Account accountB = accountRepository.getOne((long) id);
        Optional<Relationship> relationship = relationshipRepository.findByUserAAndUserBAndStatus(accountA, accountB, STATUS_PENDING);
        if (relationship.isPresent()){
            relationship.get().setStatus(STATUS_FRIENDS);
            relationshipRepository.save(relationship.get());
            return true;
        } else {
            relationship = relationshipRepository.findByUserBAndUserAAndStatus(accountB, accountA, STATUS_PENDING);
            if (relationship.isPresent()){
                relationship.get().setStatus(STATUS_FRIENDS);
                relationshipRepository.save(relationship.get());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean removeRelationshipById(Long relationshipId) {
        Optional<Relationship> relationship = relationshipRepository.findById(relationshipId);
        if (relationship.isPresent()){
            relationshipRepository.delete(relationship.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean removeRelationshipWithUser(String login, int id) {
        Account accountA = accountRepository.findByLogin(login);
        Account accountB = accountRepository.getOne((long) id);
        Optional<Relationship> relationship = relationshipRepository.findByUserAAndUserB(accountA, accountB);
        if (relationship.isPresent()){
            relationshipRepository.delete(relationship.get());
            return true;
        } else {
            relationship = relationshipRepository.findByUserBAndUserA(accountB, accountA);
            if (relationship.isPresent()){
                relationshipRepository.delete(relationship.get());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isFriend(String login, Long userB) {
        Account accountA = accountRepository.findByLogin(login);
        Account accountB = accountRepository.getOne(userB);
        Optional<Relationship> relationship = relationshipRepository.findByUserAAndUserBAndStatus(accountA, accountB, STATUS_FRIENDS);
        if (relationship.isPresent()){
            return true;
        } else {
            relationship = relationshipRepository.findByUserBAndUserAAndStatus(accountB, accountA, STATUS_FRIENDS);
            return relationship.isPresent();
        }
    }

    @Override
    public RelationshipStatusDTO getRelationshipStatus(String login, Long userB) {
        RelationshipStatusDTO relationshipStatusDTO = new RelationshipStatusDTO();
        if (isFriend(login,userB)){
            relationshipStatusDTO.setStatus("friend");
        }
        Account accountA = accountRepository.findByLogin(login);
        Account accountB = accountRepository.getOne(userB);
        if (relationshipRepository.findByUserAAndUserBAndStatus(accountA, accountB, STATUS_PENDING).isPresent()){
            relationshipStatusDTO.setStatus("invited");
        } else if (relationshipRepository.findByUserBAndUserAAndStatus(accountB, accountA, STATUS_PENDING).isPresent()){
            relationshipStatusDTO.setStatus("requested");
        } else {
            relationshipStatusDTO.setStatus("stranger");
        }
        return relationshipStatusDTO;
    }

    @Override
    public List<PublicAccountInfo> getFriendsList(String login) {
        Account account = accountRepository.findByLogin(login);
        List<Relationship> relationships1 = relationshipRepository.findAllByUserAAndStatus(account, STATUS_FRIENDS);
        List<Relationship> relationships2 = relationshipRepository.findAllByUserBAndStatus(account, STATUS_FRIENDS);
        List<PublicAccountInfo> friends = new ArrayList<>();
        for (Relationship relationship: relationships1) {
            friends.add(new PublicAccountInfo(relationship.getUserB()));
        }
        for (Relationship relationship: relationships2) {
            friends.add(new PublicAccountInfo(relationship.getUserA()));
        }
        return friends;
    }

    @Override
    public List<PublicAccountInfo> getReceivedFriendRequests(String login) {
        Account account = accountRepository.findByLogin(login);
        List<Relationship> relationships = relationshipRepository.findAllByUserBAndStatus(account, STATUS_PENDING);
        List<PublicAccountInfo> requests = new ArrayList<>();
        for (Relationship relationship: relationships) {
            requests.add(new PublicAccountInfo(relationship.getUserA()));
        }
        return requests;
    }

    @Override
    public List<PublicAccountInfo> getSentFriendRequests(String login) {
        Account account = accountRepository.findByLogin(login);
        List<Relationship> relationships = relationshipRepository.findAllByUserAAndStatus(account, STATUS_FRIENDS);
        List<PublicAccountInfo> requests = new ArrayList<>();
        for (Relationship relationship: relationships) {
            requests.add(new PublicAccountInfo(relationship.getUserB()));
        }
        return requests;
    }
}
