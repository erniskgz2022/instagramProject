package java19.instagramproject.dto.postDto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PostRequest(
        @NotBlank(message = "Title must not be empty")
        String title,
        @NotBlank(message = "Description must not be empty")
        String description,
        List<String> images,
        List<Long> taggedUserIds
) { }
