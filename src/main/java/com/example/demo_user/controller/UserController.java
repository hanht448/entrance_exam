package com.example.demo_user.controller;


import com.example.demo_user.dto.*;
import com.example.demo_user.entity.UserEntity;
import com.example.demo_user.exception.EmailNotAvailableException;
import com.example.demo_user.exception.ValidationException;
import com.example.demo_user.service.UserService;
import com.example.demo_user.service.TokenService;
import com.example.demo_user.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<UserResponseDTO> signUp(@RequestBody UserSignUpRequest request) {
        try {
            UserResponseDTO response = userService.signUpUser(
                    request.getEmail(),
                    request.getPassword(),
                    request.getFirstName(),
                    request.getLastName()
            );
            System.out.println("response" + response);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ValidationException | EmailNotAvailableException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity<SignInResponseDTO> signIn(@RequestBody SignInRequestDTO signInRequestDTO) {
        try {
            UserEntity authenticatedUser = userService.authenticateUser(signInRequestDTO.getEmail(), signInRequestDTO.getPassword());
            System.out.println("go to in signIn");
            System.out.println("authenticatedUser: " + authenticatedUser);
            if (authenticatedUser != null) {
                // create JWT token
                String jwtToken = JwtTokenUtil.createJwtToken(authenticatedUser);

                String refreshToken = JwtTokenUtil.createJwtToken(authenticatedUser);
                userService.saveRefreshToken(authenticatedUser.getId(), refreshToken);

                SignInResponseDTO responseDTO = new SignInResponseDTO(
                    new UserResponseDTO(
                            authenticatedUser.getFirstName(),
                            authenticatedUser.getLastName(),
                            authenticatedUser.getEmail(),
                            authenticatedUser.getFirstName() + " " + authenticatedUser.getLastName()
                    ),
                    jwtToken,
                    refreshToken
                );
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            System.out.println("e: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/sign-out", method = RequestMethod.POST)
    public ResponseEntity<Void> signOut() {
        try {
            userService.removeRefreshTokens();

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("e : " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/refresh-token", method = RequestMethod.POST)
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(@RequestParam String refreshToken) {

        if (!tokenService.isValidRefreshToken(refreshToken)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String newToken = tokenService.generateRefreshToken();
        String newRefreshToken = tokenService.generateRefreshToken();
        
        tokenService.invalidateRefreshToken(refreshToken, newRefreshToken);

        RefreshTokenResponseDTO responseDTO = new RefreshTokenResponseDTO(newToken, newRefreshToken);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
