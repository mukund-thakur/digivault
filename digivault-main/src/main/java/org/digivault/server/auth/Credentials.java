package org.digivault.server.auth;

public class Credentials {

  private String token;

  public Credentials(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
