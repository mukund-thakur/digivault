package org.company.digivault.exception;

public class SignInException extends DigiVaultException {

  public SignInException(String message) {
    super(message);
  }

  public SignInException(String message, Throwable cause) {
    super(message, cause);
  }

}
