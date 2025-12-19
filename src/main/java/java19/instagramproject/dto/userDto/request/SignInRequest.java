package java19.instagramproject.dto.userDto.request;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank(message = "Phone number must not be empty")
        String login,
        @NotBlank(message = "Password must not be empty")
        String password
) { }
