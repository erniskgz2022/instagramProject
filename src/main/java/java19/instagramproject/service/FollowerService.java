package java19.instagramproject.service;

import java19.instagramproject.dto.followerDto.UserShortDto;
import java19.instagramproject.dto.followerDto.response.FollowerResponse;
import java19.instagramproject.dto.followerDto.response.SearchResponse;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface FollowerService {
    List<SearchResponse> search(String word);
    FollowerResponse subscribe(Long myId, Long targetUserId) throws AccessDeniedException;
    List<UserShortDto> getAllSubscribersByUserId(Long userId);
    List<UserShortDto> getAllSubscriptionsByUserId(Long userId);
}
