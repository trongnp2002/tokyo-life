package com.webshop.tokyolife.service;

import com.webshop.tokyolife.dto.user.UserDTO;

public interface AuthService {
    public UserDTO login(UserDTO.Login userLogin) throws Exception;

    public UserDTO register(UserDTO.Register registerRequestDTO) throws Exception;
}
