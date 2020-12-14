package pl.edu.pwsztar.SocialMedia.service;

import pl.edu.pwsztar.SocialMedia.dto.PublicAccountInfo;

import java.util.List;

public interface RelationshipService {
    boolean sendFriendRequest(String login, Long userB);

    boolean acceptFriendRequest(Long relationshipId);

    boolean removeRelationship(Long relationshipId);

    boolean isFriend(String login, Long userB);

    List<PublicAccountInfo> getFriendsList(String login);

    List<PublicAccountInfo> getReceivedFriendRequests(String login);

    List<PublicAccountInfo> getSentFriendRequests(String login);
}
