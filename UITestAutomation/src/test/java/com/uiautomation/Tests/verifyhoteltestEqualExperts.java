package com.uiautomation.Tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.uiautomation.JiraUtils.JiraPolicy;
import com.uiautomation.basePage.TestBase;
import com.uiautomation.pages.HotelBookingFormPage;

/**
 * 
 * @author RK
 *
 */
public class verifyhoteltestEqualExperts extends TestBase {

	// Create an Instance for Login Page
	HotelBookingFormPage hotelBookingFormPage;

	public verifyhoteltestEqualExperts() {
		super();
	}

	@BeforeSuite
	public void setUp() throws InterruptedException {
		try {
			initialize();
			hotelBookingFormPage = new HotelBookingFormPage(driver);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * test Data for test Method
	 * 
	 * @return
	 */

	@DataProvider(name = "savehoteldata")
	public String[][] savehoteldata() {
		String[][] testdata = getData("TestData.xlsx", "savedata");
		return testdata;
	}

	@DataProvider(name = "invalidpassword")
	public String[][] invalidPwd() {
		String[][] testdata = getData("TestData.xlsx", "invalidpassword");
		return testdata;
	}

	/**
	 * Scenario - VerifyHotelBookingFormWithValidCredentials-----
	 * 
	 * @param title
	 * @param header
	 * @param fnamelbl
	 * @param firstnametxt
	 * @param surnamelbl
	 * @param surnametxt
	 * @param pricelbl
	 * @param pricetxt
	 * @param depositlbl
	 * @param deposittxt
	 * @param checkinlbl
	 * @param checkinDate
	 * @param checkoutlbl
	 * @param checkoutdate
	 * @throws InterruptedException
	 */
	@JiraPolicy(logTicketReady=true)
	@Test(priority = 0, dataProvider = "savehoteldata")
	public void TS01_VerifyHotelBookingFormWithValidCredentials(String title, String header, String fnamelbl,
			String firstnametxt, String surnamelbl, String surnametxt, String pricelbl, String pricetxt,
			String depositlbl, String deposittxt, String checkinlbl, String checkinDate, String checkoutlbl,
			String checkoutdate) throws InterruptedException {
		test = extent.startTest("TS01_verify Hotel booking form-save functionality with valid data");

		HotelBookingFormPage.IsHotelBookingFormTitleDisplayed(title);
		HotelBookingFormPage.IsHotelBooingFormHeaderDisplayed(header);

		HotelBookingFormPage.IsFirstNameLabelDisplayed(fnamelbl);
		firstnametxt = firstnametxt + " " + getRandomString();
		HotelBookingFormPage.IsUserAbletoEnterFirstName(firstnametxt);
		assertEqual(firstnametxt, checkoutlbl);
		Thread.sleep(3000);
		System.out.println("Assert Passs...........................");
		HotelBookingFormPage.IsSurNameLabelDisplayed(surnamelbl);
		surnametxt = surnametxt + " " + getRandomString();
		HotelBookingFormPage.IsUserAbletoEnterSurName(surnametxt);

		HotelBookingFormPage.IsPriceLabelDisplayed(pricelbl);
        pricetxt = pricetxt + getRandomNumber();
        System.out.println("Price Values "+ pricetxt);
        assertEqual(pricetxt, depositlbl);
		HotelBookingFormPage.IsUserAbletoEnterPrice(pricetxt);
		Thread.sleep(3000);
		HotelBookingFormPage.IsDepositLabelDisplayed(depositlbl);
		
		HotelBookingFormPage.IsUserAbletoEnterDeposit(deposittxt);

		HotelBookingFormPage.IsCheckInLabelDisplayed(checkinlbl);
		
        assertAll();
	}

	@AfterMethod
	public void takeSnapShot(ITestResult result) throws Exception {
		getResult(result);
	}

	@AfterTest
	public void quit() {
		//driver.close();
		extent.endTest(test);
		extent.flush();
		extent.close();
		System.out.println("End");
	}
}
