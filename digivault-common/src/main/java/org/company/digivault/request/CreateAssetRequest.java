package org.company.digivault.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.company.digivault.models.AssetServiceProvider;
import org.company.digivault.models.AssetType;

public class CreateAssetRequest {

  @JsonProperty
  private Long userId;

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

  public Long getUserId() {
    return userId;
  }

  public AssetType getType() {
    return type;
  }

  public AssetServiceProvider getServiceProvider() {
    return serviceProvider;
  }

  public String getAccountId() {
    return accountId;
  }

  public String getAccountHolderName() {
    return accountHolderName;
  }

  public Long getAmountInvested() {
    return amountInvested;
  }

  public boolean isNomineeReg() {
    return isNomineeReg;
  }

  public String getNomineeName() {
    return nomineeName;
  }

  public String getNotes() {
    return notes;
  }
}
