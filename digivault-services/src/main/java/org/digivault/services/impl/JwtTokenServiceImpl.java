package org.digivault.services.impl;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.digivault.services.TokenService;

public class JwtTokenServiceImpl implements TokenService<Claims> {

  private final String SECRET_KEY = "776997B9D4D2B64F2385E99D96358F9FCE3E2612516F948206EC165A0A4B3F5F";

  public String createToken(String userPrincipal) {
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    byte[] apiKeySecretBytes = DatatypeConverter
            .parseBase64Binary(SECRET_KEY);
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    long expiry = 1000 * 60;
    JwtBuilder builder = Jwts.builder()
            .setId(userPrincipal)
            .setSubject("Login")
            .setIssuer("DigiVault")
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiry))
            .signWith(SignatureAlgorithm.HS256, signingKey);
    return builder.compact();
  }

  public Claims decodeToken(String token) {
    Claims claims = Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
            .parseClaimsJws(token)
            .getBody();
    return claims;
  }
}
