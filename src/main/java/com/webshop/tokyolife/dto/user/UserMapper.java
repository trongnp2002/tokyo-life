package com.webshop.tokyolife.dto.user;


import com.webshop.tokyolife.model.AddressEntity;
import com.webshop.tokyolife.model.RolesEntity;
import com.webshop.tokyolife.model.UsersEntity;
import com.webshop.tokyolife.repository.AddressRepository;
import com.webshop.tokyolife.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final AddressRepository addressRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    public UserDTO toUserDTO (UsersEntity usersEntity, AddressEntity addressEntity){
        return UserDTO.builder()
                .email(usersEntity.getEmail())
                .UUID(usersEntity.getUuid().toString())
                .country(addressEntity.getCountry())
                .address(addressEntity.getDeliveryAddress())
                .company(addressEntity.getCompany())
                .firstName(addressEntity.getFirstName())
                .lastName(addressEntity.getLastName())
                .phone(addressEntity.getPhone())
                .token(null)
                .build();
    }

    public UserDTO toUserDTO (UsersEntity usersEntity, AddressEntity addressEntity, String token){
        return UserDTO.builder()
                .email(usersEntity.getEmail())
                .UUID(usersEntity.getUuid().toString())
                .country(addressEntity.getCountry())
                .address(addressEntity.getDeliveryAddress())
                .company(addressEntity.getCompany())
                .firstName(addressEntity.getFirstName())
                .lastName(addressEntity.getLastName())
                .phone(addressEntity.getPhone())
                .token(token)
                .build();
    }
    public UserDTO toUserDTO (UsersEntity usersEntity){
        AddressEntity addressEntity = addressRepository.findByUserIdAndIsDefaultTrue(usersEntity.getUserId()).get();
        return UserDTO.builder()
                .email(usersEntity.getEmail())
                .UUID(usersEntity.getUuid().toString())
                .country(addressEntity.getCountry())
                .address(addressEntity.getDeliveryAddress())
                .company(addressEntity.getCompany())
                .firstName(addressEntity.getFirstName())
                .lastName(addressEntity.getLastName())
                .phone(addressEntity.getPhone())
                .token(null)
                .build();
    }

    public UsersEntity toUserEntity(UserDTO.Register register){
        UsersEntity usersEntity = UsersEntity.builder()
                .email(register.getEmail())
                .dateOfBirth(register.getDob())
                .enable(true)
                .locked(false)
                .password(passwordEncoder.encode(register.getPassword()))
                .gender(register.getGender())
                .build();
        Set<RolesEntity> userRoles = new HashSet<>();
        RolesEntity roleUser = rolesRepository.findById(1).get();
        userRoles.add(roleUser);
        usersEntity.setRoles(userRoles);
        return usersEntity;
    }


}
