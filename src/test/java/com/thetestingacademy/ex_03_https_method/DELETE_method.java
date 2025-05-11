package com.thetestingacademy.ex_03_https_method;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class DELETE_method {
    RequestSpecification r;
    Response response;
    ValidatableResponse vr;

    @Test
    public void test_delete_non_bdd() {


        String token = "df2b1159af9ee90";
        String bookingid = "365";


        r = RestAssured.given();
        r.baseUri("https://restful-booker.herokuapp.com/");
        r.basePath("/booking/" + bookingid);
        r.contentType(ContentType.JSON);
        r.cookie("token", token);


        response = r.when().log().all().delete();

        vr = response.then().log().all();
        vr.statusCode(201);


    }
}
