package com.ecommerce.stepdefinitions;

import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.Assert;
import com.ecommerce.pages.CheckoutPage;
import com.ecommerce.pages.EcomLandingPage;
import com.ecommerce.pages.OrderDetailsPage;
import com.ecommerce.pages.ProductCatalogPage;
import com.ecommerece.commonworkflow.CommonWorkFlow;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class ErrorMessageStepDef extends CommonWorkFlow {

	@Autowired
	public EcomLandingPage ecomLandingPage;

	@Value("${application.url}")
	String applicationUrl;

	@Value("${error.username}")
	String errorUsername;

	@Value("${ecommerce.password}")
	String ecommercePassword;

	@Given("User launched desired browser and navigate to ecommerce login page and prepares the incorrect credentials")
	public void setUp() {
		createDriver();
		getExplicitWait();
		createJavaScriptExecutor();
		getUrl(applicationUrl);
	}

	@Given("User is on ecommerce login page and login with incorrect username and password")
	public void user_is_on_ecommerce_landing_page_and_login_with_incorrect_username_and_password() {

		System.out.println("Incorrect username entered is:" + ecommercePassword);
		String expectedMessage = "Incorrect email or password.";

		loginToApp(errorUsername, ecommercePassword);
		waitForElementToAppear(getWebElement(ecomLandingPage.toastElement));

		Assert.assertEquals(ecomLandingPage.fetchMessage(getWebElement(ecomLandingPage.toastElement)), expectedMessage,
				"Expected message is incorrect after entering incorrect username and password!");

	}

	@Then("User validate error message on login page")
	public void user_validate_error_message_on_login_page() {
		String expectedErrorMessage = "Incorrect email or password.";

		String actualErrorMMessage = ecomLandingPage.fetchMessage(getWebElement(ecomLandingPage.toastElement));
		Assert.assertTrue(expectedErrorMessage.equals(actualErrorMMessage), "Error message is displayed incorrect!!");
		
		tearDown();
	}

}
