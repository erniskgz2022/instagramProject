package java19.instagramproject.dto.userDto.response;

import lombok.Builder;

@Builder
public record AuthSigUpResponse(
        Long id,
        String userName

) {}
