package com.thetestingacademy;

import io.restassured.RestAssured;

public class APITesting_Lab001_RA {
    public static void main(String[] args) {

        String pincode = "110048";
        RestAssured
                .given().baseUri("https://api.zippopotam.us")
                .basePath("/IN/" + pincode)
                .when()
                .get()
                .then().log().all()
                .statusCode(200);

    }
}
