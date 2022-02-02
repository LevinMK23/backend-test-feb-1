package com.geekbrains.test.spoonacular;

import java.io.IOException;

import com.geekbrains.test.AbstractTest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class SpoonaccularTest extends AbstractTest {

    private static String API_KEY = "1cbcde369d1243cfb7f586b8c5b84fa2";

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://api.spoonacular.com";
    }

    @Test
    void testGetRecipesComplexSearch() throws IOException, JSONException {

        String actual = given()
                .param("apiKey", API_KEY)
                .param("query", "pasta")
                .param("number", 3)
                .log()
                .parameters()
                .expect()
                .statusCode(200)
                .time(Matchers.lessThan(3000L))
                .body("offset", is(0))
                .body("number", is(3))
                .log()
                .body()
                .when()
                .get("recipes/complexSearch")
                .body()
                .asPrettyString();

        String expected = getResourceAsString("expected.json");

        JSONAssert.assertEquals(
                expected,
                actual,
                JSONCompareMode.NON_EXTENSIBLE
        );

    }

}
