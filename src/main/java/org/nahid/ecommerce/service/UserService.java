package org.nahid.ecommerce.service;

import org.nahid.ecommerce.dto.UserDTO;
import org.nahid.ecommerce.models.User;
import org.nahid.ecommerce.request.LoginRequest;
import org.nahid.ecommerce.response.JwtResponse;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    User saveUser(User user);
    JwtResponse authenticateUser(LoginRequest loginRequest);
    UserDTO registerUser(UserDTO userDTO);
}
