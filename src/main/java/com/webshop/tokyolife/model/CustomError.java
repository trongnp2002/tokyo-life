package com.webshop.tokyolife.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomError {
    private Integer code;
    private String status;
    private String message;

    public CustomError(String status, String message){
        this.status = status;
        this.message = message;
    }

    public CustomError(String message){
        this.message = message;
    }

}
