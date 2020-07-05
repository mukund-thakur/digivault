package org.digivault.dao;

import java.util.ArrayList;
import java.util.List;

import io.dropwizard.hibernate.AbstractDAO;
import javax.persistence.Query;
import org.company.digivault.queries.AssetNamedQueries;
import org.digivault.entity.Asset;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

public class AssetDao extends DigiVaultBaseDao<Asset> {

  public AssetDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Asset createAsset(Asset asset) {
    return persist(asset);
  }

  public Asset updateAsset(Asset updatedAsset) {
    return persist(updatedAsset);
  }

  public Asset getAssetById(Long assetId) {
    return get(assetId);
  }

  public List<Asset> getAllAssetsOfUserId(Long userId) {
    Query query = currentSession().createNamedQuery(AssetNamedQueries.GET_ALL_ASSET_OF_USER_KEY);
    query.setParameter("userId", userId);
    List resultList = query.getResultList();
    List<Asset> assetList = new ArrayList<Asset>();
    for (Object obj : resultList) {
      assetList.add((Asset) obj);
    }
    return assetList;
  }
}
