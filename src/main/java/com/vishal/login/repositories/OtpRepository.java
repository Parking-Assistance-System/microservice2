package com.vishal.login.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vishal.login.entities.Otp;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, String> {

    Optional<Otp> findOtpByUsername(String username);
}
