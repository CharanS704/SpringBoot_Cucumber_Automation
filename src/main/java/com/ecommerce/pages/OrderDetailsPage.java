package com.ecommerce.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Component;


@Component
public class OrderDetailsPage {

	public By cartIcon = By.xpath("//button[@class='btn btn-custom']//i[@class='fa fa-shopping-cart']");
	//WebElement cartIcon;

	public By cartProducts = By.xpath("//ul[@class='cartWrap ng-star-inserted']");
	//List<WebElement> cartProducts;



	public List<String> getSortedProductsInCart(List<WebElement> cartProductsList) {

		return (cartProductsList.stream().map(cartProduct -> cartProduct.findElement(By.tagName("h3")).getText()).sorted()
				.collect(Collectors.toList()));
	}


}
