package com.webshop.tokyolife.repository;

import com.webshop.tokyolife.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity,Integer> {

    @Query(value = "select add from AddressEntity add where add.usersEntity.userId = :userId and add.isDefault=true")
    public Optional<AddressEntity> findByUserIdAndIsDefaultTrue(@Param("userId") Integer userId);
}
