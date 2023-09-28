package com.webshop.tokyolife.dto.user;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TokenPayload {
    private String uuid;
    private String email;
}
