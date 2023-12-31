package com.webshop.tokyolife.controller;

import com.webshop.tokyolife.dto.ResponseDTO;
import com.webshop.tokyolife.dto.user.UserDTO;
import com.webshop.tokyolife.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
@RequiredArgsConstructor
public class UserContoller extends BaseController{
    private final UserService userService;

    @ApiOperation("Lấy thông tin user hiện tại")
    @GetMapping(value = "/detail")
    public ResponseEntity<?> getUserDetail(){
        UserDTO userDTO = userService.getCurrentUser();
        return response(new ResponseDTO(200,successStatus,successMessage,userDTO));
    }

}
