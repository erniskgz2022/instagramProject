package java19.instagramproject.dto.userDto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank(message = "Username must not be empty")
        String userName,
        @Email(message = "Email must be valid")
        @NotBlank(message = "Email must not be empty")
        String email,
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,
        @Size(min = 6, message = "PhoneNumber must be at least 6 characters")
        String phoneNumber,
        String fullName,
        String image
) { }
