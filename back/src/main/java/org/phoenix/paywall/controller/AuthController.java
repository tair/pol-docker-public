package org.phoenix.paywall.controller;
import org.phoenix.paywall.model.User;
import org.phoenix.paywall.payload.request.ActivationRequest;
import org.phoenix.paywall.payload.request.LoginRequest;
import org.phoenix.paywall.payload.request.SignupRequest;
import org.phoenix.paywall.payload.request.PassResetRequest;
import org.phoenix.paywall.payload.request.PasswordRequest;
import org.phoenix.paywall.payload.request.UpdateUserRequest;
import org.phoenix.paywall.payload.response.JwtResponse;
import org.phoenix.paywall.payload.response.MessageResponse;
import org.phoenix.paywall.repository.UserRepository;
import org.phoenix.paywall.security.jwt.JwtUtils;
import org.phoenix.paywall.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        // System.out.println(signUpRequest.getFirstName() + " " + signUpRequest.getLastName());
        signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
        User user = new User(signUpRequest);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error:" + e.getMessage()));
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @PostMapping("/activate")
    public ResponseEntity<?> activateUser(@Valid @RequestBody ActivationRequest activationRequest) {
        User user = userRepository.findByEmail(activationRequest.getEmail());
        if (user == null) {
            return ResponseEntity.ok(new MessageResponse("Error: User not found! Please make sure URL is same as what it is in the email sent."));
        }
        if (!user.getActivationCode().equals(activationRequest.getActivationCode())) {
            return ResponseEntity.ok(new MessageResponse("Error: Incorrect activation code for the user!"));
        }
        if(user.getActive() == true){
            return ResponseEntity.ok(new MessageResponse("User already active!"));
        }
        user.setActive();
        try{
            userRepository.save(user);
        }catch(Exception e){
            throw e;
        }
        return ResponseEntity.ok(new MessageResponse("User activated successfully!"));
    }
    @PostMapping("/recover")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody PassResetRequest passResetRequest) {
        String email = passResetRequest.getEmail();
        // Validate email
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }
        // Generate password reset token
        String passwordResetToken = UUID.randomUUID().toString();
        System.out.println(passwordResetToken);
        // Save password reset token to database
        User user = userRepository.findByEmail(email);
        user.setPasswordResetToken(passwordResetToken);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse(passwordResetToken));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if(userDetails.getActive() == true){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), new User(userDetails)));
        }else{
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User has not been activated, please check email to activate the user before logging in!"));
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<?> newPassword(@Valid @RequestBody PasswordRequest passwordrequest) {
        String email = passwordrequest.getEmail();
        String passwordResetToken = passwordrequest.getPasswordResetToken();
        if(passwordResetToken == null || passwordResetToken.trim().isEmpty()) {
            return ResponseEntity.ok(new MessageResponse("Password reset token cannot be null or empty."));
        }
        String password = passwordrequest.getPassword();
        User user = userRepository.findByEmailAndPasswordResetToken(email, passwordResetToken);
        if(user != null){
            user.setPassword(encoder.encode(password));
            user.setPasswordResetToken(null); // invalidate the token
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Password has been changed."));
        } else {
            return ResponseEntity.ok(new MessageResponse("Invalid email/passwordresettoken combination."));
        }
    }

    @PostMapping("/updateuser")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest updatedUserRequest) {
        if (updatedUserRequest.getUsername() == null) {
            System.out.println("No getUsername found!");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: username not found!"));
        }
        User user = userRepository.findByUsername(updatedUserRequest.getUsername());
        if (user == null) {
            System.out.println("No user found!");
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User not found!"));
        }
        if (updatedUserRequest.getFirstName() != null) {
            user.setFirstName(updatedUserRequest.getFirstName());
        }
        if (updatedUserRequest.getLastName() != null) {
            user.setLastName(updatedUserRequest.getLastName());
        }
        if (updatedUserRequest.getEmail() != null) {
            user.setEmail(updatedUserRequest.getEmail());
            user.setUsername(updatedUserRequest.getEmail());
        }
        if (updatedUserRequest.getAddedPartyIds() != null) {
            user.setAddedPartyIds(updatedUserRequest.getAddedPartyIds());
        }
        
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }
}
