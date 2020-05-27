package org.digivault.server.auth;

import java.security.Principal;

public class AuthUser implements Principal {

  private String name;

  public AuthUser(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

}
