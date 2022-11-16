package com.casumo;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    public final int STATUS_CODE_200 = 200;
    public final int STATUS_CODE_201 = 201;
    public final int STATUS_CODE_400 = 400;
    public final int STATUS_CODE_404 = 404;
    public final int STATUS_CODE_500 = 500;

    @BeforeAll
    static void configureBeforeAll() {
        RestAssured.baseURI = "http://localhost:8080/";
    }

}
