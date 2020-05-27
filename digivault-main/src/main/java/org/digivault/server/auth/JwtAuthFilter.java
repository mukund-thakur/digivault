package org.digivault.server.auth;

import java.io.IOException;

import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthenticationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;

import static org.company.digivault.config.DigiVaultConstants.AUTHORIZATION_HEADER;


public class JwtAuthFilter extends AuthFilter<ContainerRequestContext, AuthUser> {

  private JwtAuthenticator jwtAuthenticator;

  public JwtAuthFilter(JwtAuthenticator jwtAuthenticator) {
    this.jwtAuthenticator = jwtAuthenticator;
  }

  @Override
  public void filter(ContainerRequestContext containerRequestContext) throws IOException {
    String token = containerRequestContext.getHeaderString(AUTHORIZATION_HEADER);

    if (token == null) {
      sendUnAuthorised("Authorization header not present");
    }

    try {
      jwtAuthenticator.authenticate(new Credentials(token));
    } catch (AuthenticationException ex) {
      sendUnAuthorised(ex.getMessage());
    }
  }

  private void sendUnAuthorised(String message) {
    throw new WebApplicationException(message, Response.Status.UNAUTHORIZED);
  }
}
