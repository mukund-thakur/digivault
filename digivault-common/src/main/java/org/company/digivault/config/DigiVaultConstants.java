package org.company.digivault.config;

public final class DigiVaultConstants {

  public static final String BASE_API_PATH = "/digivault";

  public static final String UM_BASE_API_PATH = BASE_API_PATH + "/um";

  public static final String DMS_BASE_API_PATH = BASE_API_PATH + "/dms";

  public static final String ASSET_BASE_API_PATH = DMS_BASE_API_PATH + "/asset";

  public static final String AUTHORIZATION_HEADER = "Authorization";

  public static final String SECRET_KEY = "776997B9D4D2B64F2385E99D96358F9FCE3E2612516F948206EC165A0A4B3F5F";

  // tokens will expire in 60 minutes.
  public static long TOKEN_EXPIRY = 1000 * 60 * 60;

  public static String ROLES_KEY = "roles";

}
