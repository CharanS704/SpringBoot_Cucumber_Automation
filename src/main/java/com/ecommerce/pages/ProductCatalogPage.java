package com.ecommerce.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;

@Component
public class ProductCatalogPage {

	public By products = By.cssSelector(".container .row div.col-lg-4");
	// List<WebElement> products;

	public By toastElement = By.id("toast-container");
	// WebElement toastElement;

	public By spinnerElement = By.cssSelector(".ng-animating");
	// WebElement spinnerElement;

	public By cartButton = By.xpath("//button[@class='btn btn-custom']/i[@class='fa fa-shopping-cart']");
	// WebElement cartButton;

	public WebElement getProductWebElementByName(String name, List<WebElement> actualProductList) {
		return actualProductList.stream().filter(p -> p.findElement(By.tagName("b")).getText().equals(name)).findFirst()
				.orElse(null);
	}

	public void addProductToCart(WebElement product) {

		product.findElement(By.cssSelector(".container .row div.col-lg-4 button:last-of-type")).click();

	}

	public String getToastMessage(WebElement toastElement) {
		return toastElement.getText();

	}

}
