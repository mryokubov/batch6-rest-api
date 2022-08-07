package com.academy.techcenture.utils;

import com.academy.techcenture.contants.Constants;
import com.academy.techcenture.pojos.ApiClientRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class APIUtils {

    public static String generateToken(){
        ApiClientRequest clientRequest = new ApiClientRequest("Harry Peterson", "harry.peterson@abc.com");
        Response post = RestAssured.given().contentType(ContentType.JSON).body(clientRequest).post(Constants.BASE_URL + Constants.TOKEN);
        return post.jsonPath().get("accessToken");
    }

    public static void main(String[] args) {

        System.out.println(generateToken());

    }
}
