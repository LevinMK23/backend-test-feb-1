package com.geekbrains.test.spoonacular;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.stream.Stream;

import com.geekbrains.test.AbstractTest;
import com.geekbrains.test.ImageClassifierResponse;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import net.javacrumbs.jsonunit.JsonAssert;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;
import static org.hamcrest.Matchers.is;

public class SpoonaccularTest extends AbstractTest {

    private static final String API_KEY = "1cbcde369d1243cfb7f586b8c5b84fa2";
    private static RequestSpecification BASE_SPEC;
    private static ResponseSpecification RESPONSE_SPEC;

    @BeforeAll
    static void beforeAll() {

        RestAssured.baseURI = "https://api.spoonacular.com";

        BASE_SPEC = new RequestSpecBuilder()
                .addQueryParam("apiKey", API_KEY)
                .log(LogDetail.ALL)
                .build();

        RESPONSE_SPEC = new ResponseSpecBuilder()
                .log(LogDetail.BODY)
                .log(LogDetail.ALL)
                .build();
    }

    private static Stream<Arguments> testImageClassificationData() {
        return Stream.of(
                Arguments.of("burger", "burger.jpg"),
                Arguments.of("pasta", "pasta.jpg"),
                Arguments.of("pizza", "pizza.jpg")
        );
    }

    @Test
    @Disabled
    void testGetRecipesComplexSearch() throws IOException, JSONException {

        String actual = given()
                .spec(BASE_SPEC)
                .param("query", "pasta")
                .param("number", 3)
                .expect()
                .statusCode(200)
                .time(Matchers.lessThan(3000L))
                .body("offset", is(0))
                .body("number", is(3))
                .spec(RESPONSE_SPEC)
                .when()
                .get("recipes/complexSearch")
                .body()
                .asPrettyString();

        String expected = getResourceAsString("expected.json");

        JsonAssert.assertJsonEquals(
                expected,
                actual,
                JsonAssert.when(IGNORING_ARRAY_ORDER)
        );

    }

    @Disabled
    @ParameterizedTest
    @MethodSource("testImageClassificationData")
    void testImageClassification(String dir, String resource) throws IOException {

        String separator = FileSystems.getDefault().getSeparator();
        File file = getFile("images" + separator + dir + separator + resource);

        ImageClassifierResponse response = given()
                .spec(BASE_SPEC)
                .multiPart("file", file)
                .expect()
                .body("status", is("success"))
                .body("category", is(dir))
                .body("probability", Matchers.greaterThan(0.9f))
                .spec(RESPONSE_SPEC)
                .when()
                .post("food/images/classify")
                .as(ImageClassifierResponse.class);

        ImageClassifierResponse expected = ImageClassifierResponse.builder()
                .status("success")
                .category(dir)
                .build();

        Assertions.assertEquals(expected.getStatus(), response.getStatus());

    }

}
