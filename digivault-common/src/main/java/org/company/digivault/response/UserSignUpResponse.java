package org.company.digivault.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserSignUpResponse {

  @JsonProperty
  private Long userId;

  @JsonProperty
  private String name;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
