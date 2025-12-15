package java19.instagramproject.dto.postDto.request;

import java.util.List;

public record PostRequest(
        String title,
        String description,
        List<String> images,
        List<Long> taggedUserIds
) { }
