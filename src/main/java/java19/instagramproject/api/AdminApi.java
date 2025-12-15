package java19.instagramproject.api;

import java19.instagramproject.dto.userDto.response.UserListResponse;
import java19.instagramproject.service.CommentService;
import java19.instagramproject.service.PostService;
import java19.instagramproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminApi {
    private final UserService userService;

    @GetMapping("/users")
    public List<UserListResponse> getUsers() {
        return userService.getUsers();
    }
}
