package com.webshop.tokyolife.service.impl;

import com.webshop.tokyolife.dto.user.UserDTO;
import com.webshop.tokyolife.dto.user.UserMapper;
import com.webshop.tokyolife.exception.custom.CustomAccessDeniedException;
import com.webshop.tokyolife.exception.custom.CustomBadRequestException;
import com.webshop.tokyolife.exception.custom.CustomInternalServerError;
import com.webshop.tokyolife.exception.custom.CustomNotFoundException;
import com.webshop.tokyolife.model.CustomError;
import com.webshop.tokyolife.model.OTP;
import com.webshop.tokyolife.model.UsersEntity;
import com.webshop.tokyolife.model.enums.AuthResponActionEnum;
import com.webshop.tokyolife.repository.OtpRepository;
import com.webshop.tokyolife.repository.UserRepository;
import com.webshop.tokyolife.service.AuthService;
import com.webshop.tokyolife.service.EmailService;
import com.webshop.tokyolife.utils.CryptoUtils;
import com.webshop.tokyolife.utils.JwtTokenUtils;
import com.webshop.tokyolife.utils.NumberUtils;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final EmailService emailService;
    @Value("${authResponse.key}")
    private String key;
    private final OtpRepository otpRepository;
    private final TemplateEngine templateEngine;

    @Override
    public UserDTO.LoginResponseDTO login(UserDTO.Login userLogin) throws CustomNotFoundException {
        Optional<UsersEntity> usersEntityOptional = userRepository.findByEmail(userLogin.getEmail());
        if (usersEntityOptional.isPresent()) {
            UsersEntity usersEntity = usersEntityOptional.get();
            if (!passwordEncoder.matches(userLogin.getPassword(), usersEntity.getPassword())) {
                throw new CustomNotFoundException(new CustomError("MSG_LOGIN_FAIL", "Tài khoản hoặc mật khẩu không chính xác"));
            }
            if (usersEntity.getLocked()) {
                throw new CustomAccessDeniedException(new CustomError("MSG_LOGIN_FAIL", "Tài khoản của bạn đã bị khóa do vượt quá số lần thử mật khẩu, vui lòng thử lại sau 24h"));
            }
            return userMapper.toLoginResponse(usersEntity, jwtTokenUtils.generateToken(usersEntity));
        }
        throw new CustomNotFoundException(new CustomError("MSG_LOGIN_FAIL", "Tài khoản hoặc mật khẩu không chính xác"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String register(UserDTO.Register registerRequestDTO) throws CustomBadRequestException {
        Optional<UsersEntity> usersEntityOptional = userRepository.findByEmail(registerRequestDTO.getEmail());
        if (usersEntityOptional.isPresent()) {
            throw new CustomBadRequestException(new CustomError("Email đã tồn tại."));
        }
        if (!registerRequestDTO.getPassword().equals(registerRequestDTO.getConfirmPassword())) {
            throw new CustomBadRequestException(new CustomError("Password và confirm-password phải giống nhau"));
        }
        AuthResponseDTO authResponseDTO = AuthResponseDTO.builder().object(registerRequestDTO).email(registerRequestDTO.getEmail()).build();
        String base64 = CryptoUtils.encryptObject(authResponseDTO, key);
        try {
            sendOTP(registerRequestDTO.getEmail(), AuthResponActionEnum.REGISTER.getValue());
        } catch (Exception e) {
            throw new CustomInternalServerError(new CustomError(e.getMessage()));
        }
        return base64;
    }


    @Override
    public void verifyOTP(String base64, String otp) {
        AuthResponseDTO authResponseDTO = (AuthResponseDTO) CryptoUtils.decryptObject(base64, key);
        if (authResponseDTO == null) {
            throw new CustomInternalServerError(new CustomError());
        }
        OTP otp1 = otpRepository.findByEmail(authResponseDTO.getEmail());
        otp1.checkVerify(otp);
        switch (otp1.getAction()) {
            case 1:
                createUser((UserDTO.Register) authResponseDTO.getObject());
                break;

        }
        otp1.setIsUsed(true);
        otpRepository.save(otp1);

    }

    private void createUser(UserDTO.Register register) {
        UsersEntity userDTO = userMapper.toUserEntity(register);
        userRepository.save(userDTO);
    }


    private void sendOTP(String email, int action) throws Exception {
        String code = String.valueOf(NumberUtils.getRandomInt(100000, 999999));
        OTP otp = otpRepository.findByEmail(email);
        if (otp != null) {
            otp.setCreatedAt(LocalDateTime.now());
        } else {
           otp =  OTP.builder().build();
        }
        otp.setEmail(email);
        otp.setCode(code);
        otp.setAction(action);
        otp.setIsUsed(false);

        otpRepository.save(otp);
        Context context = new Context();
        context.setVariable("webapp", "TOKYOLIFE");
        context.setVariable("email", email);
        context.setVariable("otp", otp.getCode());
        String body = templateEngine.process("email-template.html", context);
        emailService.sendMail(email, "Tokyo life", body);
        return;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class AuthResponseDTO implements Serializable {
        private Object object;
        private String email;
    }
}
