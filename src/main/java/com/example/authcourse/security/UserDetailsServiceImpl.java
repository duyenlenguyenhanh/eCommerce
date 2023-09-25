package com.example.authcourse.security;

import com.example.authcourse.exception.UserNotFoundException;
import com.example.authcourse.model.persistence.User;
import com.example.authcourse.model.persistence.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User not found, username = ".concat(username));
        }
        return new org.springframework.security.core.userdetails.User(optionalUser.get().getUsername(), optionalUser.get().getPassword(), Collections.emptyList());
    }
}
