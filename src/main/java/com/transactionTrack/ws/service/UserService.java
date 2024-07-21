package com.transactionTrack.ws.service;

import com.transactionTrack.ws.dto.UserDto;
import com.transactionTrack.ws.model.User;
import com.transactionTrack.ws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserRepository userRepository;

    public UserDto create(UserDto userDto){
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);

        return new UserDto(
                savedUser.getEmail(),
                null,
                savedUser.getFirstName(),
                savedUser.getLastName()
        );
    }
}
