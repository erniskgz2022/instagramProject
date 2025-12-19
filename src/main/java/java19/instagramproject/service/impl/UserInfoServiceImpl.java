package java19.instagramproject.service.impl;

import jakarta.transaction.Transactional;
import java19.instagramproject.config.security.AccessGuard;
import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.dto.userInfoDto.request.UserInfoRequest;
import java19.instagramproject.dto.userInfoDto.response.UserInfoResponse;
import java19.instagramproject.entity.User;
import java19.instagramproject.entity.UserInfo;
import java19.instagramproject.repo.UserInfoRepo;
import java19.instagramproject.repo.UserRepo;
import java19.instagramproject.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserRepo  userRepo;
    private final UserInfoRepo userInfoRepo;
    private final AccessGuard accessGuard;
    @Override
    public SimpleResponse saveUserInfo(Long userId, UserInfoRequest request) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user not found!"));

        if (user.getUserInfo() != null) {
            return  SimpleResponse
                    .builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("UserInfo already exists for this user!")
                    .build();
        }

        UserInfo info = new  UserInfo();
        info.setFullName(request.fullName());
        info.setBiography(request.biography());
        info.setGender(request.gender());
        info.setImage(request.image());
        info.setUser(user);
        userInfoRepo.save(info);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("UserInfo saved successfully!")
                .build();
    }

    @Override
    public UserInfoResponse findUserInfoByUserId(Long userId) {
        UserInfo info = userInfoRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("UserInfo not found!"));
        return UserInfoResponse
                .builder()
                .id(info.getId())
                .fullName(info.getFullName())
                .biography(info.getBiography())
                .gender(info.getGender())
                .image(info.getImage())
                .username(info.getUser().getUserName())
                .build();
    }

    @Override
    public SimpleResponse update(Long userId, UserInfoRequest request) throws AccessDeniedException {
        UserInfo info = userInfoRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("UserInfo not found!"));

        accessGuard.allow(userId);

        info.setFullName(request.fullName());
        info.setBiography(request.biography());
        info.setGender(request.gender());
        info.setImage(request.image());
        userInfoRepo.save(info);

        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("UserInfo updated successfully!")
                .build();
    }

    @Override
    public SimpleResponse changeImage(Long userId, String newImage) throws AccessDeniedException {
        UserInfo info = userInfoRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("UserInfo not found!"));
        accessGuard.allow(userId);
        info.setImage(newImage);
        userInfoRepo.save(info);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Image successfully changed!")
                .build();

    }

    @Override
    public SimpleResponse deleteImage(Long userId) throws AccessDeniedException {
        UserInfo info = userInfoRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("UserInfo not found!"));

        accessGuard.allow(userId);

        info.setImage(null);
        userInfoRepo.save(info);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Image deleted").build();
    }
}
