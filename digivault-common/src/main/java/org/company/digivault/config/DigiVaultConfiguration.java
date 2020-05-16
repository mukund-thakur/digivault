package org.company.digivault.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class DigiVaultConfiguration extends Configuration {

  @Valid
  @NotNull
  private DataSourceFactory dataSourceFactory = new DataSourceFactory();

  @JsonProperty("database")
  public DataSourceFactory getDataSourceFactory() {
    return dataSourceFactory;
  }

  public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
    this.dataSourceFactory = dataSourceFactory;
  }
}
