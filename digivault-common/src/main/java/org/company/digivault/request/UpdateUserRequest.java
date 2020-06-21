package org.company.digivault.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.company.digivault.models.Gender;


public class UpdateUserRequest {

  @JsonProperty
  private String name;

  @JsonProperty
  private String email;

  @JsonProperty
  private boolean isEmailVerified;

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

  @JsonProperty
  private String pan;

  @JsonProperty
  private String aadhar;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isEmailVerified() {
    return isEmailVerified;
  }

  public void setEmailVerified(boolean emailVerified) {
    isEmailVerified = emailVerified;
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

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getGcmId() {
    return gcmId;
  }

  public void setGcmId(String gcmId) {
    this.gcmId = gcmId;
  }

  public String getPan() {
    return pan;
  }

  public void setPan(String pan) {
    this.pan = pan;
  }

  public String getAadhar() {
    return aadhar;
  }

  public void setAadhar(String aadhar) {
    this.aadhar = aadhar;
  }
}
