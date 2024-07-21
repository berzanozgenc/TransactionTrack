package com.transactionTrack.ws.service;

import com.transactionTrack.ws.dto.UserDto;
import com.transactionTrack.ws.model.User;
import com.transactionTrack.ws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                savedUser.getPassword(),
                savedUser.getFirstName(),
                savedUser.getLastName()
        );
    }

    public UserDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDto(
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public UserDto update(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        User updatedUser = userRepository.save(user);
        return new UserDto(
                updatedUser.getEmail(),
                updatedUser.getPassword(),
                updatedUser.getFirstName(),
                updatedUser.getLastName()
        );
    }

    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}
