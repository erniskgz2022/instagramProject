package java19.instagramproject.api;

import jakarta.validation.Valid;
import java19.instagramproject.dto.CommentDto.request.CommentRequest;
import java19.instagramproject.dto.CommentDto.response.CommentResponse;
import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("api/comments")
@RequiredArgsConstructor
public class CommentApi {
    private final CommentService commentService;


    @PostMapping("/{userId}/{postId}")
    public SimpleResponse save(@PathVariable Long userId,
                               @PathVariable Long postId,
                               @Valid @RequestBody CommentRequest request){
        return commentService.save(userId,postId,request);
    }

    @GetMapping("/post/{postId}")
    public List<CommentResponse> findAllByPostId(@PathVariable Long postId){
        return  commentService.findAllByPostId(postId);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse deleteById(@PathVariable Long id) throws AccessDeniedException {
        return commentService.deleteById(id);
    }
}
