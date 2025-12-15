package java19.instagramproject.dto.userDto.request;

public record SignUpRequest(
        String userName,
        String password,
        String email,
        String phoneNumber,
        String fullName,
        String image
) { }
