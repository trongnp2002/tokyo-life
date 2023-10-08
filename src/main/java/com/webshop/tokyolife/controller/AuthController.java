package com.webshop.tokyolife.controller;


import com.webshop.tokyolife.dto.ResponseDTO;
import com.webshop.tokyolife.dto.user.UserDTO;
import com.webshop.tokyolife.service.AuthService;
import com.webshop.tokyolife.utils.CryptoUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class AuthController extends BaseController{
    private final AuthService authService;
    @Value("${authResponse.key}")
    private String key;

    @ApiOperation("Đăng nhập")
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO.Login loginRequestDTO) throws Exception {

        return response(new ResponseDTO(200,successStatus,"Đăng nhập thành công",authService.login(loginRequestDTO)));
    }

    @ApiOperation("Đăng kí")
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO.Register registerRequestDTO) throws Exception {

        return response(new ResponseDTO(201,createSuccessStatus,"Đăng kí tài khoản thành công",authService.register(registerRequestDTO)));
    }
    @ApiOperation("Đăng kí")
    @GetMapping(value = "/otp")
    public ResponseEntity<?> verify(@RequestParam(name = "base64") String base64, @RequestParam(name = "otp") String otp) throws Exception {
        authService.verifyOTP(base64,otp);
        return response(new ResponseDTO(201,createSuccessStatus,"Xác thực mã OTP thành công", null));
    }


}
