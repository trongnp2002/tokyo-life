package com.webshop.tokyolife.exception.custom;

import com.webshop.tokyolife.model.CustomError;

public class CustomUnauthorizedException extends BaseCustomException{
    public CustomUnauthorizedException(CustomError customError) {
        super(customError);
    }
}
