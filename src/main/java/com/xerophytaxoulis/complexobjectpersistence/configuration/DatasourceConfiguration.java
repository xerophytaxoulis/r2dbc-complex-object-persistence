package com.xerophytaxoulis.complexobjectpersistence.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import com.xerophytaxoulis.complexobjectpersistence.service.PersonReadConverter;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;

@Configuration
@ConfigurationProperties(prefix = "r2dbc")
public class DatasourceConfiguration extends AbstractR2dbcConfiguration {

    private String driverName;

    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(driverName);
    }

    @Override
    public List<Object> getCustomConverters() {
        return List.of(new PersonReadConverter());
    }

    
}
