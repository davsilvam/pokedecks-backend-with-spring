package com.davsilvam.pokedecks.services;

import com.davsilvam.pokedecks.config.errors.exceptions.ResourceNotFoundException;
import com.davsilvam.pokedecks.models.User;
import com.davsilvam.pokedecks.models.repositories.UserRepository;
import com.davsilvam.pokedecks.services.dtos.EditUserProfileRequestDTO;
import com.davsilvam.pokedecks.services.dtos.UserResponseDTO;
import com.davsilvam.pokedecks.services.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDTO findById(UUID id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("Usuário com ID " + id);
        }

        return UserMapper.toDTO(user);
    }

    public UserResponseDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("Usuário com username " + username);
        }

        return UserMapper.toDTO(user);
    }

    public UserResponseDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("Usuário com email " + email);
        }

        return UserMapper.toDTO(user);
    }

    public UserResponseDTO editProfile(UUID id, EditUserProfileRequestDTO dto) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("Usuário com ID " + id);
        }

        user.setName(dto.name() != null ? dto.name() : user.getName());
        user.setEmail(dto.email() != null ? dto.email() : user.getEmail());
        user.setPhoneNumber(dto.phoneNumber() != null ? dto.phoneNumber() : user.getPhoneNumber());
        user.setBirthDate(dto.birthDate() != null ? dto.birthDate() : user.getBirthDate());
        user.setAddress(dto.address() != null ? dto.address() : user.getAddress());

        return UserMapper.toDTO(userRepository.save(user));
    }

    public void deleteAccount(UUID id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("Usuário com ID " + id);
        }

        userRepository.deleteById(id);
    }
}
