package java19.instagramproject.api;

import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeApi {

    private final LikeService likeService;

    @PostMapping("/post/{userId}/{postId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public SimpleResponse likePost(@PathVariable Long userId,
                                   @PathVariable Long postId) {
        return likeService.likePost(userId, postId);
    }
}
