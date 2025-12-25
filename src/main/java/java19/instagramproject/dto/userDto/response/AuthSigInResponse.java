package java19.instagramproject.dto.userDto.response;

import lombok.Builder;

@Builder
public record AuthSigInResponse(
        Long id,
        String token,
        String userName

) {}
