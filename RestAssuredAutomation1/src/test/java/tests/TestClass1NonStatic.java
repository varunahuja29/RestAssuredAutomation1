package tests;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TestClass1NonStatic {
//	@Test
	public void getRequest1NonStatic() {
		Response r1 = RestAssured.get("https://reqres.in/api/users?page=2");
		System.out.println(r1.getStatusCode());
		System.out.println(r1.getBody().asString());
	}
	

	
}
