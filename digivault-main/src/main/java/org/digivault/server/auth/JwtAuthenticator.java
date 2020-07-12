package org.digivault.server.auth;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.hibernate.UnitOfWork;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import javax.ws.rs.container.ContainerRequestContext;
import org.company.digivault.models.AuthUser;
import org.company.digivault.models.Role;
import org.digivault.dao.LoggedInUserDao;
import org.digivault.entity.LoggedInUser;
import org.digivault.services.LoggedInUserMetaService;
import org.digivault.services.TokenService;
import org.digivault.services.impl.JwtTokenServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.company.digivault.config.DigiVaultConstants.AUTHORIZATION_HEADER;
import static org.company.digivault.config.DigiVaultConstants.ROLES_KEY;

public class JwtAuthenticator implements Authenticator<ContainerRequestContext, AuthUser> {

  private static Logger LOG = LoggerFactory.getLogger(JwtAuthenticator.class);

  private LoggedInUserMetaService loggedInUserMetaService;

  public JwtAuthenticator(LoggedInUserMetaService loggedInUserMetaService) {
    this.loggedInUserMetaService = loggedInUserMetaService;
  }

  @Override
  @UnitOfWork
  public Optional<AuthUser> authenticate(ContainerRequestContext containerRequestContext) throws AuthenticationException {
    TokenService<Claims> tokenService = new JwtTokenServiceImpl(loggedInUserMetaService);
    String token = containerRequestContext.getHeaderString(AUTHORIZATION_HEADER);

    if (token == null) {
      throw new AuthenticationException("Authorization header not present");
    }
    try {
      Claims claims = tokenService.decodeToken(token);
      LoggedInUser loggedInUser = loggedInUserMetaService.getLogInInfo(Long.valueOf(claims.getId()));
      if (loggedInUser == null || !token.equals(loggedInUser.getToken())) {
        throw new AuthenticationException("User not logged in. Please login again.");
      }
      LOG.info("User successfully authenticated with user id {} ", claims.getId());
      // jwt tokens doesn't support Set, so explicit conversion
      // to set is done here.
      Set<Role> roles = new HashSet<>(claims.get(ROLES_KEY, ArrayList.class));
      return Optional.of(new AuthUser(Long.valueOf(claims.getId()), roles));
    } catch (JwtException ex) {
      throw new AuthenticationException(ex.getMessage());
    }
  }
}