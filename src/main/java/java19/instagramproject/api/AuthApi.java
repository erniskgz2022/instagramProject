package java19.instagramproject.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java19.instagramproject.dto.userDto.request.SignInRequest;
import java19.instagramproject.dto.userDto.request.SignUpRequest;
import java19.instagramproject.dto.userDto.response.AuthSigInResponse;
import java19.instagramproject.dto.userDto.response.AuthSigUpResponse;
import java19.instagramproject.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Authentication", description = "User registration and login")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {
    private final AuthService authService;
    @Operation(
            summary = "Sign up",
            description = "Register a new user account"
    )
    @PostMapping(
            value = "/signUp",
            consumes = "application/json",
            produces = "application/json"
    )
    public AuthSigUpResponse signUp(@Valid @RequestBody SignUpRequest request) {
        return authService.signUp(request);
    }
    @Operation(
            summary = "Sign in",
            description = "Authenticate user and return JWT token"
    )
    @PostMapping("/signIn")
    public AuthSigInResponse signIn(@Valid @RequestBody SignInRequest request) {
        return authService.signIn(request);
    }
}
