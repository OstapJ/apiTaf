package com.ringlabskiev.test;

import com.jayway.restassured.path.json.JsonPath;
import com.ringlabskiev.api.dto.RoverPhotos;
import com.ringlabskiev.api.services.RoverPhotoService;
import com.ringlabskiev.core.BaseTest;
import com.ringlabskiev.core.annotations.TestData;
import com.ringlabskiev.core.configuration.EnvironmentConfiguration;
import com.ringlabskiev.core.configuration.RestConfig;
import com.ringlabskiev.core.converters.DateConverter;
import org.aeonbits.owner.ConfigCache;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class MarsRoverPhotos extends BaseTest {


    @Test(description = "TestCase-1 Requested Mars photo by Sol and Earth date are equal", dataProvider = GENERIC_DP)
    @TestData("testCanCheckMarsPhotosAreEqual.json")
    public void testCanCheckMarsPhotosAreEqual(final JsonPath testData) throws IOException {
        String earthDate = DateConverter.convertSolToEarthDateForRover(testData.getInt("time"), testData.get("landingDate"));
        RoverPhotoService roverPhotoService = RestConfig.getConfiguredApi().create(RoverPhotoService.class);
        EnvironmentConfiguration envConfig = ConfigCache.getOrCreate(EnvironmentConfiguration.class, System.getProperties());

        RoverPhotos roverPhotosOnSol = roverPhotoService.getRoverPhotoMetadataOnSol(envConfig.nasaApiKey(), testData.get("time")).execute().body();
        RoverPhotos roverPhotosOnEarthDate = roverPhotoService.getRoverPhotoMetadataOnEarthDate(envConfig.nasaApiKey(),  earthDate).execute().body();

        List<RoverPhotos.Photo> photoMetaOnSol = roverPhotosOnSol.getPhotos().subList(0, 10);
        List<RoverPhotos.Photo> photoMetaOnEarthDate = roverPhotosOnEarthDate.getPhotos().subList(0, 10);

//        softly.assertThat(getMd5Hexes(photoMetaOnSol)).as("Photos which are received by Sol and Earth date are different").
//                hasSameElementsAs(getMd5Hexes(photoMetaOnEarthDate));
        softly.assertThat(photoMetaOnSol).as("Metadata of photos which are received by Sol and Earth date are different").
                hasSameElementsAs(photoMetaOnEarthDate);
        softly.assertAll();
    }

//    @Test(description = "TestCase-1 Requested Mars photo by Sol and Earth date are equal", dataProvider = GENERIC_DP)
//    @TestData("testCanCheckMarsPhotosAreEqual.json")
//    public void testCanCheckMarsPhotosAreEqual(final JsonPath testData) {
//        String earthDate = DateConverter.convertSolToEarthDateForRover(testData.getInt("time"), testData.get("landingDate"));
//
//        RoverPhotos roverPhotosOnSol = RoverPhotoService.getRoverPhotoMetadataOnSol(testData.get("unitTime"), testData.get("time"));
//        RoverPhotos roverPhotosOnEarthDate = RoverPhotoService.getRoverPhotoMetadataOnSol("earth_date", earthDate);
//
//        List<RoverPhotos.Photo> photoMetaOnSol = roverPhotosOnSol.getPhotos().subList(0, 10);
//        List<RoverPhotos.Photo> photoMetaOnEarthDate = roverPhotosOnEarthDate.getPhotos().subList(0, 10);
//
//        softly.assertThat(getMd5Hexes(photoMetaOnSol)).as("Photos which are received by Sol and Earth date are different").
//                hasSameElementsAs(getMd5Hexes(photoMetaOnEarthDate));
//        softly.assertThat(photoMetaOnSol).as("Metadata of photos which are received by Sol and Earth date are different").
//                hasSameElementsAs(photoMetaOnEarthDate);
//        softly.assertAll();
//    }

//    @Test(description = "TestCase-2 Any camera made 10 times less images than any other", dataProvider = GENERIC_DP)
//    @TestData("testCanCheckAnyCameraMadeLessThanTenImagesThanAnyOther.json")
//    public void testCanCheckAnyCameraMadeLessThanTenImagesThanAnyOther(final JsonPath testData) {
//        List<String> cameraNames = testData.getList("cameras");
//        RoverPhotoService roverPhotoService = RestConfig.getConfiguredApi().create(RoverPhotoService.class);
//
//
//        Set<Integer> amountOfPhotoByEachCamera = cameraNames.stream().map(camera -> roverPhotoService.
//                getRoverPhotoMetadataOnSol(testData.get("time"), camera).execute().body().getPhotos().size()).collect(Collectors.toSet());
//
//        assertThat(Collections.max(amountOfPhotoByEachCamera) - Collections.min(amountOfPhotoByEachCamera)).
//                as("Camera made 10 times more images than any other").isLessThan(10);
//    }
//
//    private List<String> getMd5Hexes(List<RoverPhotos.Photo> photoHashOnSol) {
//        RoverPhotoService roverPhotoService = RestConfig.getConfiguredApi().create(RoverPhotoService.class);
//        return photoHashOnSol.stream().
//                map(photoMeta -> DigestUtils.md5Hex(roverPhotoService.getRoverPhoto(photoMeta.getImgSrc()).execute().body().asByteArray())).
//                collect(Collectors.toList());
//    }
}
