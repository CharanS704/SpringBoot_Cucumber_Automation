package com.ecommerce.pages;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;



@Component
public class CheckoutPage {

	public By countryInputBox = By
			.xpath("//input[@class='input txt text-validated' and @placeholder='Select Country']");
	// WebElement countryInputBox;

	public By countryList = By.xpath("//section[@class='ta-results list-group ng-star-inserted']/button");
	// List<WebElement> countryList;

	public By toastElement = By.id("toast-container");
	// WebElement toastElement;

	public By totalAmount = By.xpath("//span[contains(text(),'Total')]");
	// WebElement totalAmount;

	public By placeOrderButton = By.xpath("//a[contains(text(),'Place Order')]");
	// WebElement placeOrderButton;

	public By orderConfirmationDetails = By
			.xpath("//td[@class='box']//h1[@class='hero-primary']/parent::td/parent::tr/parent::tbody/tr");
	// List<WebElement> orderConfirmationDetails;

	public By orderConfirmationIds = By.xpath("//tr[@class='ng-star-inserted']/td/label");
	// List<WebElement> orderConfirmationIds;

	public By checkoutButton = By.xpath("//button[@class='btn btn-primary' and contains(text(),'Checkout')]");
	// WebElement checkoutButton;

	public void enterCountry(String countryName, WebElement countryBox) {

		countryBox.click();
		countryBox.sendKeys(countryName);

	}

	public void selectCountryFromList(String countryName, List<WebElement> countryList) {
		Optional<WebElement> countryElement = countryList.stream()
				.filter(country -> country.getText().equalsIgnoreCase(countryName)).findFirst();
		if (countryElement.isPresent())
			countryElement.get().click();
		else
			System.out.println(
					"The provided country: '" + countryList + "' is not available in the system to be selected!");
	}

	public void placeOrder(WebElement placeOrderButton) {
		placeOrderButton.click();
	}

	public String fetchToastMessage(WebElement toastElement) {
		return toastElement.getText();
	}

	public List<String> fetchOrderDetailsFromConfirmationPage(List<WebElement> orderConfirmationDetails,
			List<WebElement> orderConfirmationIds) {

		String OrderStatusMessage = orderConfirmationDetails.get(0).getText();

		List<String> orderDetails = orderConfirmationIds.stream().map(id -> id.getText()).collect(Collectors.toList());
		orderDetails.add(0, OrderStatusMessage);
		return orderDetails;

	}

}
