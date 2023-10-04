package com.webshop.tokyolife.service.impl;

import com.webshop.tokyolife.dto.user.UserDTO;
import com.webshop.tokyolife.dto.user.UserMapper;
import com.webshop.tokyolife.helps.SecurityHepler;
import com.webshop.tokyolife.model.UsersEntity;
import com.webshop.tokyolife.repository.UserRepository;
import com.webshop.tokyolife.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDTO getCurrentUser() {
        String email = SecurityHepler.getCurrentUsername();
        if (email != null) {
            Optional<UsersEntity> usersEntityOptional = userRepository.findByEmail(email);
            if (usersEntityOptional.isPresent()) {
                return userMapper.toUserDTO(usersEntityOptional.get());
            }
        }
        return null;
    }
}
