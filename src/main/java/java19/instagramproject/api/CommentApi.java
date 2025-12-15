package java19.instagramproject.api;

import java19.instagramproject.dto.CommentDto.request.CommentRequest;
import java19.instagramproject.dto.CommentDto.response.CommentResponse;
import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.repo.CommentRepo;
import java19.instagramproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
@RequiredArgsConstructor
public class CommentApi {
    private final CommentService commentService;


    @PostMapping("/{userId}/{postId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public SimpleResponse save(@PathVariable Long userId,
                               @PathVariable Long postId,
                               @RequestBody CommentRequest request){
        return commentService.save(userId,postId,request);
    }

    @GetMapping("/post/{postId}")
    public List<CommentResponse> findAllByPostId(@PathVariable Long postId){
        return  commentService.findAllByPostId(postId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @commentSecurity.isOwner(#id)")
    public SimpleResponse deleteById(@PathVariable Long id){
        return commentService.deleteById(id);
    }
}
