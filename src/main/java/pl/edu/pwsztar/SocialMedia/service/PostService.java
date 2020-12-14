package pl.edu.pwsztar.SocialMedia.service;

import org.springframework.data.domain.Pageable;
import pl.edu.pwsztar.SocialMedia.dto.PostDTO;
import pl.edu.pwsztar.SocialMedia.dto.PostDetailsDTO;

import java.util.List;

public interface PostService {
    List<PostDTO> getAccountPosts(Pageable pageable, Long accountId);

    boolean addPost(String login, String content);

    boolean deletePost(String login, Long postId);

    boolean editPost(String login, Long postId, String newContent);

    PostDetailsDTO getPost(Long postId);
}
