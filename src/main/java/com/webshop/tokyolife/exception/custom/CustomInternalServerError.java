package com.webshop.tokyolife.exception.custom;

import com.webshop.tokyolife.model.CustomError;

public class CustomInternalServerError extends BaseCustomException{

    public CustomInternalServerError(CustomError customError) {
        super(customError);
    }
}
