package org.nahid.ecommerce.service;

import org.nahid.ecommerce.config.JwtUtils;
import org.nahid.ecommerce.config.UserInfoConfig;
import org.nahid.ecommerce.models.ERole;
import org.nahid.ecommerce.models.Role;
import org.nahid.ecommerce.models.User;
import org.nahid.ecommerce.repository.RoleRepository;
import org.nahid.ecommerce.repository.UserRepository;
import org.nahid.ecommerce.request.LoginRequest;
import org.nahid.ecommerce.request.SignUpRequest;
import org.nahid.ecommerce.response.JwtResponse;
import org.nahid.ecommerce.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserInfoConfig userInfo = (UserInfoConfig) authentication.getPrincipal();
        List<String> roles = userInfo.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt, userInfo.getId(), userInfo.getUsername(), roles);
    }

    @Override
    public MessageResponse registerUser(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new MessageResponse("Error: Email is already in use!");
        }

        User user = new User();
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setMobileNumber(signUpRequest.getMobileNumber());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            // Assign default role if no roles are specified
            Optional<Role> userRoleOptional = roleRepository.findByName(ERole.USER);
            if (userRoleOptional.isPresent()) {
                roles.add(userRoleOptional.get());
            } else {
                return new MessageResponse("Error: Default role is not found.");
            }
        } else {
            for (String role : strRoles) {
                switch (role.toLowerCase()) {
                    case "admin":
                        Optional<Role> adminRoleOptional = roleRepository.findByName(ERole.ADMIN);
                        if (adminRoleOptional.isPresent()) {
                            roles.add(adminRoleOptional.get());
                        } else {
                            return new MessageResponse("Error: Admin role is not found.");
                        }
                        break;
                    case "user":
                        Optional<Role> userRoleOptional = roleRepository.findByName(ERole.USER);
                        if (userRoleOptional.isPresent()) {
                            roles.add(userRoleOptional.get());
                        } else {
                            return new MessageResponse("Error: User role is not found.");
                        }
                        break;
                    default:
                        return new MessageResponse("Error: Role " + role + " is not recognized.");
                }
            }
        }

        user.setRoles(roles);
        userRepository.save(user);
        return new MessageResponse("User registered successfully!");
    }
}
