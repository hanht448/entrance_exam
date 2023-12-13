package com.example.demo_user.service;


import com.example.demo_user.dto.UserResponseDTO;
import com.example.demo_user.entity.TokenEntity;
import com.example.demo_user.entity.UserEntity;
import com.example.demo_user.exception.EmailNotAvailableException;
import com.example.demo_user.exception.ValidationException;
import com.example.demo_user.repository.TokenRepository;
import com.example.demo_user.repository.UserRepository;
import com.example.demo_user.util.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserResponseDTO signUpUser(String email, String password, String firstName, String lastName) {
        // Validation
        if (!isEmailValid(email) || !isPasswordValid(password)) {
            throw new ValidationException("Validation failed");
        }

        // Check if email is available
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailNotAvailableException("Email is not available");
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        // Create a new user entity
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(encryptPassword(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUpdatedAt(localDateTime);
        user.setHash(encryptPassword(password));
        // save UserEntity
        user = userRepository.save(user);

        // Create and return the response DTO
        return createUserResponseDTO(user);
    }

    @Override
    public UserEntity authenticateUser(String email, String password) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        if (passwordEncoder.matches(password, userOptional.get().getPassword())) {
            return userOptional.get();
        } else {
            return null;
        }
    }

    @Override
    public void saveRefreshToken(int userId, String refreshToken) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        optionalUser.ifPresent(user -> {
            TokenEntity existingToken = tokenRepository.findByUserId(userId);

            if (existingToken != null) {
                existingToken.setRefreshToken(refreshToken);
                existingToken.setUpdatedAt(LocalDateTime.now());
            } else {
                TokenEntity newToken = new TokenEntity();
                newToken.setUser(user);
                newToken.setRefreshToken(refreshToken);
                newToken.setUpdatedAt(LocalDateTime.now());
                tokenRepository.save(newToken);
            }
        });
    }

    @Override
    public void removeRefreshTokens() {
        List<TokenEntity> tokens = tokenRepository.findAllByRefreshTokenIsNotNull();

        tokenRepository.deleteAll(tokens);
    }


    private boolean isEmailValid(String email) {
        // Implement email validation logic
        return EmailValidator.isValid(email);
    }

    private boolean isPasswordValid(String password) {
        // Implement password validation logic
        return password != null && password.length() >= 8 && password.length() <= 20;
    }

    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
    public UserResponseDTO createUserResponseDTO(UserEntity userEntity) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(userEntity.getId());
        responseDTO.setFirstName(userEntity.getFirstName());
        responseDTO.setLastName(userEntity.getLastName());
        responseDTO.setEmail(userEntity.getEmail());
        responseDTO.setDisplayName(userEntity.getFirstName() + " " + userEntity.getLastName());
        return responseDTO;
    }

}