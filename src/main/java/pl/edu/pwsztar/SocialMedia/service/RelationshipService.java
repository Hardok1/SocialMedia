package pl.edu.pwsztar.SocialMedia.service;

import pl.edu.pwsztar.SocialMedia.dto.PublicAccountInfo;
import pl.edu.pwsztar.SocialMedia.dto.RelationshipStatusDTO;

import java.util.List;

public interface RelationshipService {
    boolean sendFriendRequest(String login, Long userB);

    boolean acceptFriendRequestById(Long relationshipId);

    boolean acceptFriendRequestByUser(String login, int id);

    boolean removeRelationshipById(Long relationshipId);

    boolean removeRelationshipWithUser(String login, int id);

    boolean isFriend(String login, Long userB);

    RelationshipStatusDTO getRelationshipStatus(String login, Long userB);

    List<PublicAccountInfo> getFriendsList(String login);

    List<PublicAccountInfo> getReceivedFriendRequests(String login);

    List<PublicAccountInfo> getSentFriendRequests(String login);
}
