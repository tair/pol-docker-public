package org.phoenix.paywall.payload.response;

import java.util.List;

import org.phoenix.paywall.model.User;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private User user;
    private String accessToken;

    public JwtResponse(String accessToken, Long id, User user) {
        this.token = accessToken;
        this.id = id;
        this.user = user;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
      this.token = accessToken;
    }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
