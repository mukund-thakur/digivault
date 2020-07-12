package org.company.digivault.queries;

public class TokenNamedQueries {

  public static final String DELETE_TOKEN_OF_USER_KEY = "delete_token_of_user";

  public static final String DELETE_TOKEN_OF_USER_VALUE =
          "delete from LoggedInUser lu where lu.userId = :userId";
}
