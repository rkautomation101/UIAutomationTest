package com.uiautomation.basePage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.uiautomation.excelReaders.Excel_Reader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase{

	private static final Random generator = new Random();
	static final String SOURCE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz";
	static SecureRandom secureRnd = new SecureRandom();

	public static Properties prop;
	public static WebDriver driver;

	public static FileInputStream fis;
	public static ExtentTest test;
	public static ExtentReports extent;

	static DesiredCapabilities cap;
	static Excel_Reader excel;
	static {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		extent = new ExtentReports(System.getProperty("user.dir") + "\\ExtentReport"
				+ formater.format(calendar.getTime()) + ".html", false);

	}

	/**
	 * This method is to determine the reading the property file.
	 * 
	 * @throws IOException
	 */
	public void properties() throws IOException {
		prop = new Properties();
		try {
			fis = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/java/com/uiautomation/config/config.properties");
			prop.load(fis);
			System.out.println("Properties from config file " + prop);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public  String[][] getData(String excelName, String sheetName) {
		String path = System.getProperty("user.dir") + "\\TestData\\" + excelName;
		excel = new Excel_Reader(path);
		String[][] data = excel.getDataFromSheet(sheetName, excelName);
		return data;
	}
	
	SoftAssert sassert = new SoftAssert();
	public void That(boolean text){
		sassert.assertTrue(text);
	}
	public void assertEqual(Object actual, Object expected){
		sassert.assertEquals(actual, expected);
		
	}
public void assertAll(){
	sassert.assertAll();
}
	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
		// below line is just to append the date format with the screenshot name
		// to
		// avoid duplicate names
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "ERRORedTestsScreenshots"
		// under src
		// folder
		String destination = System.getProperty("user.dir") + "/ERRORedTestsScreenshots/" + screenshotName + dateName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		// Returns the captured file path
		return destination;
	}

	public void takeSnapShot(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.ERROR, "Test Case ERRORed is " + result.getName());
			test.log(LogStatus.ERROR, "Test Case ERRORed is " + result.getThrowable());
			// To capture screenshot path and store the path of the screenshot
			// in the string
			// "screenshotPath"
			// We do pass the path captured by this mehtod in to the extent
			// reports using
			// "logger.addScreenCapture" method.
			String screenshotPath = getScreenshot(driver, result.getName());
			// To add it in the extent report
			test.log(LogStatus.ERROR, test.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
		}
		// ending test
		// endTest(logger) : It ends the current test and prepares to create
		// HTML report
		extent.endTest(test);
	}

	public String getRandomString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 4) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	
	public void getResult(ITestResult result) throws IOException {
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenShotPath = capture(driver, "screenShotName");
			test.log(LogStatus.ERROR, result.getThrowable());
			test.log(LogStatus.ERROR, "Snapshot below: " + test.addScreenCapture(screenShotPath));
		}
		extent.endTest(test);
	}

	public static String capture(WebDriver driver, String screenShotName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String dest = System.getProperty("user.dir") + "\\ErrorScreenshots\\" + screenShotName + ".png";
		File destination = new File(dest);
		FileUtils.copyFile(source, destination);

		return dest;
	}
	
	
	public void initialize() throws IOException, InterruptedException {
		properties();

		invokeBrowser("chrome");
		driver.get(prop.getProperty("siteurl"));
		Thread.sleep(4000);
		driver.manage().window().maximize(); // window maximize

	}
	
 SoftAssert asserts = new SoftAssert();
	public void verify(Object actual, Object expected){
		asserts.assertEquals(actual, expected);
	}
	public String getPageTitle() {
		return null;
	}

	public String getPageHeader(By locator) {
		return null;
	}

	public WebElement getElement(By locator) {
		return null;
	}

	public void waitForElementPresent(By locator) {
	}

	public void waitForPageTitle(String title) {
	}
	public void Login(String username, String password) {

	}
	
	/**
	 * Invoke the browser for all levels.
	 * 
	 * @param browser
	 */
	public void invokeBrowser(String browser) {

		if (System.getProperty("os.name").contains("Windows")) {
			if (browser.equalsIgnoreCase("firefox")) {

				// DesiredCapabilities cap = new DesiredCapabilities();
				// cap.setCapability("proxy", proxy);
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions options = new FirefoxOptions();
				String strFFBinaryPath = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
				options.setBinary(strFFBinaryPath);
				driver = new FirefoxDriver(options);

				// driver = new FirefoxDriver();
			} else if (browser.equalsIgnoreCase("chrome")) {

				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--incognito");
				options.addArguments("--start-maximized");
				// options.addArguments("--headless");
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);

				driver = new ChromeDriver(capabilities);

			} else if (browser.equalsIgnoreCase("IE")) {
				// WebDriverManager.iedriver().setup();

				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "//drivers//IEDriverServer.exe");

				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				cap.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				cap.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
				cap.setCapability(InternetExplorerDriver.ELEMENT_SCROLL_BEHAVIOR, true);
				cap.setCapability("ignoreProtectedModeSettings", true);
				// cap.setCapability("IE.binary", "C:/Program Files
				// (x86)/Internet
				// Explorer/iexplore.exe");
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				cap.setJavascriptEnabled(true);
				cap.setCapability("requireWindowFocus", true);
				cap.setCapability("enablePersistentHover", false);

				driver = new InternetExplorerDriver(cap);

			} else {
				System.out.println("Browser Name not found");
			}

		}

	}

	public void getScreenShot(String name) {

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath()
					+ "/src/main/resources/screenshots/";
			File destFile = new File(
					(String) reportDirectory + name + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath()
					+ "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void selectDashBoardElement(String elementName) throws InterruptedException {
		Thread.sleep(5000);
		WebElement ele = driver.findElement(By.className("padding-bottom-15"));
		List<WebElement> li = ele.findElements(By.tagName("a"));
		for (WebElement webElement : li) {
			if (webElement.getText().equalsIgnoreCase(elementName)) {
				webElement.click();

				break;
			}
		}
	}

	public String captureScreen(String fileName) {
		if (fileName == "") {
			fileName = "blank";
		}
		File destFile = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath()
					+ "/src/main/java/com/test/automation/uiAutomation/screenshot/";
			destFile = new File(
					(String) reportDirectory + fileName + "_" + formater.format(calendar.getTime()) + ".png");
			FileUtils.copyFile(scrFile, destFile);
			// This will help us to link the screen shot in testNG report
			Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath()
					+ "' height='100' width='100'/> </a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destFile.toString();
	}

	public static String randomString(int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++)
			sb.append(SOURCE.charAt(secureRnd.nextInt(SOURCE.length())));
		return sb.toString();
	}

	public void acceptAlert() {
		Alert al = driver.switchTo().alert();
		al.accept();
	}

	public void dismissAlert() {
		Alert al = driver.switchTo().alert();
		al.dismiss();

	}

	public void waitforElementPresent(String element) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(element))));
	}

	public void waitForPageToLoad(long timeOutInSeconds) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driver) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		try {
			System.out.println("Waiting for page to load...");
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(expectation);
		} catch (Throwable error) {
			System.out.println(
					"Timeout waiting for Page Load Request to complete after " + timeOutInSeconds + " seconds");
			Assert.assertFalse(true, "Timeout waiting for Page Load Request to complete.");
		}
	}

	public static int getRandomNumber(){
		
		  Random r = new Random();
		    return r.nextInt((5 - 2) + 1) + 2;
	}
	
	public static int ConvertStringToInt(String value){
		   
		   int finalValue = Integer.parseInt(value);
		   return finalValue;
	}
	
	public static void EnterText(WebDriver driver, By elementLocator, String value, String name) {
		try {
			WebElement ele = driver.findElement(elementLocator);
			if (ele.isEnabled()) {
				ele.clear();
				ele.sendKeys(value);
				test.log(LogStatus.PASS, "To Verify User able to Enter " + name, value + " Text entered successfully");
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "To verify " + name + " is visible with in provided time ",
					"An exception occurred waiting for " + name + " to enter text");
		}

	}

	public static void SelectValueFromDropdown(WebDriver driver, By elementLocator, String value, String name) {
		try {
			WebElement ele = driver.findElement(elementLocator);
			if (ele.isDisplayed()) {
				new Select(driver.findElement(elementLocator)).selectByVisibleText(value);
				test.log(LogStatus.PASS, "To Verify User able to Select " + name,
						value + " Text selected successfully");
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "To verify " + name + " is visible with in provided time ",
					"An exception occurred waiting for " + name + " to enter text");
		}

	}

	public static void SelectCheckBox(WebDriver driver, By elementLocator, String name) {
		try {
			WebElement ele = driver.findElement(elementLocator);
			if (ele.isDisplayed() && ele.isEnabled()) {
				ele.click();
				test.log(LogStatus.PASS, "To Verify User able to check  " + name, name + " checked successfully");
			}
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "To verify " + name + " is clickable with in provided time ", e.getMessage());
		}
	}

	public static void IsElementClickable(WebDriver driver, By elementLocator, String name) {
		try {
			WebElement ele = driver.findElement(elementLocator);
			ele.click();
			test.log(LogStatus.PASS, "To Verify is User able to click on " + name, name + " clicked successfully");
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "To verify " + name + " is clickable with in provided time ",
					"An exception occurred waiting for " + name + " to enter text" + e.getMessage());

		}
	}
	
	public static void PageRefresh() throws InterruptedException{
		
		driver.navigate().refresh();
		Thread.sleep(1500);
	}
	
	public static boolean IsElementExpectedTextMatchWithActualTextFromListOfValues(WebDriver webDriver, By elementLocator,
			String expectedText){
		try {
			List<WebElement> ele = driver.findElements(elementLocator);
			for (WebElement webElement : ele) {
				if (webElement.getText().trim().equals(expectedText)) {
					//test.log(LogStatus.PASS, "To Verify value from List of Elements of " + name, name + "value" +expectedText+" is visible from list successfully");
					return true;
				}
			}
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "To verify " + expectedText + " is not displayed with in provided time ",
					"An exception occurred waiting for " + expectedText + " to enter text" + e.getMessage());
		}
		return false;
	}

	public static void IsUserAbleToDeleteValueFromListOfValues(WebDriver webDriver, By elementLocator,
			String expectedText, String name){
		try {
			List<WebElement> firstNameValues = driver.findElements(elementLocator);
			List<WebElement> deletebtns = driver.findElements(By.xpath("//input[@value='Delete']"));
			int finalSize  = firstNameValues.size()-1; //5
			
			for (int i = 0; i < finalSize; i++) {
			
					if (firstNameValues.get(i).getText().equals(expectedText)) {
						deletebtns.get(i).click();
						test.log(LogStatus.PASS, "To Verify " + expectedText + " able to delete from list of "+ name,
								expectedText + " Is Deleted From List of " + name);
						break;
					}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void IsExpectedValueMatchWithActualValue(WebDriver webDriver, By elementLocator,
			String expectedText) {
		try {
			WebElement ele = driver.findElement(elementLocator);
			String actualText = ele.getText();
			System.out.println(actualText);
			Assert.assertEquals(actualText, expectedText);
			test.log(LogStatus.PASS, "To Verify is Expected text " + expectedText + " matching with Actual Text",
					expectedText + " matching with Actual Text " + actualText);
		} catch (Exception e) {
			test.log(LogStatus.ERROR, "To verify " + expectedText + " is not displayed with in provided time ",
					"An exception occurred waiting for " + expectedText + " to enter text" + e.getMessage());
		}
	}

	public static boolean IsElementPresent(WebDriver driver, By elementLocator, String name) {

		WebElement ele = driver.findElement(elementLocator);
		if (ele.isDisplayed()) {
			test.log(LogStatus.PASS, "To Verify is Element present " + name, name + " Is Element Present Successfully");
		} else {
			return false;
		}
		return true;
	}

	public static void IsElementVisible(WebDriver driver, By elementLocator, String name) {
		try {
			WebElement ele = driver.findElement(elementLocator);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);
			test.log(LogStatus.PASS, "To Verify is " + name + " Element Visible ",
					name + "Element Visible Successfully");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			test.log(LogStatus.ERROR, "To verify " + name + " is visible with in provided time",
					"An exception occurred waiting for " + name + " to become visible" + e.getMessage());

		}

	}

	public static String GetText(WebDriver driver, By elementLocator) {
		String text = null;
		try {
			WebElement ele = driver.findElement(elementLocator);
			text = ele.getText();
			return text;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			test.log(LogStatus.ERROR, "To verify " + elementLocator + " is visible with in provided time",
					"An exception occurred waiting for " + elementLocator + " to become visible" + e.getMessage());
		}
		return text;
	}

	public static void waitForPageToBeReady() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// This loop will rotate for 100 times to check If page Is ready after
		// every 1 second.
		// You can replace your if you wants to Increase or decrease wait time.
		for (int i = 0; i < 400; i++) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			// To check page ready state.

			if (js.executeScript("return document.readyState").toString().equals("complete")) {
				break;
			}
		}
	}
}
