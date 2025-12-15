package java19.instagramproject.api;

import jakarta.annotation.security.PermitAll;
import java19.instagramproject.dto.postDto.request.PostRequest;
import java19.instagramproject.dto.postDto.response.PostResponse;
import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostApi {
    private final PostService postService;

    @PostMapping("/{userId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public SimpleResponse savePost(@PathVariable  Long userId, @RequestBody PostRequest request){
        return postService.savePost(userId, request);
    }

    @GetMapping("/{postId}")
    public PostResponse getById(@PathVariable  Long postId){
        return postService.getById(postId);
    }

    @GetMapping
    @PermitAll
    public List<PostResponse> getAll(){
        return postService.getAll();
    }


    @PutMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN') or @postSecurity.isOwner(#postId)")
    public SimpleResponse updatePost(@PathVariable  Long postId, @RequestBody PostRequest request){
        return postService.update(postId, request);
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('ADMIN') or @postSecurity.isOwner(#postId)")
    public SimpleResponse deletePost(@PathVariable  Long postId){
        return postService.delete(postId);
    }

    @GetMapping("/feed/{userId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<PostResponse> feed(@PathVariable  Long userId){
        return postService.feed(userId);
    }
}
