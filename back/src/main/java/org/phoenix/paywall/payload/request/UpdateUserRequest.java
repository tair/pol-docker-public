package org.phoenix.paywall.payload.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {

    @Size(max = 50)
    private String email;
    @Size(min = 3, max = 50)
    private String username;
    private String firstName;
    private String lastName;
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
