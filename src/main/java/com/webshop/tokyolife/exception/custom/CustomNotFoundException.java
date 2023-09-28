package com.webshop.tokyolife.exception.custom;

import com.webshop.tokyolife.model.CustomError;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;


public class CustomNotFoundException extends BaseCustomException {

    public CustomNotFoundException(CustomError customError) {

        super(customError);

    }


}
