package org.company.digivault.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserSignInRequest {

  @JsonProperty
  private Long userId;

  @JsonProperty
  private String contactNum;

  @JsonProperty
  private String email;

  @JsonProperty
  private String password;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getContactNum() {
    return contactNum;
  }

  public void setContactNum(String contactNum) {
    this.contactNum = contactNum;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
