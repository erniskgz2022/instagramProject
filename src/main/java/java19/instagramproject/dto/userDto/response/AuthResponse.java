package java19.instagramproject.dto.userDto.response;

import lombok.Builder;

@Builder
public record AuthResponse(
        Long id,
        String token,
        String userName

) {}
