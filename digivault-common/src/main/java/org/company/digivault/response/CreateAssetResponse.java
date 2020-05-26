package org.company.digivault.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateAssetResponse {

  @JsonProperty
  private Long assetId;

  public void setAssetId(Long assetId) {
    this.assetId = assetId;
  }
}
