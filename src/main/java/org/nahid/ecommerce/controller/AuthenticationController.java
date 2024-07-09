package org.nahid.ecommerce.controller;

import org.nahid.ecommerce.dto.UserDTO;
import org.nahid.ecommerce.request.LoginRequest;
import org.nahid.ecommerce.response.JwtResponse;
import org.nahid.ecommerce.response.MessageResponse;
import org.nahid.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse = userService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        userService.registerUser(userDTO);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
