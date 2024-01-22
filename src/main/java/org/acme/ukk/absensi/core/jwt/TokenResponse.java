package org.acme.ukk.absensi.core.jwt;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.acme.ukk.absensi.core.util.jackson.TokenSerialize;

@Getter
@JsonSerialize(using = TokenSerialize.class)
public class TokenResponse {

  private String token;
  private String message;

  public TokenResponse(String token, String message) {
    this.token = token;
    this.message = message;
  }
}
