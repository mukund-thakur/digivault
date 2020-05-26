package org.digivault.server;

import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.company.digivault.api.AssetManagementService;
import org.company.digivault.api.UserResource;
import org.company.digivault.config.DigiVaultConfiguration;
import org.digivault.dao.AssetDao;
import org.digivault.dao.UserDao;
import org.digivault.entity.User;
import org.digivault.services.AssetMetaService;
import org.digivault.services.UserMetaService;
import org.digivault.services.impl.RDBMSAssetMetaServiceImpl;
import org.digivault.services.impl.RDBMSUserMetaServiceImpl;

public class DigiVault extends Application<DigiVaultConfiguration> {

  private final HibernateBundle<DigiVaultConfiguration> hibernateBundle = new
            ScanningHibernateBundle<DigiVaultConfiguration>("org.digivault.entity") {
              public PooledDataSourceFactory getDataSourceFactory(DigiVaultConfiguration digiVaultConfiguration) {
                return digiVaultConfiguration.getDataSourceFactory();
              }
            };

  @Override
  public void initialize(Bootstrap<DigiVaultConfiguration> bootstrap) {
    bootstrap.addBundle(hibernateBundle);
  }

  public void run(DigiVaultConfiguration digiVaultConfiguration, Environment environment) {
    addResources(environment);
  }

  private void addResources(Environment environment) {
    addUserResource(environment);
    addAssetResource(environment);
  }

  private void addUserResource(Environment environment) {
    UserDao userDao = new UserDao(hibernateBundle.getSessionFactory());
    AssetDao assetDao = new AssetDao(hibernateBundle.getSessionFactory());
    UserMetaService userMetaService = new RDBMSUserMetaServiceImpl(userDao);
    AssetMetaService assetMetaService = new RDBMSAssetMetaServiceImpl(assetDao);
    final UserResource userResource = new UserResource(userMetaService, assetMetaService);
    environment.jersey().register(userResource);
  }

  private void addAssetResource(Environment environment) {
    AssetDao assetDao = new AssetDao(hibernateBundle.getSessionFactory());
    AssetMetaService assetMetaService = new RDBMSAssetMetaServiceImpl(assetDao);
    final AssetManagementService assetManagementService = new AssetManagementService(assetMetaService);
    environment.jersey().register(assetManagementService);
  }

  public static void main(String[] args) throws Exception {
    new DigiVault().run(args);
  }

}
