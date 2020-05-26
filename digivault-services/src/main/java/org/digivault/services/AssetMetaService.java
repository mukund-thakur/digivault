package org.digivault.services;

import java.util.List;

import org.digivault.entity.Asset;

public interface AssetMetaService {

  Asset createAsset(Asset asset);

  Asset getAssetById(Long assetId);

  List<Asset> getAllAssetOfUser(Long userId);
}
