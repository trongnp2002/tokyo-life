package com.webshop.tokyolife.exception.custom;

import com.webshop.tokyolife.model.CustomError;

public class CustomAccessDeniedException extends BaseCustomException{
    public CustomAccessDeniedException(CustomError customError) {
        super(customError);
    }
}
