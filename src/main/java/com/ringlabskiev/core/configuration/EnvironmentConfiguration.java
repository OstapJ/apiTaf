package com.ringlabskiev.core.configuration;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:com/ringlabskiev/core/envConfiguration.properties"})
public interface EnvironmentConfiguration extends Config {

    @DefaultValue("prod")
    String env();

    @Key("servers.${env}.name")
    String name();

    @Key("servers.${env}.baseUrl")
    String baseUrl();

    @Key("servers.${env}.basePath")
    String basePath();

    @Key("servers.${env}.api_key")
    String nasaApiKey();

}
