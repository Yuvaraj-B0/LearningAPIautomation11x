package com.thetestingacademy.ex_03_https_method;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class Get_method {

    @Test
    public void test_GET_POSITIVE_BDD() {
        String pincode = "110048";
        RestAssured
                .given()
                .baseUri("https://api.zippopotam.us")
                .basePath("/IN/" + pincode)
                .when()
                .log()
                .all()
                .get()
                .then()
                .log()
                .all()
                .statusCode(200);

    }
    RequestSpecification r; //Given
    Response response; //When
    ValidatableResponse vr; //Then

    @Test
    public void test_GET_POSITIVE_NonBDD(){
        String pincode =  "560066";
        //Given
        r = RestAssured.given();
        r.baseUri("https://api.zippopotam.us");
        r.basePath("/IN/" + pincode);

        //When
        response = r.when().log().all().get();

        //Then
        vr = response.then().log().all();
        vr.statusCode(200);
    }
}
