package java19.instagramproject.api;

import jakarta.validation.Valid;
import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.dto.userInfoDto.request.UserInfoRequest;
import java19.instagramproject.dto.userInfoDto.response.UserInfoResponse;
import java19.instagramproject.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("api/usersInfo")
@RequiredArgsConstructor
public class UserInfoApi {
    private final UserInfoService userInfoService;
    @PostMapping("/{userId}")
    public SimpleResponse saveUserInfo(@PathVariable Long userId,
                                       @Valid @RequestBody UserInfoRequest request){
        return userInfoService.saveUserInfo(userId, request);
    }

    @GetMapping("/{userId}")
    public UserInfoResponse findUserInfoByUserId(@PathVariable Long userId){
        return userInfoService.findUserInfoByUserId(userId);
    }

    @PutMapping("/{userId}")
    public SimpleResponse update(@PathVariable Long userId,
                                 @Valid @RequestBody UserInfoRequest request) throws AccessDeniedException {
        return userInfoService.update(userId, request);
    }

    @PatchMapping("/{userId}/change-image")
    public SimpleResponse changeImage(@PathVariable Long userId,
                                      @Valid @RequestBody String newImage ) throws AccessDeniedException {
        return userInfoService.changeImage(userId,newImage);
    }

    @PatchMapping("/{userId}/delete-image")
    public SimpleResponse deleteImage(@PathVariable Long userId ) throws AccessDeniedException {
        return userInfoService.deleteImage(userId);
    }
}
