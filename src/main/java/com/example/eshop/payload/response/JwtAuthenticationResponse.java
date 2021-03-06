package com.example.eshop.payload.response;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
  private String accessToken;
  private String tokenType = "Bearer";
  private int id;
  private String role;

  public JwtAuthenticationResponse(String accessToken, int id, String role) {
    this.accessToken = accessToken;
    this.id = id;
    this.role = role;
  }
}
