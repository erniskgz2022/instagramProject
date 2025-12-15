package java19.instagramproject.service;

import java19.instagramproject.dto.userDto.SimpleResponse;
import java19.instagramproject.dto.userDto.request.SignInRequest;
import java19.instagramproject.dto.userDto.request.SignUpRequest;
import java19.instagramproject.dto.userDto.response.AuthResponse;
import java19.instagramproject.dto.userDto.response.UserByIdResponse;
import java19.instagramproject.dto.userDto.response.UserListResponse;
import java19.instagramproject.dto.userDto.response.UserResponse;
import java19.instagramproject.entity.User;

import java.util.List;

public interface UserService  {
    List<UserListResponse> getUsers();
    UserResponse userProfile(Long id);
    User findUserById(Long id);      // Entity үчүн
    UserByIdResponse getUserById(Long id);
    SimpleResponse update(Long id, SignUpRequest request);
    SimpleResponse delete(Long id);
}
