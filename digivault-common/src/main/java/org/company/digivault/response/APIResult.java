package org.company.digivault.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class APIResult {

  @JsonProperty
  private String message;

  public APIResult(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
