package java19.instagramproject.service;

import java19.instagramproject.dto.postDto.request.PostRequest;
import java19.instagramproject.dto.postDto.response.PostResponse;
import java19.instagramproject.dto.userDto.SimpleResponse;

import java.util.List;

public interface PostService {
    SimpleResponse savePost(Long userId, PostRequest request);
    PostResponse getById(Long postId);
    List<PostResponse> getAll();
    SimpleResponse update(Long postId, PostRequest request);
    SimpleResponse delete(Long postId);
    List<PostResponse> feed(Long userId);

}
