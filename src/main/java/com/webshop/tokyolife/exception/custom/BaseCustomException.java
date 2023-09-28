package com.webshop.tokyolife.exception.custom;

import com.webshop.tokyolife.model.CustomError;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class BaseCustomException extends RuntimeException{
    private CustomError customError ;
    public BaseCustomException(CustomError customError){
        this.customError = customError;
    }
}

