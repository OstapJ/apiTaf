package com.ringlabskiev.core;


import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.json.config.JsonPathConfig;
import com.ringlabskiev.core.annotations.TestData;
import com.ringlabskiev.core.exceptions.TestDataException;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;


public abstract class BaseTest {
    protected static final String GENERIC_DP = "genericDP";
    protected SoftAssertions softly = new SoftAssertions();


    @DataProvider(name = GENERIC_DP)
    public Object[][] loadFromExamplesTable(Method method) {
        String examplesTableFile = "/" + method.getAnnotation(TestData.class).value();
        JsonPath testData = loadJsonFile(examplesTableFile);
        return new Object[][]{{testData}};
    }

    private JsonPath loadJsonFile(String path) {
        try {
            URL url = this.getClass().getResource(path);
            return new JsonPath(new File(url.toURI())).using(new JsonPathConfig("UTF-8"));
        } catch (final URISyntaxException e) {
            throw new TestDataException("Json file '%s' not found", path, e);
        }
    }

}
