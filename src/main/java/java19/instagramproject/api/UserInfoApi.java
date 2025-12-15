package java19.instagramproject.api;

import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.dto.userInfoDto.request.UserInfoRequest;
import java19.instagramproject.dto.userInfoDto.response.UserInfoResponse;
import java19.instagramproject.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/usersInfo")
@RequiredArgsConstructor
public class UserInfoApi {
    private final UserInfoService userInfoService;
    @PostMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public SimpleResponse saveUserInfo(@PathVariable Long userId, @RequestBody UserInfoRequest request){
        return userInfoService.saveUserInfo(userId, request);
    }

    @GetMapping("/{userId}")
    public UserInfoResponse findUserInfoByUserId(@PathVariable Long userId){
        return userInfoService.findUserInfoByUserId(userId);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public SimpleResponse update(@PathVariable Long userId, @RequestBody UserInfoRequest request){
        return userInfoService.update(userId, request);
    }

    @PatchMapping("/{userId}/change-image")
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public SimpleResponse changeImage(@PathVariable Long userId, @RequestBody String newImage ){
        return userInfoService.changeImage(userId,newImage);
    }

    @PatchMapping("/{userId}/delete-image")
    @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
    public SimpleResponse deleteImage(@PathVariable Long userId ){
        return userInfoService.deleteImage(userId);
    }
}
