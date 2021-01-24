package pl.edu.pwsztar.SocialMedia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.SocialMedia.model.Comment;
import pl.edu.pwsztar.SocialMedia.repository.AccountRepository;
import pl.edu.pwsztar.SocialMedia.repository.CommentRepository;
import pl.edu.pwsztar.SocialMedia.repository.PostRepository;
import pl.edu.pwsztar.SocialMedia.service.CommentService;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class CommentServiceImpl implements CommentService {

    final CommentRepository commentRepository;
    final PostRepository postRepository;
    final AccountRepository accountRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, AccountRepository accountRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean addComment(String login, Long postId, String content) {
        try {
            Comment comment = new Comment();
            comment.setPost(postRepository.getOne(postId));
            comment.setAuthor(accountRepository.findByLogin(login));
            comment.setContent(content);
            comment.setCreated_at(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
            commentRepository.save(comment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean editComment(String login, Long commentId, String content) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            comment.get().setContent(content);
            commentRepository.save(comment.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteComment(String login, Long commentId) {
        if (accountRepository.existsByLogin(login)) {
            commentRepository.deleteById(commentId);
        }
        return false;
    }

    @Override
    public Page<Comment> getPostComments(Pageable pageable, Long postId) {
        return commentRepository.findAllByPost(pageable, postRepository.getOne(postId));
    }
}
