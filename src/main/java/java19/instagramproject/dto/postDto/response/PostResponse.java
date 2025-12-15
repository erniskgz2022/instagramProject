package java19.instagramproject.dto.postDto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
@Builder
public record PostResponse(
        Long id,
        String title,
        String description,
        LocalDateTime createdAt,
        List<String> images,
        int likeCount,
        List<String> taggedUsers
) { }
