package org.digivault.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.company.digivault.models.AssetServiceProvider;
import org.company.digivault.models.AssetType;

@Entity
@Table(name = "digivault_asset")
@NamedQueries({
        @NamedQuery(name = "get_all_asset_of_user_id", query = "select OBJECT(a) from Asset a where a.userId = :userId")
})
public class Asset extends BaseEntity {

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "active")
  private boolean active = true;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private AssetType type;

  @Column(name = "service_provider")
  @Enumerated(EnumType.STRING)
  private AssetServiceProvider serviceProvider;

  @Column(name = "account_id")
  private String accountId;

  @Column(name = "account_holder_name")
  private String accountHolderName;

  @Column(name = "amount_invested")
  private Long amountInvested;

  @Column(name = "is_nominee_reg")
  private boolean isNomineeReg;

  @Column(name = "nominee_name")
  private String nomineeName;

  @Column(name = "notes")
  private String notes;

  @Column(name = "document_id")
  private String documentId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

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
