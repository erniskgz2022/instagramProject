package java19.instagramproject.dto.userInfoDto.response;

import java19.instagramproject.enums.Gender;
import lombok.Builder;

@Builder
public record UserInfoResponse(
        Long id,
        String fullName,
        String biography,
        Gender gender,
        String image,
        String username
) { }
