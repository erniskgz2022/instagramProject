package java19.instagramproject.dto.followerDto.response;

import lombok.Builder;

@Builder
public record SearchResponse(
        Long id,
        String userName,
        String fullName,
        String image
) { }
