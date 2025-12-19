package java19.instagramproject.dto.CommentDto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
        @NotBlank(message = "Comment text must not be empty")
        String comment
) { }
