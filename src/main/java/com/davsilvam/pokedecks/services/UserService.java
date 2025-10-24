package com.davsilvam.pokedecks.services;

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
        return UserMapper.toDTO(userRepository.findById(id).orElse(null));
    }

    public UserResponseDTO findByUsername(String username) {
        return UserMapper.toDTO(userRepository.findByUsername(username).orElse(null));
    }

    public UserResponseDTO findByEmail(String email) {
        return UserMapper.toDTO(userRepository.findByEmail(email).orElse(null));
    }

    public UserResponseDTO editProfile(UUID userId, EditUserProfileRequestDTO dto) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        user.setName(dto.name() != null ? dto.name() : user.getName());
        user.setEmail(dto.email() != null ? dto.email() : user.getEmail());
        user.setPhoneNumber(dto.phoneNumber() != null ? dto.phoneNumber() : user.getPhoneNumber());
        user.setBirthDate(dto.birthDate() != null ? dto.birthDate() : user.getBirthDate());
        user.setAddress(dto.address() != null ? dto.address() : user.getAddress());

        return UserMapper.toDTO(userRepository.save(user));
    }

    public void deleteAccount(UUID userId) {
        userRepository.deleteById(userId);
    }
}
