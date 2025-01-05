package com.ecommerece.commonworkflow;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.common.ProjectUtil;
import com.ecommerce.pages.CheckoutPage;
import com.ecommerce.pages.EcomLandingPage;
import com.ecommerce.pages.OrderDetailsPage;
import com.ecommerce.pages.ProductCatalogPage;

public class CommonWorkFlow extends ProjectUtil {

	@Autowired
	public EcomLandingPage ecomLandingPage;

	@Autowired
	public ProductCatalogPage productCatalogPage;

	@Autowired
	public OrderDetailsPage orderDetailsPage;

	@Autowired
	public CheckoutPage checkOutPage;

	WebElement userEmail;
	WebElement password;
	WebElement submit;

	public void loginToApp(String userEmailId, String userActualPassword) {

		userEmail = getWebElement(ecomLandingPage.userEmail);
		password = getWebElement(ecomLandingPage.password);
		submit = getWebElement(ecomLandingPage.submit);
		
		waitForElementToAppear(userEmail);
		userEmail.sendKeys(userEmailId);

		waitForElementToAppear(password);
		password.sendKeys(userActualPassword);

		waitForElementToBeClickable(submit);
		submit.click();

	}

}
