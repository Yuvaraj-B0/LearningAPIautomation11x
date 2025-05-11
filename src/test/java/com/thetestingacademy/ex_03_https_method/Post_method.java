package com.thetestingacademy.ex_03_https_method;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class Post_method {

    RequestSpecification r; //Given
    Response response; //When
    ValidatableResponse vr; //Then
    String payload = "{\n" +
            "    \"username\" : \"admin\",\n" +
            "    \"password\" : \"password123\"\n" +
            "}";
    @Test
    public void test_POST_Auth_BDD(){
                RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/auth")
                .contentType(ContentType.JSON)
                .log().all()
                .body(payload)
                .when()
                .log().all().post()
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void test_POST_Auth_NON_BDD(){
        r = RestAssured.given();
        r.given().baseUri("https://restful-booker.herokuapp.com")
                .basePath("/auth")
                .contentType(ContentType.JSON)
                .body(payload).log().all();
        response = r.when().log().all().post();
        vr = response.then().log().all();
        vr.statusCode(200);


    }
}
