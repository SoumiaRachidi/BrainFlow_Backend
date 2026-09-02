package com.brainflow.brainflow.config;

import com.brainflow.brainflow.entity.User;
import com.brainflow.brainflow.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> maybeUser = userRepository.findByEmail(username);
        if (maybeUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        User user = maybeUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getSystemRole() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getSystemRole().name()));
            authorities.add(new SimpleGrantedAuthority(user.getSystemRole().name()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                user.isApproved(),
                true,
                true,
                true,
                authorities
        );
    }
}