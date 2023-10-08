package com.webshop.tokyolife.service;

import com.webshop.tokyolife.dto.user.UserDTO;
import com.webshop.tokyolife.service.impl.AuthServiceImpl;

public interface AuthService {
    public UserDTO.LoginResponseDTO login(UserDTO.Login userLogin) throws Exception;

    public String register(UserDTO.Register registerRequestDTO) throws Exception;

    void verifyOTP(String base64, String otp);
}
