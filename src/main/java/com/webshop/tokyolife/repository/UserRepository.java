package com.webshop.tokyolife.repository;

import com.webshop.tokyolife.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Integer> {

    public Optional<UsersEntity> findByEmail(String email);
    public Optional<UsersEntity> findByEmailAndPassword(String email, String password);

    public Optional<UsersEntity> findByUuid(UUID uuid);


}
