package com.webshop.tokyolife.controller;


import com.webshop.tokyolife.dto.ResponseDTO;
import com.webshop.tokyolife.dto.user.UserDTO;
import com.webshop.tokyolife.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class AuthController extends BaseController{
    private final AuthService authService;

    @ApiOperation("Đăng nhập")
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDTO.Login loginRequestDTO) throws Exception {

        return response(new ResponseDTO(200,successStatus,"Đăng nhập thành công",authService.login(loginRequestDTO)));
    }

    @ApiOperation("Đăng kí")
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO.Register registerRequestDTO) throws Exception {

        return response(new ResponseDTO(200,successStatus,"Đăng kí tài khoản thành công",authService.register(registerRequestDTO)));
    }
}
