package org.digivault.server;

import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.company.digivault.api.UserResource;
import org.company.digivault.config.DigiVaultConfiguration;
import org.digivault.dao.UserDao;
import org.digivault.entity.User;
import org.digivault.services.UserMetaService;
import org.digivault.services.impl.RDBMSUserMetaServiceImpl;

public class DigiVault extends Application<DigiVaultConfiguration> {

  private final HibernateBundle<DigiVaultConfiguration> hibernateBundle = new
            HibernateBundle<DigiVaultConfiguration>(User.class) {
              public PooledDataSourceFactory getDataSourceFactory(DigiVaultConfiguration digiVaultConfiguration) {
                return digiVaultConfiguration.getDataSourceFactory();
              }
            };

  @Override
  public void initialize(Bootstrap<DigiVaultConfiguration> bootstrap) {
    bootstrap.addBundle(hibernateBundle);
  }

  public void run(DigiVaultConfiguration digiVaultConfiguration, Environment environment) {
    UserDao userDao = new UserDao(hibernateBundle.getSessionFactory());
    UserMetaService userMetaService = new RDBMSUserMetaServiceImpl(userDao);
    final UserResource userResource = new UserResource(userMetaService);
    environment.jersey().register(userResource);
  }

  public static void main(String[] args) throws Exception {
    new DigiVault().run(args);
  }

}
