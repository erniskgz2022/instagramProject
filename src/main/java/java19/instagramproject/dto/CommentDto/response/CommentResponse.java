package java19.instagramproject.dto.CommentDto.response;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record CommentResponse(
        Long id,
        String comment,
        LocalDateTime createdAt,
        String userName,
        int likeCount
) { }
