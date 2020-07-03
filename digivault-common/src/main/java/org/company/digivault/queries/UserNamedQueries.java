package org.company.digivault.queries;

public final class UserNamedQueries {

  public static final String GET_USER_BY_EMAIL_KEY = "get_user_by_email";

  public static final String GET_USER_BY_EMAIL_VALUE = "select OBJECT(a) from User a where a.email = :email";

  public static final String GET_USER_BY_CONTACT_KEY = "get_user_by_contact";

  public static final String GET_USER_BY_CONTACT_VALUE = "select OBJECT(a) from User a where a.contactNum = :contactNum";
}
