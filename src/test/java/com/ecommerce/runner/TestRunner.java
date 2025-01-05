package com.ecommerce.runner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src/test/resources/features" }, glue = { "com.ecommerce.stepdefinitions" }, plugin = {
		"pretty",
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" }, tags = "@PostAPIValidation or @ErrorMessage or @DBValidation2 or @SingleOrder")
public class TestRunner extends AbstractTestNGCucumberTests {
	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}

}
