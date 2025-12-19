package java19.instagramproject.api;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.dto.userDto.request.SignUpRequest;
import java19.instagramproject.dto.userDto.response.UserByIdResponse;
import java19.instagramproject.dto.userDto.response.UserListResponse;
import java19.instagramproject.dto.userDto.response.UserResponse;
import java19.instagramproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
@Tag(name = "Users", description = "User profile and account operations")
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserApi {
        private final UserService userService;


    @GetMapping("{id}/profile")
    public UserResponse profile(@PathVariable Long id) {
        return userService.userProfile(id);
    }

    @GetMapping()
    public List<UserListResponse> getUsers() throws AccessDeniedException {
        return userService.getUsers();
    }

    @GetMapping("{id}")
    public UserByIdResponse getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update/{id}")
    public SimpleResponse update( @PathVariable Long id,
                                  @Valid @RequestBody SignUpRequest request) throws AccessDeniedException {
        return userService.update(id,request);
    }


    @DeleteMapping("{id}")
    public SimpleResponse delete(@PathVariable Long id) throws AccessDeniedException {
        return userService.delete(id);
    }

}
