package com.webshop.tokyolife.controller;

import com.webshop.tokyolife.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@Component
@CrossOrigin(origins = "*")
public class BaseController {
    @Value("${error.status.success}")
    public String successStatus;
    @Value("${error.message.success}")
    public String successMessage;

    @Value("${error.status.created}")
    public String createSuccessStatus;
    @Value("${error.message.created}")
    public String createSuccessMessage;

    protected ResponseEntity response(ResponseDTO entity) {
        return new ResponseEntity(entity, HttpStatus.valueOf(entity.getCode()));
    }

}
