package java19.instagramproject.dto.userDto.response;

public record UserListResponse(
        String username,
        String fullName,
        String image,
        int subscribers,
        int subscriptions
) {
}
