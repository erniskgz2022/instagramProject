package java19.instagramproject.dto.userDto.response;

import lombok.Builder;

@Builder
public record UserByIdResponse(
        Long id,
        String userName,
        String password,
        String email,
        String phoneNumber
) { }
