package org.phoenix.paywall.payload.request;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank
    @Size(max = 50)
    private String email;

    // private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    private String activationCode;
    private Boolean active;
    private String firstName;
    private String lastName;
    private String role;

    private String associatedPartyIds;
    private String addedPartyIds;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public String getActivationCode() {
        return activationCode ;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Boolean getActive(){
        return active;
    }

    public void setActive(Boolean active){
        this.active = active;
    }


    public void setRole(String role) {
        this.role = role;
    }

    // public Set<String> getRole() {
    //     return role;
    // }

    // public void setRole(Set<String> role) {
    //     this.role = role;
    // }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
