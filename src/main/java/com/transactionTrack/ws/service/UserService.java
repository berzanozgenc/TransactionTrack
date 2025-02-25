package com.transactionTrack.ws.service;

import com.transactionTrack.ws.dto.UserDto;
import com.transactionTrack.ws.exception.EmailValidationException;
import com.transactionTrack.ws.exception.UserNotFoundException;
import com.transactionTrack.ws.model.Transaction;
import com.transactionTrack.ws.model.User;
import com.transactionTrack.ws.repository.TransactionRepository;
import com.transactionTrack.ws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public UserDto create(UserDto userDto){
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailValidationException("This email is already in use");
        }

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
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserDto(
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public UserDto update(Long id, UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailValidationException("Bu e-posta adresi zaten kullanılmakta.");
        }
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
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

    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        transactionRepository.deleteAllByUser(user.getId());
        userRepository.delete(user);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
