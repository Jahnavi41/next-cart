package dev.ju.nextcart.service.user;

import dev.ju.nextcart.dto.UserDTO;
import dev.ju.nextcart.model.User;
import dev.ju.nextcart.request.CreateUserRequest;
import dev.ju.nextcart.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDTO convertUserToDto(User user);
}
