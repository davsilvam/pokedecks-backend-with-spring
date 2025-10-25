package com.davsilvam.pokedecks.services;

import com.davsilvam.pokedecks.models.UserAuthenticated;
import com.davsilvam.pokedecks.models.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .map(UserAuthenticated::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com email " + email + " não encontrado"));
    }
}
