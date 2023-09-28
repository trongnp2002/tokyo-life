package com.webshop.tokyolife.service.impl;

import com.webshop.tokyolife.dto.user.UserDTO;
import com.webshop.tokyolife.dto.user.UserMapper;
import com.webshop.tokyolife.exception.custom.CustomBadRequestException;
import com.webshop.tokyolife.exception.custom.CustomNotFoundException;
import com.webshop.tokyolife.model.AddressEntity;
import com.webshop.tokyolife.model.CustomError;
import com.webshop.tokyolife.model.UsersEntity;
import com.webshop.tokyolife.repository.AddressRepository;
import com.webshop.tokyolife.repository.UserRepository;
import com.webshop.tokyolife.service.AuthService;
import com.webshop.tokyolife.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    @Override
    public UserDTO login(UserDTO.Login userLogin) throws CustomNotFoundException {
        Optional<UsersEntity> usersEntityOptional = userRepository.findByEmail(userLogin.getEmail());
        if(usersEntityOptional.isPresent() ){
            UsersEntity usersEntity = usersEntityOptional.get();
            if(!passwordEncoder.matches(userLogin.getPassword(),usersEntity.getPassword())){
                throw new CustomNotFoundException(new CustomError("MSG_LOGIN_FAIL","Tài khoản hoặc mật khẩu không chính xác"));
            }
            if(usersEntity.getLocked()){

            }
            Optional<AddressEntity> addressEntity = addressRepository.findByUserIdAndIsDefaultTrue(usersEntity.getUserId());
            return userMapper.toUserDTO(usersEntity,addressEntity.get(),jwtTokenUtils.generateToken(usersEntity));
        }
        throw new CustomNotFoundException(new CustomError("MSG_LOGIN_FAIL","Tài khoản hoặc mật khẩu không chính xác"));
    }

    @Override
    @Transactional
    public UserDTO register(UserDTO.Register registerRequestDTO) throws CustomBadRequestException {
        Optional<UsersEntity> usersEntityOptional = userRepository.findByEmail(registerRequestDTO.getEmail());
        if(usersEntityOptional.isPresent()){
            throw new CustomBadRequestException(new CustomError("Email đã tồn tại."));
        }
        if(!registerRequestDTO.getPassword().equals(registerRequestDTO.getConfirmPassword())){
            throw new CustomBadRequestException(new CustomError("Password và confirm-password phải giống nhau"));
        }
        UsersEntity usersEntity= userRepository.save(userMapper.toUserEntity(registerRequestDTO));
        AddressEntity addressEntity = addressRepository.save(AddressEntity.builder()
                .usersEntity(usersEntity).firstName(registerRequestDTO.getFirstName())
                .lastName(registerRequestDTO.getLastName()).phone(registerRequestDTO.getPhone())
                .isDefault(true).country("Viet Nam").build());
        List<AddressEntity> addresses = new ArrayList<>();
        addresses.add(addressEntity);
        usersEntity.setAddress(addresses);
        usersEntity = userRepository.save(usersEntity);
        return userMapper.toUserDTO(usersEntity,addressEntity);
    }
}
