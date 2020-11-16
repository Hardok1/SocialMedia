package pl.edu.pwsztar.SocialMedia.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.pwsztar.SocialMedia.model.Account;
import pl.edu.pwsztar.SocialMedia.model.Post;

import java.util.List;

public interface PostService {
    Page<Post> getAccountPosts(Pageable pageable, String login);
    boolean addPost(String login, String content);
    boolean deletePost(String login, Long postId);
    boolean editPost(String login, Long postId, String newContent);
    Post getPost(Long postId);
}
