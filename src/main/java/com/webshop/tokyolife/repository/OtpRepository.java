package com.webshop.tokyolife.repository;

import com.webshop.tokyolife.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<OTP,Integer> {
    OTP findByEmail(String email);
}
