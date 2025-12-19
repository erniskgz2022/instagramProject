package java19.instagramproject.api;

import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeApi {

    private final LikeService likeService;

    @PostMapping("/post/{userId}/{postId}")
    public SimpleResponse likePost(@PathVariable Long userId,
                                   @PathVariable Long postId) throws AccessDeniedException {
        return likeService.likePost(userId, postId);
    }
}
