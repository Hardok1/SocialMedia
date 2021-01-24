package pl.edu.pwsztar.SocialMedia.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pwsztar.SocialMedia.model.Comment;


public interface CommentService {
    boolean addComment(String login, Long postId, String content);

    boolean editComment(String login, Long commentId, String content);

    boolean deleteComment(String login, Long commentId);

    Page<Comment> getPostComments(Pageable pageable, Long postId);
}
