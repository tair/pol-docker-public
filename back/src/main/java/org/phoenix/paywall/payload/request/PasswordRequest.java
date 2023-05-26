package org.phoenix.paywall.payload.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordRequest {
    
    @NotBlank
    private String email;

	@NotBlank
    @NotBlank
    @Size(min = 6, max = 40)
	private String password;

    private String passwordResetToken;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getPasswordResetToken() {
		return passwordResetToken;
	}
    
}
