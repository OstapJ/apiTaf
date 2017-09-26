package com.ringlabskiev.api.services;

import com.jayway.restassured.response.Response;
import com.ringlabskiev.api.dto.RoverPhotos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface RoverPhotoService {



    @GET("rovers/curiosity/photos")
    Call<RoverPhotos> getRoverPhotoMetadataOnSol(@Query("api_key") String authorization, @Query("sol") String unitTime);

    @GET("rovers/curiosity/photos")
    Call<RoverPhotos> getRoverPhotoMetadataOnSol(@Query("api_key") String authorization, @Query("sol") String unitTime, @Query("camera") String camera);

    @GET("rovers/curiosity/photos")
    Call<RoverPhotos> getRoverPhotoMetadataOnEarthDate(@Query("api_key") String authorization, @Query("earth_date") String unitTime);

    @GET("rovers/curiosity/photos")
    Call<RoverPhotos> getRoverPhotoMetadata(@Query("api_key") String authorization, @Query("sol") String unitTime, @Query("camera") String camera);

    @GET()
    Call<Response> getRoverPhoto(@Url String url);


//    public static RoverPhotos getRoverPhotoMetadataOnSol(final String unitTime, final String time) {
//        return getRoverPhotoMetadataOnSol(unitTime, time, StringUtils.EMPTY, StringUtils.EMPTY);
//    }

//    public static RoverPhotos getRoverPhotoMetadataOnSol(final String unitTime, final String time, final String camera, final String cameraName) {
//        return nasaConfig().
//                when().
//                queryParam(unitTime, time).
//                queryParam(camera, cameraName).
//                get("rovers/curiosity/photos").
//                then().
//                log().status().log().body().
//                statusCode(HttpStatus.SC_OK).
//                extract().
//                response().as(RoverPhotos.class);
//    }
//
//    public static Response getRoverPhoto(final String resourcePath) {
//        return nasaConfig().
//                contentType(ContentType.BINARY).
//                when().
//                get(resourcePath).
//                then().
//                log().status().
//                statusCode(HttpStatus.SC_OK).
//                extract().
//                response();
//    }

}




