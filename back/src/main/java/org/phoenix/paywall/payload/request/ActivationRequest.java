package org.phoenix.paywall.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ActivationRequest {

    @NotBlank
    @Size(max = 50)
    private String email;

    private String activationCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivationCode() {
        return activationCode ;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

}
