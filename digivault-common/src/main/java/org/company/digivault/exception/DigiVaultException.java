package org.company.digivault.exception;

public class DigiVaultException extends Exception {

  public DigiVaultException(String message) {
    super(message);
  }

  public DigiVaultException(String message, Throwable cause) {
    super(message, cause);
  }
}
