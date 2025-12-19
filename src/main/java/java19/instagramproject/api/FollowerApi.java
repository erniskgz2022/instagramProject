package java19.instagramproject.api;

import java19.instagramproject.dto.followerDto.UserShortDto;
import java19.instagramproject.dto.followerDto.response.FollowerResponse;
import java19.instagramproject.dto.followerDto.response.SearchResponse;
import java19.instagramproject.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("api/followers")
@RequiredArgsConstructor
public class FollowerApi {
    private final FollowerService followerService;

    @GetMapping("/search")
    public List<SearchResponse> search(String word){
        return followerService.search(word);
    }

    @PostMapping("{myId}/subscribe/{targetId}")
    public FollowerResponse subscribe (@PathVariable("myId") Long myId, @PathVariable("targetId") Long targetId) throws AccessDeniedException {
        return followerService.subscribe(myId, targetId);
    }

    @GetMapping("/{userId}/subscribers")
    public List<UserShortDto> subscribers(@PathVariable Long userId){
        return followerService.getAllSubscribersByUserId(userId);
    }

    @GetMapping("/{userId}/subscriptions")
    public List<UserShortDto> subscriptions(@PathVariable Long userId){
        return followerService.getAllSubscriptionsByUserId(userId);
    }
}
