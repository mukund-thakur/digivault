package org.digivault.server.auth;

import java.util.Optional;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.digivault.services.TokenService;
import org.digivault.services.impl.JwtTokenServiceImpl;

public class JwtAuthenticator implements Authenticator<Credentials, AuthUser> {

  @Override
  public Optional<AuthUser> authenticate(Credentials credentials) throws AuthenticationException {
    TokenService<Claims> tokenService = new JwtTokenServiceImpl();
    try {
      Claims claims = tokenService.decodeToken(credentials.getToken());
      return Optional.of(new AuthUser(String.valueOf(claims.getId())));
    } catch (JwtException ex) {
      throw new AuthenticationException(ex.getMessage());
    }
  }
}