package java19.instagramproject.service;

import java19.instagramproject.dto.CommentDto.request.CommentRequest;
import java19.instagramproject.dto.CommentDto.response.CommentResponse;
import java19.instagramproject.dto.userDto.SimpleResponse;

import java.util.List;

public interface CommentService {
    SimpleResponse save(Long userId,Long postId, CommentRequest request);
    List<CommentResponse> findAllByPostId(Long postId);
    SimpleResponse deleteById(Long id);
}
