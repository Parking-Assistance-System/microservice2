package com.vishal.login.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vishal.login.entities.Otp;
import com.vishal.login.entities.User;
import com.vishal.login.repositories.OtpRepository;
import com.vishal.login.repositories.UserRepository;
import com.vishal.login.utils.GenerateCodeUtil;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;
    
    @Autowired
    private JwtUtil jwtutil;

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void auth(User user) {
        Optional<User> o =
                userRepository.findUserByUsername(user.getUsername());

        if(o.isPresent()) {
            User u = o.get();
            if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                renewOtp(u);
            } else {
                throw new BadCredentialsException("Bad credentials.");
            }
        } else {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    public boolean check(Otp otpToValidate) {
        Optional<Otp> userOtp = otpRepository.findOtpByUsername(otpToValidate.getUsername());
        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            if (otpToValidate.getCode().equals(otp.getCode())) {
                return true;
            }
        }

        return false;
    }

    private void renewOtp(User u) {
        String code = GenerateCodeUtil.generateCode();

        Optional<Otp> userOtp = otpRepository.findOtpByUsername(u.getUsername());
        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            otp.setCode(code);
        } else {
            Otp otp = new Otp();
            otp.setUsername(u.getUsername());
            otp.setCode(code);
            otpRepository.save(otp);
        }
    }

    
    public String auth(String username,String password) {
    	
    	Optional<User> o =
                userRepository.findUserByUsername(username);
    	
    	if(o.isPresent()) {
    		User u=o.get();
    		System.out.println(passwordEncoder.encode(password));
    		if(passwordEncoder.matches( password,u.getPassword())) {
    			
    			String token=jwtutil.generateJwt(username);
    			return token;
    		}
    		return null;
    		
    	}
    	return null;
    }
    
    public boolean isUserExist(String username) {
    	
    	
    	Optional<User> o =
                userRepository.findUserByUsername(username);
    	return o.isPresent();
    	
    	
    }
    
    public boolean isCarExist(String carNo) {
//    	Optional<User> o =
//                userRepository.findUserByCarNo(carNo);
//    	return o.isPresent();
    	System.out.println("before"+carNo+ "Recieved");
    	User o = userRepository.findUserByCarNo(carNo);
    	if(o == null) {
    		return false;
    	}
    	return true;
    }
    
}
