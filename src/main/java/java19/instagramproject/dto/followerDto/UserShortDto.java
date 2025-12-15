package java19.instagramproject.dto.followerDto;

import lombok.Builder;

@Builder
public record UserShortDto(
        Long id,
        String userName,
        String fullName,
        String image
) { }
