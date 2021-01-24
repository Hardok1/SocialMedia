package pl.edu.pwsztar.SocialMedia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.SocialMedia.dto.CommentDTO;
import pl.edu.pwsztar.SocialMedia.dto.PostDTO;
import pl.edu.pwsztar.SocialMedia.dto.PostDetailsDTO;
import pl.edu.pwsztar.SocialMedia.dto.PublicAccountInfo;
import pl.edu.pwsztar.SocialMedia.model.Account;
import pl.edu.pwsztar.SocialMedia.model.Comment;
import pl.edu.pwsztar.SocialMedia.model.Post;
import pl.edu.pwsztar.SocialMedia.repository.AccountRepository;
import pl.edu.pwsztar.SocialMedia.repository.PostRepository;
import pl.edu.pwsztar.SocialMedia.service.PostService;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    final PostRepository postRepository;
    final AccountRepository accountRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, AccountRepository accountRepository) {
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<PostDTO> getAccountPosts(Pageable pageable, Long accountId) {
        Page<Post> posts = postRepository.findAllByOriginalPosterOrderByCreatedAtDesc(pageable, accountRepository.getOne(accountId));
        List<PostDTO> postDTOS = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        for (Post post : posts.getContent()) {
            postDTOS.add(new PostDTO(post.getId(), post.getContent(), formatter.format(post.getCreatedAt().getTime())));
        }
        return postDTOS;
    }

    @Override
    public boolean addPost(String login, String content) {
        try {
            Post post = new Post();
            post.setContent(content);
            post.setCreatedAt(Calendar.getInstance(TimeZone.getTimeZone("Poland")));
            post.setOriginalPoster(accountRepository.findByLogin(login));
            postRepository.save(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePost(String login, Long postId) {
        if (postRepository.existsById(postId)) {
            Post post = postRepository.getOne(postId);
            if (post.getOriginalPoster().getId().equals(accountRepository.findByLogin(login).getId())) {
                postRepository.deleteById(postId);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean editPost(String login, Long postId, String newContent) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            post.get().setContent(newContent);
            postRepository.save(post.get());
            return true;
        }
        return false;
    }

    @Override
    public PostDetailsDTO getPost(Long postId) {
        Post post = postRepository.getOne(postId);
        Set<CommentDTO> commentDTOS = new HashSet<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        for (Comment comment : post.getComment()) {
            Account author = comment.getAuthor();
            PublicAccountInfo account = new PublicAccountInfo(author.getId(), author.getForename(), author.getSurname(), author.getCountry(), author.getCity());
            commentDTOS.add(new CommentDTO(comment.getId(), comment.getContent(), formatter.format(comment.getCreated_at().getTime()), account));
        }
        return new PostDetailsDTO(post.getId(), post.getContent(), formatter.format(post.getCreatedAt().getTime()), commentDTOS);
    }
}
