package java19.instagramproject.dto.postDto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
@Builder
@Schema(description = "Post response model")
public record PostResponse(
        Long id,
        String title,
        String description,
        LocalDateTime createdAt,
        List<String> images,
        int likeCount,
        List<String> taggedUsers
) { }
