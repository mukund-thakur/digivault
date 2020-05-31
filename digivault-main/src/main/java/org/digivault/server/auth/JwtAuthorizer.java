package org.digivault.server.auth;

import io.dropwizard.auth.Authorizer;
import org.company.digivault.models.AuthUser;

public class JwtAuthorizer implements Authorizer<AuthUser> {

  @Override
  public boolean authorize(AuthUser principal, String role) {
    return principal.getRoles().contains(role);
  }
}
