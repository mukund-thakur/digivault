package org.company.digivault.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.company.digivault.models.AssetServiceProvider;
import org.company.digivault.models.AssetType;

public class UpdateAssetRequest {

  @JsonProperty
  private AssetType type;

  @JsonProperty
  private AssetServiceProvider serviceProvider;

  @JsonProperty
  private String accountId;

  @JsonProperty
  private String accountHolderName;

  @JsonProperty
  private Long amountInvested;

  @JsonProperty
  private boolean isNomineeReg;

  @JsonProperty
  private String nomineeName;

  @JsonProperty
  private String notes;

  @JsonProperty
  private String documentId;

  public AssetType getType() {
    return type;
  }

  public void setType(AssetType type) {
    this.type = type;
  }

  public AssetServiceProvider getServiceProvider() {
    return serviceProvider;
  }

  public void setServiceProvider(AssetServiceProvider serviceProvider) {
    this.serviceProvider = serviceProvider;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getAccountHolderName() {
    return accountHolderName;
  }

  public void setAccountHolderName(String accountHolderName) {
    this.accountHolderName = accountHolderName;
  }

  public Long getAmountInvested() {
    return amountInvested;
  }

  public void setAmountInvested(Long amountInvested) {
    this.amountInvested = amountInvested;
  }

  public boolean isNomineeReg() {
    return isNomineeReg;
  }

  public void setNomineeReg(boolean nomineeReg) {
    isNomineeReg = nomineeReg;
  }

  public String getNomineeName() {
    return nomineeName;
  }

  public void setNomineeName(String nomineeName) {
    this.nomineeName = nomineeName;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getDocumentId() {
    return documentId;
  }

  public void setDocumentId(String documentId) {
    this.documentId = documentId;
  }
}
