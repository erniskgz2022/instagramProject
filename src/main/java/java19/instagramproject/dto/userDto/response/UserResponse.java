package java19.instagramproject.dto.userDto.response;

import java19.instagramproject.dto.postDto.response.PostResponse;
import lombok.Builder;

import java.util.List;
@Builder
public record UserResponse(
        String username,
        String fullName,
        String image,
        int subscribers,
        int subscriptions,
        List<PostResponse> posts
) {}
