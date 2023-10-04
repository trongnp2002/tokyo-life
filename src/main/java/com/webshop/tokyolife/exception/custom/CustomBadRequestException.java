package com.webshop.tokyolife.exception.custom;

import com.webshop.tokyolife.model.CustomError;

public class CustomBadRequestException extends BaseCustomException{

    public CustomBadRequestException( CustomError customError) {
        super(customError);
    }
}
