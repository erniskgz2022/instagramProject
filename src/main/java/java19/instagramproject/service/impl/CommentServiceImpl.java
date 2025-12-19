package java19.instagramproject.service.impl;

import jakarta.transaction.Transactional;
import java19.instagramproject.config.security.AccessGuard;
import java19.instagramproject.dto.CommentDto.request.CommentRequest;
import java19.instagramproject.dto.CommentDto.response.CommentResponse;
import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.entity.Comment;
import java19.instagramproject.entity.Like;
import java19.instagramproject.entity.Post;
import java19.instagramproject.entity.User;
import java19.instagramproject.repo.CommentRepo;
import java19.instagramproject.repo.PostRepo;
import java19.instagramproject.repo.UserRepo;
import java19.instagramproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo  commentRepo;
    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final AccessGuard accessGuard;
    @Override
    public SimpleResponse save(Long userId, Long postId, CommentRequest request) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found!"));

        Comment comment = new Comment();
        comment.setComment(request.comment());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(user);
        comment.setPost(post);

        Like like = new Like();
        like.setLike(false);
        like.setUser(user);
        like.setComment(comment);

        comment.getLikes().add(like);
        commentRepo.save(comment);

        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Comment Successfully saved!")
                .build();
    }

    @Override
    public List<CommentResponse> findAllByPostId(Long postId) {
        return commentRepo.findAllByPostId(postId)
                .stream()
                .map(c -> CommentResponse.builder()
                        .id(c.getId())
                        .comment(c.getComment())
                        .createdAt(c.getCreatedAt())
                        .userName(c.getUser().getUserName())
                        .likeCount((int) c.getLikes().stream().filter(Like::isLike).count())
                        .build())
                .toList();
    }

    @Override
    public SimpleResponse deleteById(Long id) throws AccessDeniedException {
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new RuntimeException("Comment not found!"));
        accessGuard.allow(comment.getUser().getId());
        commentRepo.delete(comment);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Comment deleted successfully!!").build();
    }
}
