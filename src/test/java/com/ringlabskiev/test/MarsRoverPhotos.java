package com.ringlabskiev.test;

import com.jayway.restassured.path.json.JsonPath;
import com.ringlabskiev.api.dto.RoverPhotos;
import com.ringlabskiev.api.services.RoverPhotoService;
import com.ringlabskiev.core.BaseTest;
import com.ringlabskiev.core.annotations.TestData;
import com.ringlabskiev.core.converters.DateConverter;
import org.apache.commons.codec.digest.DigestUtils;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class MarsRoverPhotos extends BaseTest {


    @Test(description = "TestCase-1 Requested Mars photo by Sol and Earth date are equal", dataProvider = GENERIC_DP)
    @TestData("testCanCheckMarsPhotosAreEqual.json")
    public void testCanCheckMarsPhotosAreEqual(final JsonPath testData) {
        String earthDate = DateConverter.convertSolToEarthDateForRover(testData.getInt("time"), testData.get("landingDate"));

        RoverPhotos roverPhotosOnSol = RoverPhotoService.getRoverPhotoMetadata(testData.get("unitTime"), testData.get("time"));
        RoverPhotos roverPhotosOnEarthDate = RoverPhotoService.getRoverPhotoMetadata("earth_date", earthDate);

        List<RoverPhotos.Photo> photoMetaOnSol = roverPhotosOnSol.getPhotos().subList(0, 10);
        List<RoverPhotos.Photo> photoMetaOnEarthDate = roverPhotosOnEarthDate.getPhotos().subList(0, 10);

        softly.assertThat(getMd5Hexes(photoMetaOnSol)).as("Photos which are received by Sol and Earth date are different").
                hasSameElementsAs(getMd5Hexes(photoMetaOnEarthDate));
        softly.assertThat(photoMetaOnSol).as("Metadata of photos which are received by Sol and Earth date are different").
                hasSameElementsAs(photoMetaOnEarthDate);
        softly.assertAll();
    }

    @Test(description = "TestCase-2 Any camera made 10 times less images than any other", dataProvider = GENERIC_DP)
    @TestData("testCanCheckAnyCameraMadeLessThanTenImagesThanAnyOther.json")
    public void testCanCheckAnyCameraMadeLessThanTenImagesThanAnyOther(final JsonPath testData) {
        List<String> cameraNames = testData.getList("cameras");

        Set<Integer> amountOfPhotoByEachCamera = cameraNames.stream().map(camera -> RoverPhotoService.
                getRoverPhotoMetadata(testData.get("unitTime"), testData.get("time"), "camera", camera).getPhotos().size()).collect(Collectors.toSet());

        assertThat(Collections.max(amountOfPhotoByEachCamera) - Collections.min(amountOfPhotoByEachCamera)).
                as("Camera made 10 times more images than any other").isLessThan(10);
    }

    private List<String> getMd5Hexes(List<RoverPhotos.Photo> photoHashOnSol) {
        return photoHashOnSol.stream().
                map(photoMeta -> DigestUtils.md5Hex(RoverPhotoService.getRoverPhoto(photoMeta.getImgSrc()).getBody().asByteArray())).
                collect(Collectors.toList());
    }
}
