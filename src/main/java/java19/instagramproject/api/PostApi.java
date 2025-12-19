package java19.instagramproject.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import java19.instagramproject.dto.postDto.request.PostRequest;
import java19.instagramproject.dto.postDto.response.PostResponse;
import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
@Tag(name = "Posts", description = "Post management and feed pagination")
@RestController
@RequestMapping("api/posts")
@RequiredArgsConstructor
public class PostApi {
    private final PostService postService;

    @PostMapping
    public SimpleResponse savePost( @Valid @RequestBody PostRequest request) throws AccessDeniedException {
        return postService.savePost(request);
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
    public SimpleResponse updatePost(@PathVariable  Long postId,
                                     @Valid @RequestBody PostRequest request) throws AccessDeniedException {
        return postService.update(postId, request);
    }

    @DeleteMapping("/{postId}")
    public SimpleResponse deletePost(@PathVariable  Long postId) throws AccessDeniedException {
        return postService.delete(postId);
    }

    @GetMapping("/feed/{userId}")
    public List<PostResponse> feed(@PathVariable  Long userId){
        return postService.feed(userId);
    }
    @Operation(
            summary = "Get posts (paginated)",
            description = "Retrieve posts ordered by newest first with pagination"
    )
    @GetMapping("/page")
    public Page<PostResponse> getAll(
            @RequestParam(required = false) Long userId,
            @Parameter(description = "Page number (starts from 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        return postService.getAll(userId, page, size);
    }




}
