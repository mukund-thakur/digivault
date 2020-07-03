package org.company.digivault.queries;

public final class AssetNamedQueries {

  public static final String GET_ALL_ASSET_OF_USER_KEY = "get_all_asset_of_user_id";

  public static final String GET_ALL_ASSET_OF_USER_VALUE = "select OBJECT(a) from Asset a where a.userId = :userId";

}
