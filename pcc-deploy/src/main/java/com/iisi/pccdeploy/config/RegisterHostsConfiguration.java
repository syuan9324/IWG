package com.iisi.pccdeploy.config;

import com.iisi.pccdeploy.service.HostConnectionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class RegisterHostsConfiguration {

    @Bean
    public HostConnectionProperties hostConnectionProperties(){
        LoaderOptions options = new LoaderOptions();
        options.setAllowDuplicateKeys(false);
        final Yaml yaml = new Yaml(new Constructor(HostConnectionProperties.class,options));
        try (final InputStream in = this.getClass().getResourceAsStream("/hostsConnection.yml")) {
            return yaml.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
