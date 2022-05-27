package com.vishal.login.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vishal.login.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findUserByUsername(String username);
    
    @Query(value = "SELECT * FROM user WHERE car_no = ?1", nativeQuery = true)
   User findUserByCarNo(String carNo);
}
