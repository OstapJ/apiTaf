package com.ringlabskiev.core.configuration;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.config.DecoderConfig;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.config.RestAssuredConfig;
import com.jayway.restassured.filter.log.LogDetail;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigCache;

import static com.jayway.restassured.RestAssured.given;

public final class RestConfig {

    private static final String ENCODING = "UTF-8";
    private static EnvironmentConfiguration envConfig;

    static {
        envConfig = ConfigCache.getOrCreate(EnvironmentConfiguration.class, System.getProperties());
        RestAssured.config = new RestAssuredConfig().
                decoderConfig(new DecoderConfig(ENCODING)).
                encoderConfig(new EncoderConfig(ENCODING, ENCODING));
    }

    private RestConfig() {
    }

    public static RequestSpecification nasaConfig() {
        return given().
                spec(requestSpec()).
                queryParam("api_key", envConfig.nasaApiKey());
    }

    private static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .addHeader("Accept-Language", "en")
                .setContentType(ContentType.JSON)
                .setBaseUri(envConfig.baseUrl())
                .setBasePath(envConfig.basePath())
                .log(LogDetail.METHOD)
                .log(LogDetail.PATH)
                .log(LogDetail.BODY)
                .build();
    }
}
