package org.digivault.server.auth;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.AuthenticationException;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.company.digivault.models.AuthUser;

@Priority(Priorities.AUTHENTICATION)
public class JwtAuthFilter extends AuthFilter<ContainerRequestContext, AuthUser> {

  @Override
  public void filter(ContainerRequestContext containerRequestContext) throws IOException {
    try {
      Optional<AuthUser> principal = authenticator.authenticate(containerRequestContext);
      if (principal.isPresent()) {
        containerRequestContext.setSecurityContext(new SecurityContext() {
          public Principal getUserPrincipal() {
            return principal.get();
          }

          public boolean isUserInRole(String role) {
            return JwtAuthFilter.this.authorizer.authorize(principal.get(), role, containerRequestContext);
          }

          public boolean isSecure() {
            return containerRequestContext.getSecurityContext().isSecure();
          }

          public String getAuthenticationScheme() {
            return "JWT_AUTH";
          }
        });
      }
    } catch (AuthenticationException ex) {
      sendUnAuthorised(ex.getMessage());
    }
  }

  private void sendUnAuthorised(String message) {
    throw new WebApplicationException(message, Response.Status.UNAUTHORIZED);
  }

  public static class Builder extends
          AuthFilterBuilder<ContainerRequestContext, AuthUser, JwtAuthFilter> {

    @Override
    protected JwtAuthFilter newInstance() {
      return new JwtAuthFilter();
    }
  }
}
