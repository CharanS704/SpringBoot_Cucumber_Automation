package com.ecommerce.stepdefinitions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.Assert;

import com.ecommerce.common.ProjectUtil;
import com.ecommerce.pages.CheckoutPage;
import com.ecommerce.pages.EcomLandingPage;
import com.ecommerce.pages.OrderDetailsPage;
import com.ecommerce.pages.ProductCatalogPage;
import com.ecommerece.commonworkflow.CommonWorkFlow;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.*;

public class OrderValidationStepDef extends CommonWorkFlow {

	@Autowired
	public EcomLandingPage ecomLandingPage;

	@Autowired
	public ProductCatalogPage productCatalogPage;

	@Autowired
	public OrderDetailsPage orderDetailsPage;

	@Autowired
	public CheckoutPage checkOutPage;
	
	@Value("${application.url}")
	String applicationUrl;

	@Value("${ecommerce.username}")
	String ecommerceUsername;

	@Value("${ecommerce.password}")
	String ecommercePassword;
	
	WebElement cartIcon;
	WebElement checkOutButton;
	WebElement toastIcon;

	ArrayList<String> expectedProductsList;
	
	@Given("User launched desired browser and navigate to ecommerce login page url")
	public void setUp() {
		createDriver();
		getExplicitWait();
		createJavaScriptExecutor();
		getUrl(applicationUrl);
	}

	@Given("User is on ecommerce login page and login with correct username and password")
	public void user_is_on_ecommerce_landing_page_and_login_with_username_and_password() {


		String expectedMessage = "Login Successfully";
		System.out.println("Provided username: "+ecommerceUsername);
		loginToApp(ecommerceUsername, ecommercePassword);
		
		this.toastIcon = getWebElement(ecomLandingPage.toastElement);
		waitForElementToAppear(this.toastIcon);

		Assert.assertEquals(ecomLandingPage.fetchMessage(this.toastIcon), expectedMessage,
				"Expected message is incorrect after entering correct username and password!");
		System.out.println("Login successfull");

	}

	@Then("User selects orders {string} from product catalog page and navigate to orders page")
	public void user_selects_multiple_orders_from_product_catalog_page_and_navigate_to_orders_page(String orders) {
		expectedProductsList = new ArrayList<>(Arrays.asList(orders.split(",")));
		List<WebElement> actualProductsList = getWebElements(productCatalogPage.products);

		for (String product : expectedProductsList) {
			waitForElementsToAppear(getWebElements(productCatalogPage.products));
			WebElement productElement = productCatalogPage.getProductWebElementByName(product, actualProductsList);
			Assert.assertTrue(product.equals(productElement.findElement(By.tagName("b")).getText()));

			waitForElementToDisappear(getWebElement(productCatalogPage.toastElement));
			waitForElementToAppear(
					productElement.findElement(By.cssSelector(".container .row div.col-lg-4 button:last-of-type")));
			scrollToTheWebElement(productElement);

			productCatalogPage.addProductToCart(productElement);
			
			waitForElementToDisappear(getWebElement(productCatalogPage.spinnerElement));
			waitForElementToAppear(getWebElement(productCatalogPage.toastElement));
			
			String message = productCatalogPage.getToastMessage(getWebElement(productCatalogPage.toastElement));

			Assert.assertEquals(message, "Product Added To Cart",
					"Message displayed is not as expected when product is added to the cart!!");
		}

	}

	@When("User validate actual selected orders from orders page against the list of expected orders")
	public void user_validate_actual_selected_orders_from_orders_page_against_the_list_of_expected_orders() {

		cartIcon = getWebElement(orderDetailsPage.cartIcon);
		waitForElementToAppear(cartIcon);
		scrollWindowTo(0, 0);
		waitForElementToBeClickable(cartIcon);
		cartIcon.click();

		waitForElementsToAppear(getWebElements(orderDetailsPage.cartProducts));
		List<String> actualSortedProductsList = orderDetailsPage
				.getSortedProductsInCart(getWebElements(orderDetailsPage.cartProducts));
		Assert.assertTrue(
				(expectedProductsList.stream().sorted().collect(Collectors.toList())).equals(actualSortedProductsList),
				"Products added in the cart are not as expected!!");
	}

	@Then("User navigate to checkout page")
	public void user_navigate_to_checkout_page() {
		checkOutButton = getWebElement(checkOutPage.checkoutButton);

		waitForElementToBeClickable(getWebElement(checkOutPage.checkoutButton));
		scrollToTheWebElement(checkOutButton);
		clickElementUsingJavacript(checkOutButton);

		waitForElementToBeClickable(getWebElement(checkOutPage.countryInputBox));
		checkOutPage.enterCountry("india", getWebElement(checkOutPage.countryInputBox));

		waitForElementsToAppear(getWebElements(checkOutPage.countryList));
		checkOutPage.selectCountryFromList("india", getWebElements(checkOutPage.countryList));

		waitForElementToBeClickable(getWebElement(checkOutPage.placeOrderButton));
		checkOutPage.placeOrder(getWebElement(checkOutPage.placeOrderButton));

		waitForElementToAppear(getWebElement(checkOutPage.toastElement));
		String message = checkOutPage.fetchToastMessage(getWebElement(checkOutPage.toastElement));

		Assert.assertTrue("Order Placed Successfully".equals(message), "Checkout message is not as expected!");

		scrollWindowTo(0, 0);



	}

	@Then("User verify the order status and fetch order Ids")
	public void user_verify_the_order_status_and_fetch_order_ids() {
		Assert.assertTrue("THANKYOU FOR THE ORDER.".equals(checkOutPage
				.fetchOrderDetailsFromConfirmationPage(getWebElements(checkOutPage.orderConfirmationDetails),
						getWebElements(checkOutPage.orderConfirmationIds))
				.stream().findFirst().get()));
		
		System.out.println("Order details: ");
		checkOutPage.fetchOrderDetailsFromConfirmationPage(getWebElements(checkOutPage.orderConfirmationDetails),
				getWebElements(checkOutPage.orderConfirmationIds))
				.forEach(details -> System.out.println(details));
		
		tearDown();
	}
}
