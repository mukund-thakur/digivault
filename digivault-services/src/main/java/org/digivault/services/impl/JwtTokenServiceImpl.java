package org.digivault.services.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.company.digivault.models.Role;
import org.digivault.services.TokenService;

import static org.company.digivault.config.DigiVaultConstants.ROLES_KEY;
import static org.company.digivault.config.DigiVaultConstants.SECRET_KEY;
import static org.company.digivault.config.DigiVaultConstants.TOKEN_EXPIRY;

public class JwtTokenServiceImpl implements TokenService<Claims> {

  public String createToken(String userPrincipal, Set<Role> allowedRoles) {
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    byte[] apiKeySecretBytes = DatatypeConverter
            .parseBase64Binary(SECRET_KEY);
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    Map<String, Object> claims = new HashMap<String, Object>();
    claims.put(ROLES_KEY, allowedRoles);
    JwtBuilder builder = Jwts.builder()
            .setClaims(claims)
            .setIssuer("DigiVault")
            .setId(userPrincipal)
            .setSubject("Login")
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRY))
            .signWith(SignatureAlgorithm.HS256, signingKey);
    return builder.compact();
  }

  public Claims decodeToken(String token) {
    return Jwts.parser()
            .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
            .parseClaimsJws(token)
            .getBody();
  }
}
