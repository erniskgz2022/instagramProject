package java19.instagramproject.api;
import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.dto.userDto.request.SignUpRequest;
import java19.instagramproject.dto.userDto.response.UserByIdResponse;
import java19.instagramproject.dto.userDto.response.UserListResponse;
import java19.instagramproject.dto.userDto.response.UserResponse;
import java19.instagramproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserApi {
        private final UserService userService;


    @GetMapping("{id}/profile")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserResponse profile(@PathVariable Long id) {
        return userService.userProfile(id);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserListResponse> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserByIdResponse getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public SimpleResponse update( @PathVariable Long id, @RequestBody SignUpRequest request) {
        return userService.update(id,request);
    }


    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public SimpleResponse delete(@PathVariable Long id) {
        return userService.delete(id);
    }

}
