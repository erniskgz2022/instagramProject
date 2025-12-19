package java19.instagramproject.service;

import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.dto.userInfoDto.request.UserInfoRequest;
import java19.instagramproject.dto.userInfoDto.response.UserInfoResponse;

import java.nio.file.AccessDeniedException;

public interface UserInfoService {
    SimpleResponse saveUserInfo(Long userId, UserInfoRequest request);

    UserInfoResponse findUserInfoByUserId(Long userId);

    SimpleResponse update(Long userId, UserInfoRequest request) throws AccessDeniedException;

    SimpleResponse changeImage(Long userId, String newImage) throws AccessDeniedException;

    SimpleResponse deleteImage(Long userId) throws AccessDeniedException;
}
