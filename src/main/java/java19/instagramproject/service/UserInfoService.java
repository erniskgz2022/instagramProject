package java19.instagramproject.service;

import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.dto.userInfoDto.request.UserInfoRequest;
import java19.instagramproject.dto.userInfoDto.response.UserInfoResponse;

public interface UserInfoService {
    SimpleResponse saveUserInfo(Long userId, UserInfoRequest request);

    UserInfoResponse findUserInfoByUserId(Long userId);

    SimpleResponse update(Long userId, UserInfoRequest request);

    SimpleResponse changeImage(Long userId, String newImage);

    SimpleResponse deleteImage(Long userId);
}
