package org.acme.ukk.absensi.core.jwt.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtil {

  private static final String ADMIN_SECRET_KEY = "admin.jwt.service-key.secret.smk.2.tabanan";
  private static final String SCANNER_SECRET_KEY = "scanner.jwt.service-key.secret.smk.2.tabanan";
  private static final String USER_SECRET_KEY = "user.jwt.service-key.secret";
  private static final long EXPIRATION_TIME = 864000000; //60000 864000000 15000

  private JwtTokenUtil() {}


  private static Key getAdminPrivateKey() {
    return Keys.hmacShaKeyFor(ADMIN_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
  }

  private static Key getScannerPrivateKey() {
    return Keys.hmacShaKeyFor(SCANNER_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
  }

  private static Key getUserPrivateKey() {
    return Keys.hmacShaKeyFor(USER_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
  }

  //Generate Admin Token
  public static String generateAdminToken(String subject) {
    return Jwts
      .builder()
      .setSubject(subject)
      .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
      .signWith(getAdminPrivateKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  //Generate Scanner Token
  public static String generateScannerToken(String subject) {
    return Jwts
      .builder()
      .setSubject(subject)
      .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
      .signWith(getScannerPrivateKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  //Generate User Token
  public static String generateUserToken(String subject) {
    return Jwts
      .builder()
      .setSubject(subject)
      .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
      .signWith(getUserPrivateKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  //Validate token
  public static boolean validateToken(String token, int number) {
    Key selectedKey = switch(number) {
      case 1 -> getAdminPrivateKey();
      case 2-> getScannerPrivateKey();
      case 3-> getUserPrivateKey();
      default -> throw new IllegalArgumentException("Invalid number!!");
    };
    try {
      Jwts
        .parserBuilder()
        .setSigningKey(selectedKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  //Get Subject from token
  public static String getSubjectFromToken(String token) {
    Claims claims = Jwts
      .parserBuilder()
      .setSigningKey(getAdminPrivateKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
    return claims.getSubject();
  }

  public static String convertToken(String token) {
    return token.substring("Bearer".length()).trim();
  }
}
