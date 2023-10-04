package com.webshop.tokyolife.dto.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.webshop.tokyolife.model.RolesEntity;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonTypeName("user")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class UserDTO {
    private String UUID;
    private String email;
    private String firstName;
    private String lastName;
    private String company;
    private String address;
    private String phone;
    private String token;
    private String country;
    private List<String> roles;
    private String typeToken;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonTypeName("user")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class Register{
        @NotBlank(message = "Phải điền đầy đủ họ và tên")
        private String firstName;
        @NotBlank(message = "Phải điền đầy đủ họ và tên")
        private String lastName;
        @NotBlank(message = "Mật khẩu không để trống")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]{6,18}$", message = "Mật khẩu 6-12 ký tự, bao gồm chữ hoa, chữ thường, số, ký tự đặc biệt")
        private String password;
        @NotBlank(message = "Mật khẩu xác nhận không để trống")
        private String confirmPassword;
        @NotBlank
        @Email
        private String email;
        private LocalDate dob;
        @NotBlank(message = "Số điện thoại không để trống")
        @Pattern(regexp = "^(0[3|5|7|8|9])+([0-9]{8})\\b$", message = "Số điện thoại không đúng")
        private String phone;
        @NotNull(message = "Giới tính không được bỏ trống")
        private Boolean gender;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonTypeName("user")
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
    public static class Login {
        @NotBlank
        @Email
        private String email;
        @NotBlank
        private String password;
    }


}
