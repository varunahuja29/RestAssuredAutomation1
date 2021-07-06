package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
//import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

//Given: Pre conditions
//When: Performing action such as get/post/put request
//Then: Validations such as validating status code, Content-Type etc

public class TestClass2Static {
//	@Test
	// A simple get request using the static methods and properties from Request class
	public void getRequest1() {
		Response r1 = get("https://reqres.in/api/users?page={pageNumber}", 2);
		System.out.println(r1.getStatusCode());
		System.out.println(r1.getStatusLine());
		System.out.println(r1.getBody().asString());
		
		Assert.assertEquals(r1.getStatusCode(), 200);
	}
	
//	@Test
	// A simple get request using the static methods and properties from Request class using Gherkin
	public void getRequest2() {
		baseURI = "https://reqres.in/api";
		given().
			get("/users?page=2").
		then().
			statusCode(200).
			body("data[0].id", equalTo(7)).
			body("data.first_name", hasItems("Michael", "Lindsay"));
	}
	
//	@Test
	// How to create Json object payload using Java map 
	public void postRequest1() {
		Map requestMap = new HashMap();
		requestMap.put("name", "Mike");
		requestMap.put("age", "31");
		
		System.out.println(requestMap);	// This does not gives output in proper JSON format, conver it using JSON library (JSONObject class).
		
		JSONObject requestMapJson = new JSONObject(requestMap);	// Pass created map as constructor argument
		System.out.println(requestMapJson);
		System.out.println(requestMapJson.toString());	// Convert the Json object to a string, will give the same output as above, but will not be a json object rather be a string.
	}
	
//	@Test
	// How to create Json object payload directly using JSON library 
	public void postRequest2() {
		JSONObject requestMapJson = new JSONObject();	// Use no arguments constructor
		requestMapJson.put("name", "Mike");
		requestMapJson.put("age", "31");
		
		System.out.println(requestMapJson);
		System.out.println(requestMapJson.toString());
	}

//	@Test
	// Post the content using api with Gherkin
	public void postRequest3() {
		JSONObject requestMapJson = new JSONObject();	// Use no arguments constructor
		requestMapJson.put("name", "Mike");
		requestMapJson.put("age", "31");
		
		System.out.println(requestMapJson);
		
		baseURI = "https://reqres.in/api";
		given().
			body(requestMapJson.toString()).
		when().
			post("/users").
		then().
			statusCode(201).	// Status code returned for successful creation.
			log().all();
	}
	
//	@Test
	//Post the content using api with headers using Gherkin
	public void postRequest4() {
		JSONObject requestMapJson = new JSONObject();	// Use no arguments constructor
		requestMapJson.put("name", "Mike");
		requestMapJson.put("age", "31");
		
		System.out.println(requestMapJson);
		
		baseURI = "https://reqres.in/api";
		given().
//			header("Content-Type", "application/json").	// Way 1 to define that request type is JSON
			contentType(ContentType.JSON).				// Way 2 to define that request type is JSON
			accept(ContentType.JSON).					// This says that the api accepts the response as JSON
			body(requestMapJson.toString()).
		when().
			post("/users").
		then().
			statusCode(201).	// Status code returned for successful creation.
			log().all();
	}
	
//	@Test
	// Post the content using api and get the response object back.
	public void postRequest5() {
		JSONObject requestMapJson = new JSONObject();	// Use no arguments constructor
		requestMapJson.put("name", "Mike");
		requestMapJson.put("age", "31");
		System.out.println(requestMapJson);
		
		baseURI = "https://reqres.in/api";
		Response r5 = given().
			contentType(ContentType.JSON).				// Way 2 to define that request type is JSON
			accept(ContentType.JSON).					// This says that the api accepts the response as JSON
			body(requestMapJson.toString()).
		when().
			post("/users").
		then().					// Validates response in then 
			statusCode(201).	// Status code returned for successful creation.
			contentType(ContentType.JSON).			// Checking that the response type received is JSON
			extract().response();

		System.out.println("Full response is: " + r5.asString());
		System.out.println("Response body is: " + r5.getBody().asString());
		System.out.println("Age of the user added is: " + r5.jsonPath().getString("age"));
	}
	
	
	@SuppressWarnings("unchecked")
//	@Test
	public void putAndPatchRequest1() {
		JSONObject reqJson1 = new JSONObject();
		reqJson1.put("name", "John");
		reqJson1.put("job", "SE");
		System.out.println(reqJson1);
		
		baseURI = "https://reqres.in";
		Response r1 = given().
			contentType(ContentType.JSON).				// Way 2 to define that request type is JSON
			accept(ContentType.JSON).					// This says that the api accepts the response as JSON
			body(reqJson1.toString()).
		when().
//			put("/api/users/2").
			patch("/api/users/2").						// Other syntax remains the same for put & patch
		then().
			statusCode(200).
			extract().response();
		
		System.out.println("Response as pretty string: " + r1.asPrettyString());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void deleteRequest1() {
		baseURI = "https://reqres.in";

		Response r1 = when().
			delete("/api/users/2").						// Other syntax remains the same for put & patch
		then().
			statusCode(204).
			extract().response();
		
		System.out.println("Response as pretty string: " + r1.asPrettyString());
	}
	
}
