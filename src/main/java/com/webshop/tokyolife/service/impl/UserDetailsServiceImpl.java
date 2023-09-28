package com.webshop.tokyolife.service.impl;

import com.webshop.tokyolife.dto.user.AuthUserDetails;
import com.webshop.tokyolife.model.UsersEntity;
import com.webshop.tokyolife.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsersEntity> usersEntityOptional = userRepository.findByEmail(username);
        if(usersEntityOptional.isPresent()){
            UsersEntity usersEntity = usersEntityOptional.get();
            return new AuthUserDetails(usersEntity);
        }
        throw new UsernameNotFoundException("Invalid username or password");
    }
}
