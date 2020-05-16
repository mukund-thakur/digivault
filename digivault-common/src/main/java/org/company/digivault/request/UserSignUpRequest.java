package org.company.digivault.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.company.digivault.models.Gender;

public class UserSignUpRequest {

  @JsonProperty
  private String name;

  @JsonProperty
  private String contactNum;

  @JsonProperty
  private String password;

  @JsonProperty
  private String dob;

  @JsonProperty
  private Gender gender;

  @JsonProperty
  private String gcmId;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getDob() {
    return dob;
  }

  public void setDob(String dob) {
    this.dob = dob;
  }

  public String getGcmId() {
    return gcmId;
  }

  public void setGcmId(String gcmId) {
    this.gcmId = gcmId;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }
}
