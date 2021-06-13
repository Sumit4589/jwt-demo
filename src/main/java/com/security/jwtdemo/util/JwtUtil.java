package com.security.jwtdemo.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

  private static final byte[] SECRET_KEY = "key".getBytes();

  public static String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap();
    return createToken(claims, userDetails.getUsername());
  }

  private static String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder().setClaims(claims).setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
  }

  public static boolean validateToken(String token, UserDetails userDetails) {
    String username = extractUser(token);
    return userDetails.getUsername().equalsIgnoreCase(username);
  }

  public static String extractUser(String token) {
    Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    return claims.getSubject();

  }
}
