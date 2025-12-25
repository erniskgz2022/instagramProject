package java19.instagramproject.service.impl;

import jakarta.transaction.Transactional;
import java19.instagramproject.config.security.AccessGuard;
import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.entity.Like;
import java19.instagramproject.entity.Post;
import java19.instagramproject.entity.User;
import java19.instagramproject.repo.LikeRepo;
import java19.instagramproject.repo.PostRepo;
import java19.instagramproject.repo.UserRepo;
import java19.instagramproject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeServiceImpl implements LikeService {

    private final LikeRepo likeRepo;
    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final AccessGuard accessGuard;

    @Override
    public SimpleResponse likePost(Long userId, Long postId) throws AccessDeniedException {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found!"));

        Like existing = likeRepo.findByUserIdAndPostId(userId, postId);
        if (existing != null) {
            likeRepo.delete(existing);
            return new SimpleResponse(HttpStatus.OK, "Like removed");
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        likeRepo.save(like);

        return new SimpleResponse(HttpStatus.OK, "Post liked!");
    }
}
