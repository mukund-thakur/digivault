package org.digivault.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.dropwizard.Application;
import io.dropwizard.auth.PolymorphicAuthDynamicFeature;
import io.dropwizard.auth.PolymorphicAuthValueFactoryProvider;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.company.digivault.api.AssetManagementService;
import org.company.digivault.api.UserResource;
import org.company.digivault.config.DigiVaultConfiguration;
import org.digivault.dao.AssetDao;
import org.digivault.dao.LoggedInUserDao;
import org.digivault.dao.UserDao;
import org.company.digivault.models.AuthUser;
import org.digivault.server.auth.JwtAuthFilter;
import org.digivault.server.auth.JwtAuthenticator;
import org.digivault.server.auth.JwtAuthorizer;
import org.digivault.services.AssetMetaService;
import org.digivault.services.LoggedInUserMetaService;
import org.digivault.services.UserMetaService;
import org.digivault.services.impl.RDBMSAssetMetaServiceImpl;
import org.digivault.services.impl.RDBMSLoggedInUserMetaService;
import org.digivault.services.impl.RDBMSUserMetaServiceImpl;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigiVault extends Application<DigiVaultConfiguration> {

  private static Logger LOG = LoggerFactory.getLogger(DigiVault.class);

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
    registerAuthFilters(environment);
  }

  private void registerAuthFilters(Environment environment) {
    LoggedInUserDao loggedInUserDao = new LoggedInUserDao(hibernateBundle.getSessionFactory());
    LoggedInUserMetaService loggedInUserMetaService = new RDBMSLoggedInUserMetaService(loggedInUserDao);
    JwtAuthenticator jwtAuthenticator = new UnitOfWorkAwareProxyFactory(hibernateBundle)
            .create(JwtAuthenticator.class, LoggedInUserMetaService.class, loggedInUserMetaService);
    final JwtAuthFilter jwtAuthFilter = new JwtAuthFilter.Builder()
            .setAuthenticator(jwtAuthenticator)
            .setAuthorizer(new JwtAuthorizer())
            .buildAuthFilter();
    final  PolymorphicAuthDynamicFeature feature =
            new PolymorphicAuthDynamicFeature<>(
            ImmutableMap.of(AuthUser.class, jwtAuthFilter));
    final PolymorphicAuthValueFactoryProvider.Binder<AuthUser> binder =
            new PolymorphicAuthValueFactoryProvider.Binder<>(
            ImmutableSet.of(AuthUser.class));
    environment.jersey().register(feature);
    environment.jersey().register(binder);
    environment.jersey().register(RolesAllowedDynamicFeature.class);
  }

  private void addUserResource(Environment environment) {
    UserDao userDao = new UserDao(hibernateBundle.getSessionFactory());
    AssetDao assetDao = new AssetDao(hibernateBundle.getSessionFactory());
    LoggedInUserDao loggedInUserDao = new LoggedInUserDao(hibernateBundle.getSessionFactory());
    UserMetaService userMetaService = new RDBMSUserMetaServiceImpl(userDao);
    AssetMetaService assetMetaService = new RDBMSAssetMetaServiceImpl(assetDao);
    LoggedInUserMetaService loggedInUserMetaService = new RDBMSLoggedInUserMetaService(loggedInUserDao);
    final UserResource userResource = new UserResource(userMetaService, assetMetaService, loggedInUserMetaService);
    environment.jersey().register(userResource);
  }

  private void addAssetResource(Environment environment) {
    AssetDao assetDao = new AssetDao(hibernateBundle.getSessionFactory());
    UserDao userDao = new UserDao(hibernateBundle.getSessionFactory());
    UserMetaService userMetaService = new RDBMSUserMetaServiceImpl(userDao);
    AssetMetaService assetMetaService = new RDBMSAssetMetaServiceImpl(assetDao);
    final AssetManagementService assetManagementService = new AssetManagementService(assetMetaService, userMetaService);
    environment.jersey().register(assetManagementService);
  }

  public static void main(String[] args) throws Exception {
    LOG.info("Starting DigiVault Server");
    new DigiVault().run(args);
  }

}
