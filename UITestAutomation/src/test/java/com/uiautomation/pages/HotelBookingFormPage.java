package com.uiautomation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.uiautomation.basePage.TestBase;

public class HotelBookingFormPage {

	public static WebDriver driver;

	// Login Page Constructor with Driver to connect to TestBase driver.
	public HotelBookingFormPage(WebDriver driver) {
		HotelBookingFormPage.driver = driver;
	}

	public static String getTitle() {
		return driver.getTitle();
	}

	public static String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	static By headerlbl = By.xpath("//h1[text()='Hotel booking form']");
	static By fnamelbl = By.xpath("//h3[text()='Firstname']");
	static By surnamelbl = By.xpath("//h3[text()='Surname']");
	static By pricelbl = By.xpath("//h3[text()='Price']");
	static By depositlbl = By.xpath("//h3[text()='Deposit']");
	static By checkinlbl = By.xpath("//h3[text()='Check-in']");
	static By checkoutlbl = By.xpath("//h3[text()='Check-out']");
	static By savebtn = By.xpath("//input[@value=' Save ']");
	static By deletbtn = By.xpath("//input[@value=' Delete ']");
	static By firstnamerecords = By.xpath("//*[@class='row']/following::div[2]");

	static By fnametxt = By.id("firstname");
	static By surnametxt = By.id("lastname");
	static By pricetxt = By.id("totalprice");
	static By depositdrp = By.id("depositpaid");
	static By checkindt = By.id("checkin");
	static By checkoutdt = By.id("checkout");
	static By valuesfromlist = By.cssSelector("div.col-md-2 p");
	static By valuesfromlistforprice = By.cssSelector("div.col-md-1 p");

	private static String nmheader = "Hotel Booking Form header";
	private static String nmfnamelbl = "Firstname lable";
	private static String nmsurnamelbl = "Surname lable";
	private static String nmpricelbl = "price lable";
	private static String nmdepositlbl = "Deposit lable";

	private static String nmcheckinlbl = "check in date lable";
	private static String nmcheckoutlbl = "check out date lable";

	private static String nmfnametxt = "Firstname text field";
	private static String nmsurnametxt = "Surname text field";
	private static String nmpricetxt = "price text field";
	private static String nmdepositdrp = "Deposit Drop down";

	private static String nmcheckindt = "check in date field";
	private static String nmcheckoutdt = "check out date field";
	private static String nmsavebtn = "Save button";
	private static String nmfirstnamevalues = "First name from list of values";
	

	public static void IsHotelBookingFormTitleDisplayed(String expectedTitle) {

		String title = getTitle();
		Assert.assertEquals(title, expectedTitle);
	}

	public static void IsHotelBooingFormHeaderDisplayed(String headers) {
		TestBase.IsElementVisible(HotelBookingFormPage.driver, headerlbl, nmheader);
		String headertxt = TestBase.GetText(HotelBookingFormPage.driver, headerlbl);
		Assert.assertEquals(headertxt, headers);
	}

	public static void IsFirstNameLabelDisplayed(String value) {
		TestBase.IsElementVisible(HotelBookingFormPage.driver, fnamelbl, nmfnamelbl);
		String headertxt = TestBase.GetText(HotelBookingFormPage.driver, fnamelbl);
		Assert.assertEquals(headertxt, value);
	}

	public static void IsUserAbletoEnterFirstName(String value) {
		TestBase.IsElementVisible(HotelBookingFormPage.driver, fnametxt, nmfnametxt);
		TestBase.EnterText(HotelBookingFormPage.driver, fnametxt, value, nmfnametxt);
	}

	public static void IsSurNameLabelDisplayed(String value) {

		TestBase.IsElementVisible(HotelBookingFormPage.driver, surnamelbl, nmsurnamelbl);
		String surname = TestBase.GetText(HotelBookingFormPage.driver, surnamelbl);
		Assert.assertEquals(surname, value);
	}

	public static void IsUserAbletoEnterSurName(String value) {
		TestBase.IsElementVisible(HotelBookingFormPage.driver, surnametxt, nmsurnametxt);
		TestBase.EnterText(HotelBookingFormPage.driver, surnametxt, value, nmsurnametxt);
	}

	public static void IsPriceLabelDisplayed(String value) {

		TestBase.IsElementVisible(HotelBookingFormPage.driver, pricelbl, nmpricelbl);
		String price = TestBase.GetText(HotelBookingFormPage.driver, pricelbl);
		Assert.assertEquals(price, value);
	}

	public static void IsUserAbletoEnterPrice(String value) {
		TestBase.IsElementVisible(HotelBookingFormPage.driver, pricetxt, nmpricetxt);
		TestBase.EnterText(HotelBookingFormPage.driver, pricetxt, value, nmpricetxt);
	}

	public static void IsDepositLabelDisplayed(String value) {

		TestBase.IsElementVisible(HotelBookingFormPage.driver, depositlbl, nmdepositlbl);
		String deposit = TestBase.GetText(HotelBookingFormPage.driver, depositlbl);
		Assert.assertEquals(deposit, value);
	}

	public static void IsUserAbletoEnterDeposit(String value) {
		TestBase.IsElementVisible(HotelBookingFormPage.driver, depositdrp, nmdepositdrp);
		TestBase.SelectValueFromDropdown(HotelBookingFormPage.driver, depositdrp, value, nmdepositdrp);
	}

	public static void IsCheckInLabelDisplayed(String value) {

		TestBase.IsElementVisible(HotelBookingFormPage.driver, checkinlbl, nmcheckinlbl);
		String deposit = TestBase.GetText(HotelBookingFormPage.driver, checkinlbl);
		Assert.assertEquals(deposit, value);
	}

	public static void IsUserAbletoEnterCheckInDate(String value) {
		TestBase.IsElementVisible(HotelBookingFormPage.driver, checkindt, nmcheckindt);
		TestBase.EnterText(HotelBookingFormPage.driver, checkindt, value, nmcheckindt);
	}

	public static void IsCheckOutLabelDisplayed(String value) {

		TestBase.IsElementVisible(HotelBookingFormPage.driver, checkoutlbl, nmcheckoutlbl);
		String deposit = TestBase.GetText(HotelBookingFormPage.driver, checkoutlbl);
		Assert.assertEquals(deposit, value);
	}

	public static void IsUserAbletoEnterCheckOut(String value) {
		TestBase.IsElementVisible(HotelBookingFormPage.driver, checkoutdt, nmcheckoutdt);
		TestBase.EnterText(HotelBookingFormPage.driver, checkoutdt, value, nmcheckoutdt);
	}

	public static void IsSaveButtonDisplayed() {
		TestBase.IsElementVisible(HotelBookingFormPage.driver, savebtn, nmsavebtn);
	}

	public static void ClickSavebutton() throws InterruptedException {
		TestBase.IsElementVisible(HotelBookingFormPage.driver, savebtn, nmsavebtn);
		TestBase.IsElementClickable(HotelBookingFormPage.driver, savebtn, nmsavebtn);
		Thread.sleep(3000);
		TestBase.PageRefresh();
	}
	
	public static boolean IsSavedDataDisplayedSuccessfully(String value){
		return TestBase.IsElementExpectedTextMatchWithActualTextFromListOfValues(HotelBookingFormPage.driver, valuesfromlist, value);
	}
	public static boolean IsSavedDataDisplayedSuccessfullyForPrice(String value){
		return TestBase.IsElementExpectedTextMatchWithActualTextFromListOfValues(HotelBookingFormPage.driver, valuesfromlistforprice, value);
	}
	
	public static void IsUserAbleToDeleteRecordFromHotelBookingList(String value){
	 TestBase.IsUserAbleToDeleteValueFromListOfValues(HotelBookingFormPage.driver, firstnamerecords, value, nmfirstnamevalues);
	}
	
	
	

}