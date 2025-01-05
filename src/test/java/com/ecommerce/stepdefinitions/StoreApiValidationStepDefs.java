package com.ecommerce.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.ecommerce.common.ProjectUtil;

public class StoreApiValidationStepDefs extends ProjectUtil{

	RequestSpecification requestSpecification;
	Response response;
	String baseUri = "https://rahulshettyacademy.com";
	static String placeId = "";
	static String responseStatusMessage = "";
	static int actualStatusCode = 0;

	@Given("User collects base connection details and prepares request")
	public void prepareRequestSpecification() {
		requestSpecification = given().log().all().baseUri(baseUri).queryParam("key", "qaclick123");
	}

	@Given("Request payload is available and the shoot a POST request is sent to API")
	public void request_payload_is_available_and_the_request_is_sent_to_api() {

		Map<String, Object> mainJson = new HashMap<String, Object>();
		Map<String, Object> nestedJson = new HashMap<String, Object>();

		nestedJson.put("lat", -38.383494);
		nestedJson.put("lng", 33.427362);

		mainJson.put("location", nestedJson);
		mainJson.put("accuracy", 50);
		mainJson.put("name", "First Main Rd ST");
		mainJson.put("phone_number", "(+91)9876543210");
		mainJson.put("address", "29,1st cross road, near bus stand");
		mainJson.put("types", List.of("shoe park", "shop"));

		mainJson.put("website", "http://google.com");
		mainJson.put("language", "English");

		System.out.println(mainJson);
		response = given().spec(requestSpecification).body(mainJson).when().post("/maps/api/place/add/json").then()
				.log().all().extract().response();

		placeId = JsonPath.from(response.asString()).getString("place_id");
		actualStatusCode = response.getStatusCode();
		responseStatusMessage = response.path("status");

		System.out.println("Response for POST request: " + response.asString());

	}

	@Given("User prepare the url along with place Id {string} and shoot a GET request")
	public void prepare_get_request(String placeId) {

		response = given().spec(requestSpecification).queryParam("place_id", placeId).when()
				.get("/maps/api/place/get/json").then().log().ifError().extract().response();
		actualStatusCode = response.getStatusCode();
		System.out.println("Response for GET request: " + response.asString());
	}

	@Given("User prepare a request payload to update record with place Id {string} with updated address {string} and shoot a PUT request")
	public void update_existing_data(String placeId, String updatedAddress) {
		HashMap<String, Object> putJson = new HashMap<String, Object>();
		putJson.put("place_id", placeId);
		putJson.put("address", updatedAddress);
		putJson.put("key", "qaclick123");

		HashMap<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("place_id", placeId);
		queryParams.put("key", "qaclick123");

		response = given().spec(requestSpecification).queryParam("place_id", placeId).body(putJson).when()
				.put("maps/api/place/update/json").then().log().ifError().extract().response();

		responseStatusMessage = JsonPath.from(response.asString()).get("msg");
		actualStatusCode = response.getStatusCode();

		System.out.println("Response for PUT request: " + response.asString());

	}

	@Given("User prepare a request payload to delete record with place Id {string} and shoot a DELETE request")
	public void delete_place_id(String placeId) {
		HashMap<String, Object> deleteJson = new HashMap<String, Object>();
		deleteJson.put("place_id", placeId);
		response = given().spec(requestSpecification).body(deleteJson).when()
				.delete("/maps/api/place/delete/json").then().log().ifError().extract().response();
		actualStatusCode = response.getStatusCode();
		responseStatusMessage = JsonPath.from(response.asString()).getString("status");

	}

	@Then("Validate the response status code is {int}")
	public void validate_the_response_status_code_is(Integer statusCode) {
		Assert.assertEquals(actualStatusCode, statusCode, "Expected status code to be 200, but got something else!");

	}

	@And("Validate response message indicates the data is successfully saved")
	public void validate_response_message_indicates_the_data_is_successfully_saved() {
		Assert.assertEquals(responseStatusMessage, "OK",
				"Response message was expected to be 'OK', instead got something else!");

	}

	@And("Validate response message indicates the data is successfully updated")
	public void validate_response_message_indicates_the_data_is_successfully_updated() {
		Assert.assertEquals(responseStatusMessage, "Address successfully updated",
				"The response message is not as expected post data updation!");
	}

	@And("Validate response message indicates the data is successfully deleted")
	public void validate_response_message_indicates_the_data_is_successfully_deleted() {
		Assert.assertEquals(responseStatusMessage, "OK",
				"Response message was expected to be 'OK', instead got something else!");
	}

}
