package com.thetestingacademy.RA_task;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

public class APITesting01_RA {
    RequestSpecification rs;
    Response res;
    ValidatableResponse vr;
    String token;
    int bookingid;

    @Owner("Yuvaraj")
    @Severity(SeverityLevel.CRITICAL)
    @Description("TC#1 - Verify that the Create token")
    @Test(priority = 1 )
    public  void test_Post_token(){
        String token_payload = "{\n" +
                "    \"username\" : \"admin\",\n" +
                "    \"password\" : \"password123\"\n" +
                "}";
        rs = RestAssured.given();
        rs.baseUri("https://restful-booker.herokuapp.com");
        rs.basePath("/auth");
        rs.contentType(ContentType.JSON);
        rs.body(token_payload).log().all();

        res = rs.when().log().all().post();

        vr = res.then().log().all();
        vr.statusCode(200);
        token= res.jsonPath().getString("token");
        String responseBody = res.getBody().asPrettyString();

// Add to Allure report
        Allure.addAttachment("API Response", "application/json", responseBody, ".json");
     //   System.out.println(token);

    }
    @Severity(SeverityLevel.NORMAL)
    @Description("TC#2 - Verify that the Create Booking")
    @Test(priority = 2)
    public void test_post_create_booking(){
        String create_booking_payload = "{\n" +
                "    \"firstname\" : \"Jim\",\n" +
                "    \"lastname\" : \"Brown\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        rs = RestAssured.given();
        rs.baseUri("https://restful-booker.herokuapp.com");
        rs.basePath("/booking");
        rs.contentType(ContentType.JSON);
        rs.body(create_booking_payload).log().all();

        res = rs.when().log().all().post();

        vr = res.then().log().all();
        vr.statusCode(200);
        String responseBody = res.getBody().asPrettyString();

// Add to Allure report
        Allure.addAttachment("API Response", "application/json", responseBody, ".json");
        bookingid= Integer.parseInt(res.jsonPath().getString("bookingid"));
       // System.out.println(bookingid);
        Assert.assertNotNull(bookingid);
        vr.body("booking.firstname", Matchers.equalTo("Jim"));
        vr.body("booking.lastname", Matchers.equalTo("Brown"));
        vr.body("booking.depositpaid", Matchers.equalTo(true));
       // vr.body("bookingid", Matchers.notNullValue());

    }
    @Severity(SeverityLevel.NORMAL)
    @Description("TC#3 - Verify Get Booking with bookingid")
    @Test(priority = 3)
    public void test_get_booking(){
        System.out.println(bookingid);
        rs = RestAssured.given();
        rs.baseUri("https://restful-booker.herokuapp.com");
        rs.basePath("/booking/"+bookingid);
        rs.contentType(ContentType.JSON);
        res = rs.when().log().all().get();
        vr = res.then().log().all();
        vr.statusCode(200);
        String responseBody = res.getBody().asPrettyString();

// Add to Allure report
        Allure.addAttachment("API Response", "application/json", responseBody, ".json");
        vr.body("firstname", Matchers.equalTo("Jim"));
        vr.body("lastname", Matchers.equalTo("Brown"));
        vr.body("depositpaid", Matchers.equalTo(true));
       // vr.body("bookingid", Matchers.notNullValue());
    }
    @Severity(SeverityLevel.NORMAL)
    @Description("TC#4 - Verify Update booking")
    @Test(priority = 4)
    public void test_update_booking_put() {
               String payloadPUT = "{\n" +
                "    \"firstname\" : \"yuvaraj\",\n" +
                "    \"lastname\" : \"Kumar\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : false,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2024-01-01\",\n" +
                "        \"checkout\" : \"2024-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Lunch\"\n" +
                "}";


        rs = RestAssured.given();
        rs.baseUri("https://restful-booker.herokuapp.com");
        rs.basePath("/booking/" + bookingid);
        rs.contentType(ContentType.JSON);
        rs.cookie("token", token);
        rs.body(payloadPUT).log().all();
        res = rs.when().log().all().put();
        vr = res.then().log().all();
        vr.statusCode(200);
        String responseBody = res.getBody().asPrettyString();

// Add to Allure report
        Allure.addAttachment("API Response", "application/json", responseBody, ".json");

    }
    @Severity(SeverityLevel.NORMAL)
    @Description("TC#1 - Verify Partial update")
    @Test(priority = 5)
    public void test_partial_updated_patch() {
                String payloadPatch = "{\n" +
                "    \"firstname\" : \"yuvaraj\",\n" +
                "    \"lastname\" : \"nivas\"\n" +
                "}";

        rs = RestAssured.given();
        rs.baseUri("https://restful-booker.herokuapp.com/");
        rs.basePath("/booking/" + bookingid);
        rs.contentType(ContentType.JSON);
        rs.cookie("token", token);
        rs.body(payloadPatch).log().all();

        res = rs.when().log().all().patch();

        vr = res.then().log().all();
        vr.statusCode(200);
        String responseBody = res.getBody().asPrettyString();

// Add to Allure report
        Allure.addAttachment("API Response", "application/json", responseBody, ".json");


    }

    @Severity(SeverityLevel.NORMAL)
    @Description("TC#1 - Verify delete booking")
    @Test(priority = 6)
    public void test_delete() {
        rs = RestAssured.given();
        rs.baseUri("https://restful-booker.herokuapp.com/");
        rs.basePath("/booking/" + bookingid);
        rs.contentType(ContentType.JSON);
        rs.cookie("token", token);


        res = rs.when().log().all().delete();

        vr = res.then().log().all();
        vr.statusCode(201);
        String responseBody = res.getBody().asPrettyString();

            // Add to Allure report
        Allure.addAttachment("API Response", "application/json", responseBody, ".json");


    }
}
