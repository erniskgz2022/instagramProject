package java19.instagramproject.dto.userInfoDto.request;

import java19.instagramproject.enums.Gender;

public record UserInfoRequest(
        String fullName,
        String biography,
        Gender gender,
        String image
) { }
