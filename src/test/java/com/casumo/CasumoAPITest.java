package com.casumo;


import static io.restassured.path.xml.XmlPath.CompatibilityMode.HTML;
import static io.restassured.RestAssured.given;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class CasumoAPITest extends TestBase {


    @CsvSource(value = {
//            "abcdefghijklmnopqrstuvwxyz         | bcdfghjklmnpqrstvwxz",
            "!abc!                              | !bc!",
            "#abc                               | #bc",
            "@abc                               | @bc",
            "^&*()_+=-*<>,.{}[];'\"abc~`        | ^&*()_+=-*<>,.{}[];'\"bc~`",
            "%abc                               | %bc",
            "%20abc                             | %20bc",
            "^abc^                              | ^bc^",
            "&abc*                              | &bc*",
            "(abc)                              | (bc)",
            "_a+b=c                             | _+b=c",
            "-a*b<c>                            | -*b<c>",
            "ab\\c                              | b\\c",
            "[abc]                              | [bc]",
            "{abc}                              | {bc}",
            ";'abc;'                            | ;'bc;'",
            ":ab:c                              | :b:c",
            "ab\":c                             | b\"c",
            "aа                                 | а",
            "<script>alert<script>              | <scrpt>lrt<scrpt>",
            "sb?da                              | sb?d",
            "0123456789abc0123456789            | 0123456789bc0123456789",
            "abcabc/>                           | bcbc"
    }, delimiter = '|')
    @Owner("Example owner")
    @Story("Check symbols delition")
    @DisplayName("To get response without the vowels test")
    @ParameterizedTest(name="Test for request {0} and expected response {1}")
    void getTextWithoutTheVowelsTest(String req, String resp) {

        Response response = given()
                .log().uri()
                .log().body()
                .contentType(ContentType.HTML)
                .when()
                .get("/"+req)
                .then()
                .log().status()
                .log().body()
                .statusCode(STATUS_CODE_200)
                .extract().response();

        XmlPath htmlPath = new XmlPath(HTML, response.getBody().asString());
        Assertions.assertEquals(resp, htmlPath.getString("body"));

    }


    @CsvSource(value = {
            "<script>alert</script>             | nullErrorNot Found",
            "/abcabc>                           | nullErrorNot Found"


    }, delimiter = '|')
    @Owner("Example owner")
    @Story("Check symbols delition")
    @DisplayName("To get error response")
    @ParameterizedTest(name="Test for request {0} and expected response {1}")
    void getTextWithoutTheVowelsNegativeTest(String req, String resp) {

        Response response = given()
                .log().uri()
                .log().body()
                .contentType(ContentType.HTML)
                .when()
                .get("/"+req)
                .then()
                .log().status()
                .log().body()
                .statusCode(STATUS_CODE_404)
                .extract().response();

        XmlPath htmlPath = new XmlPath(HTML, response.getBody().asString());
        Assertions.assertEquals(resp, htmlPath.getString("body"));

    }
}
