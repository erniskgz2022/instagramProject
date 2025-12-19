package java19.instagramproject.service;

import java19.instagramproject.dto.postDto.request.PostRequest;
import java19.instagramproject.dto.postDto.response.PostResponse;
import java19.instagramproject.dto.userDto.SimpleResponse;
import org.springframework.data.domain.Page;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface PostService {
    SimpleResponse savePost( PostRequest request) throws AccessDeniedException;
    PostResponse getById(Long postId);
    List<PostResponse> getAll();
    SimpleResponse update(Long postId, PostRequest request) throws AccessDeniedException;
    SimpleResponse delete(Long postId) throws AccessDeniedException;
    List<PostResponse> feed(Long userId);
    Page<PostResponse> getAll(Long userId, int page, int size);
}
