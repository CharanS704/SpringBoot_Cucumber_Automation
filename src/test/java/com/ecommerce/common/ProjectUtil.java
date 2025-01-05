package com.ecommerce.common;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;

import com.ecommerce.pages.OrderDetailsPage;

import org.openqa.selenium.JavascriptExecutor;

public class ProjectUtil {

	@Value("${browserName}")
	String browser;

	@Value("${default.timeout}")
	Long defaultTimeout;

	public WebDriver webDriver;
	public WebDriverWait webDriverWait;
	public JavascriptExecutor javaScriptExecutor;

	public void createDriver() {
		System.out.println("Selected browser is: " + browser);
		switch (browser) {
		case "Chrome":
			this.webDriver = new ChromeDriver();
			break;
		case "FireFox":
			this.webDriver = new FirefoxDriver();
			break;
		case "Edge":
			this.webDriver = new EdgeDriver();
			break;
		default:
			this.webDriver = null;
		}

	}

	public void getExplicitWait() {
		System.out.println("Explicit wait of: " + defaultTimeout + " seconds is created!");
		this.webDriverWait = new WebDriverWait(this.webDriver, Duration.ofSeconds(defaultTimeout));

	}

	public void createJavaScriptExecutor() {
		this.javaScriptExecutor = (JavascriptExecutor) webDriver;
	}

	public void getUrl(String url) {
		this.webDriver.get(url);
		this.webDriver.manage().window().maximize();
		this.webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
		this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
	}
	
	public WebElement getWebElement(By by) {
		return this.webDriver.findElement(by);
	}

	public List<WebElement> getWebElements(By by) {
		return this.webDriver.findElements(by);

	}

	public void waitForElementToAppear(WebElement webElement) {
		this.webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
	}

	public void waitForElementToBeClickable(WebElement webElement) {
		this.webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
	}

	public void waitForElementToDisappear(WebElement webElement) {
		this.webDriverWait.until(ExpectedConditions.invisibilityOf(webElement));
	}

	public void waitForElementsToAppear(List<WebElement> webElements) {
		this.webDriverWait.until(ExpectedConditions.visibilityOfAllElements(webElements));
	}

	public void scrollToTheWebElement(WebElement element) {
		this.javaScriptExecutor.executeScript("arguments[0].scrollIntoView(true)", element);
	}

	public void scrollWindowTo(int x, int y) {
		this.javaScriptExecutor.executeScript("window.scrollTo(" + x + "," + y + ");");
	}

	public void clickElementUsingJavacript(WebElement element) {
		this.javaScriptExecutor.executeScript("arguments[0].click();", element);
	}
	
	public void tearDown() {
		webDriver.quit();
	}


}
