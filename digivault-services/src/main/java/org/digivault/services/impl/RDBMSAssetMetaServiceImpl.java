package org.digivault.services.impl;

import java.util.List;

import org.digivault.dao.AssetDao;
import org.digivault.entity.Asset;
import org.digivault.services.AssetMetaService;

public class RDBMSAssetMetaServiceImpl implements AssetMetaService {

  private AssetDao assetDao;

  public RDBMSAssetMetaServiceImpl(AssetDao assetDao) {
    this.assetDao = assetDao;
  }

  public Asset createAsset(Asset asset) {
    return assetDao.createAsset(asset);
  }

  public Asset updateAsset(Asset updatedAsset) {
    return assetDao.updateAsset(updatedAsset);
  }

  public Asset getAssetById(Long assetId) {
    return assetDao.getAssetById(assetId);
  }

  public List<Asset> getAllAssetOfUser(Long userId) {
    return assetDao.getAllAssetsOfUserId(userId);
  }
}
