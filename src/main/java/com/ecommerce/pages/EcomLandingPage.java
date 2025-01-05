package com.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;


@Component
public class EcomLandingPage {

	public By userEmail = By.id("userEmail");
	// WebElement userEmail;

	public By password = By.id("userPassword");
	//WebElement password;

	public By submit = By.id("login");
	//WebElement submit;

	public By toastElement = By.xpath("//div[@class='toast-bottom-right toast-container']");
	//WebElement toastElement;


	public String fetchMessage(WebElement element) {

		return element.getText();
	}

}
