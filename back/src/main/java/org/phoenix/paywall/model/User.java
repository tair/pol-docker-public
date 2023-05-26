package org.phoenix.paywall.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import org.phoenix.paywall.payload.request.SignupRequest;
import org.phoenix.paywall.security.services.UserDetailsImpl;

import java.util.List;

import org.phoenix.paywall.payload.request.ActivationRequest;
import org.phoenix.paywall.payload.request.PassResetRequest;
import org.phoenix.paywall.payload.request.PasswordRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(min = 3, max = 50)
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;
    private String activationCode;
    private Boolean active;
    private String firstName;
    private String lastName;
    private String role;
    private String passwordResetToken;

    private String associatedPartyIds;
    private String addedPartyIds;

    public User() {
    }

    public User(String username, String email, String password, String firstName, String lastName, String associatedPartyIds, String addedPartyIds) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.associatedPartyIds = associatedPartyIds;
        this.addedPartyIds = addedPartyIds;
    }

    public User(SignupRequest request) {
        this.username = request.getUsername();
        this.password = request.getPassword();
        this.email = request.getEmail();
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.role = request.getRole();
        this.activationCode = request.getActivationCode();
        this.associatedPartyIds = request.getAssociatedPartyIds();
        this.addedPartyIds = request.getAddedPartyIds();
        this.active = request.getActive();
    }

    public User(ActivationRequest activationRequest) {
        this.email = activationRequest.getEmail();
        this.activationCode = activationRequest.getActivationCode();
        // this.active = activationRequest.setActive(active);
    }

    public User(PasswordRequest passwordRequest) {
        this.email = passwordRequest.getEmail();
        this.passwordResetToken = passwordRequest.getPasswordResetToken();
        // this.active = activationRequest.setActive(active);
    }


    public User(UserDetailsImpl userDetails) {
        this.username = userDetails.getUsername();
        this.password = userDetails.getPassword();
        this.email = userDetails.getEmail();
        this.firstName = userDetails.getFirstName();
        this.lastName = userDetails.getLastName();
        this.role = userDetails.getRole();
        this.associatedPartyIds = userDetails.getAssociatedPartyIds();
        this.addedPartyIds = userDetails.getAddedPartyIds();
    }

    public User(PassResetRequest passResetRequest){
        this.email = passResetRequest.getEmail();
    }
    
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getActive(){
        return active;
    }

    public void setActive(){
        this.active = true;
    }

    public String getActivationCode(){
        return activationCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPasswordResetToken(){
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }
    
    public String getAssociatedPartyIds() {
        return associatedPartyIds;
    }

    public void setAssociatedPartyIds(String associatedPartyIds) {
        this.associatedPartyIds = associatedPartyIds;
    }

    public String getAddedPartyIds() {
        return addedPartyIds;
    }

    public void setAddedPartyIds(String addedPartyIds) {
        this.addedPartyIds = addedPartyIds;
    }
}
