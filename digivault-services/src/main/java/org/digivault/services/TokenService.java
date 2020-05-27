package org.digivault.services;

public interface TokenService<R> {

  String createToken(String userPrincipal);

  R decodeToken(String token);
}
