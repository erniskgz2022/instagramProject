package java19.instagramproject.service;

import java19.instagramproject.dto.userDto.SimpleResponse;

public interface LikeService {
    SimpleResponse likePost(Long userId, Long postId);
}
