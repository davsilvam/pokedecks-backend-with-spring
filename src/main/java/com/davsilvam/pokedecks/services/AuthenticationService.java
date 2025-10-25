package com.davsilvam.pokedecks.services;

import com.davsilvam.pokedecks.config.errors.exceptions.ResourceConflictException;
import com.davsilvam.pokedecks.config.security.JwtService;
import com.davsilvam.pokedecks.models.User;
import com.davsilvam.pokedecks.models.enums.UserRole;
import com.davsilvam.pokedecks.models.repositories.UserRepository;
import com.davsilvam.pokedecks.services.dtos.CreateUserRequestDTO;
import com.davsilvam.pokedecks.services.dtos.UserResponseDTO;
import com.davsilvam.pokedecks.services.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO register(CreateUserRequestDTO request) {
        User existingUserByUsername = userRepository.findByUsername(request.username()).orElse(null);

        if (existingUserByUsername != null) {
            throw new ResourceConflictException("Username " + request.username() + " já está em uso");
        }

        User existingUserByEmail = userRepository.findByEmail(request.email()).orElse(null);

        if (existingUserByEmail != null) {
            throw new ResourceConflictException("Email " + request.email() + " já está em uso");
        }

        User user = User.builder()
                .name(request.name())
                .username(request.username())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(UserRole.USER)
                .build();

        userRepository.save(user);

        return UserMapper.toDTO(user);
    }

    public String authenticate(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }
}
