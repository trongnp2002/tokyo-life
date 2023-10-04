package com.webshop.tokyolife.exception.custom;

import com.webshop.tokyolife.model.CustomError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseCustomException extends RuntimeException {
    private CustomError customError;

    public BaseCustomException(CustomError customError) {
        this.customError = customError;
    }
}
