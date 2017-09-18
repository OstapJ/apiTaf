package com.ringlabskiev.api.services;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.ringlabskiev.api.dto.RoverPhotos;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import static com.ringlabskiev.core.configuration.RestConfig.nasaConfig;


public final class RoverPhotoService {

    private RoverPhotoService() {
    }

    public static RoverPhotos getRoverPhotoMetadata(final String unitTime, final String time) {
        return getRoverPhotoMetadata(unitTime, time, StringUtils.EMPTY, StringUtils.EMPTY);
    }

    public static RoverPhotos getRoverPhotoMetadata(final String unitTime, final String time, final String camera, final String cameraName) {
        return nasaConfig().
                when().
                queryParam(unitTime, time).
                queryParam(camera, cameraName).
                get("rovers/curiosity/photos").
                then().
                log().status().log().body().
                statusCode(HttpStatus.SC_OK).
                extract().
                response().as(RoverPhotos.class);
    }

    public static Response getRoverPhoto(final String resourcePath) {
        return nasaConfig().
                contentType(ContentType.BINARY).
                when().
                get(resourcePath).
                then().
                log().status().
                statusCode(HttpStatus.SC_OK).
                extract().
                response();
    }

}




