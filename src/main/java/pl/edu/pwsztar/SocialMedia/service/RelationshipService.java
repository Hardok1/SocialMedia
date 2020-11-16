package pl.edu.pwsztar.SocialMedia.service;

import pl.edu.pwsztar.SocialMedia.model.Account;
import pl.edu.pwsztar.SocialMedia.model.Relationship;

import java.util.List;

public interface RelationshipService {
    boolean sendFriendRequest(String login, Long userB);
    boolean acceptFriendRequest(Long relationshipId);
    boolean removeRelationship(Long relationshipId);
    boolean isFriend(String login, Long userB);
    List<Account> getFriendsList(String login);
    List<Account> getReceivedFriendRequests(String login);
    List<Account> getSentFriendRequests(String login);
}
