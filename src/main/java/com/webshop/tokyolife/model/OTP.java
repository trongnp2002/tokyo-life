package com.webshop.tokyolife.model;

import com.webshop.tokyolife.exception.custom.CustomBadRequestException;
import lombok.*;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Entity
@Table(name = "otp")
@AllArgsConstructor
@NoArgsConstructor
public class OTP extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",insertable = false,updatable = false)
    private Integer id;
    private String email;
    private String code;
    private Boolean isUsed;
    private int action;

    public void checkVerify(String code){
        if(!this.getCode().equals(code)){
            throw new CustomBadRequestException(new CustomError("OTP_HAS_NOT_CORRECT","Mã OTP không chính xác."));
        }
        if(this.getIsUsed()){
            throw new CustomBadRequestException(new CustomError("OTP_HAS_BEEN_USED","Mã OTP đã được sử dụng."));
        }
        Duration duration = Duration.between(this.getCreatedAt(), LocalDateTime.now());
        if(duration.toMinutes() > 5){
            throw new CustomBadRequestException(new CustomError("OTP_EXPRIED", "Mã OTP đã hết hạn."));
        }

    }



}
