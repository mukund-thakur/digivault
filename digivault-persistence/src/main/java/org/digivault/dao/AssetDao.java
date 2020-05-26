package org.digivault.dao;

import java.util.ArrayList;
import java.util.List;

import io.dropwizard.hibernate.AbstractDAO;
import javax.persistence.Query;
import org.digivault.entity.Asset;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

public class AssetDao extends AbstractDAO<Asset> {

  public AssetDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Asset createAsset(Asset asset) {
    return persist(asset);
  }

  public Asset getAssetById(Long assetId) {
    return get(assetId);
  }

  public List<Asset> getAllAssetsOfUserId(Long userId) {
    Query query = currentSession().createNamedQuery("get_all_asset_of_user_id");
    query.setParameter("userId", userId);
    List resultList = query.getResultList();
    List<Asset> assetList = new ArrayList<Asset>();
    for (Object obj : resultList) {
      assetList.add((Asset) obj);
    }
    return assetList;
  }
}
