package pl.edu.pwsztar.SocialMedia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.SocialMedia.model.Post;
import pl.edu.pwsztar.SocialMedia.repository.AccountRepository;
import pl.edu.pwsztar.SocialMedia.repository.PostRepository;
import pl.edu.pwsztar.SocialMedia.service.PostService;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

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
    public Page<Post> getAccountPosts(Pageable pageable, String login) {
        return postRepository.findAllByOriginalPoster(pageable, accountRepository.findByLogin(login));
    }

    @Override
    public boolean addPost(String login, String content) {
        try {
            Post post = new Post();
            post.setContent(content);
            post.setCreatedAt(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
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
    public Post getPost(Long postId) {
        return postRepository.getOne(postId);
    }
}
