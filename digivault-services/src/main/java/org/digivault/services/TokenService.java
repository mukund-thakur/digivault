package org.digivault.services;

import java.util.Set;

import org.company.digivault.models.Role;

public interface TokenService<R> {

  String createToken(String userPrincipal, Set<Role> roles);

  R decodeToken(String token);
}
