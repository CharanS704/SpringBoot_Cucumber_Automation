package com.ecommerce.stepdefinitions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

import com.ecommerce.common.ProjectUtil;
import com.ecommerce.dao.StoreRepository;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class OrderItemsStepDef extends ProjectUtil {

	Boolean itemStatus;
	Integer actualItemQty;
	List<String> expectedItemsList;
	List<Object> dbItemNameList;

	@Autowired
	public StoreRepository storeRepositrory;

	@Given("User establish connectivity with the database and prepares query")
	public void estabilish_database_connection() {
		storeRepositrory.setSELECT_QUERY_FOR_COUNT(
				"select itemQuantity from ItemDetails where itemName =? and isItemBooked =?");

	}

	@Then("User fetch records for the item {string} which are left with booked status as {string}")
	public void fetch_item_qty(String item, String status) {
		if (status.equalsIgnoreCase("Yes")) {
			itemStatus = true;
		} else {
			itemStatus = false;
		}

		actualItemQty = storeRepositrory.totalRecordsCountByNameAndStatus(item, itemStatus);
		System.out.println("Current stock for the item: " + actualItemQty);

	}

	@And("User validate the quantity for the records against the expected stock quantity {int}")
	public void validate_stock_units(Integer expectedQty) {

		Assert.assertEquals(actualItemQty, expectedQty, "Item stock is not as per expected quantity!!");

	}

	@Given("User establish connectivity with the database and prepares query to fetch all the records from database")
	public void prepareQueryToFetchReordsByStatus() {

	}

	@Then("User fetch the records from the database with status as {string}")
	public void fetchRecordsByStatus(String status) {
		if (status.equalsIgnoreCase("Yes")) {
			itemStatus = true;
		} else {
			itemStatus = false;
		}

		dbItemNameList = storeRepositrory.getRecordsByStatus(itemStatus).stream()
				.map(mapList -> mapList.get("itemName")).sorted().collect(Collectors.toList());
		System.out.println("DB List: " + dbItemNameList);
	}

	@And("User validate the items in list {string} are present in the database with booked status {string}")
	public void validateUniqueItems(String items, String status) {
		expectedItemsList = new ArrayList<String>(Arrays.asList(items.split(","))).stream().sorted()
				.collect(Collectors.toList());
		System.out.println("expected item list: "+expectedItemsList);
		
		Assert.assertTrue(expectedItemsList.equals(dbItemNameList),"The items in the DB are not as expected!");
	}

}
