package java19.instagramproject.service;

import java19.instagramproject.dto.userDto.SimpleResponse;

import java.nio.file.AccessDeniedException;

public interface LikeService {
    SimpleResponse likePost(Long userId, Long postId) throws AccessDeniedException;
}
