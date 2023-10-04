package com.webshop.tokyolife.exception.custom;

import com.webshop.tokyolife.model.CustomError;

public class CustomUnavailableException extends BaseCustomException{
    public CustomUnavailableException(CustomError customError) {
        super(customError);
    }
}
