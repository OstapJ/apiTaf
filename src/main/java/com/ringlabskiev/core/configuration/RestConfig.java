package com.ringlabskiev.core.configuration;

import okhttp3.OkHttpClient;
import org.aeonbits.owner.ConfigCache;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

public final class RestConfig {

    private static final String ENCODING = "UTF-8";
    private static EnvironmentConfiguration envConfig;
    private static Retrofit RETROFIT;

    static {
        envConfig = ConfigCache.getOrCreate(EnvironmentConfiguration.class, System.getProperties());
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(100, TimeUnit.SECONDS).readTimeout(100, TimeUnit.SECONDS).build();
        RETROFIT = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(envConfig.baseUrl())
                .client(okHttpClient)
                .build();

//        RestAssured.config = new RestAssuredConfig().
//                decoderConfig(new DecoderConfig(ENCODING)).
//                encoderConfig(new EncoderConfig(ENCODING, ENCODING));
    }

    private RestConfig() {}

    public static Retrofit getConfiguredApi(){
        return RETROFIT;
    }


//    public static RequestSpecification nasaConfig() {
//        return given().
//                spec(requestSpec()).
//                queryParam("api_key", envConfig.nasaApiKey());
//    }
//
//    private static RequestSpecification requestSpec() {
//        return new RequestSpecBuilder()
//                .addHeader("Accept-Language", "en")
//                .setContentType(ContentType.JSON)
//                .setBaseUri(envConfig.baseUrl())
//                .setBasePath(envConfig.basePath())
//                .log(LogDetail.METHOD)
//                .log(LogDetail.PATH)
//                .log(LogDetail.BODY)
//                .build();
//    }
}
