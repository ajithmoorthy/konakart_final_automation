package com.atmecs.konakart.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.atmecs.konakart.extentreport.Extent;
import com.atmecs.konakart.logreports.LogReporter;
import com.relevantcodes.extentreports.LogStatus;

public class ValidaterHelper extends Extent{
	LogReporter log=new LogReporter();
	ActionHelper helper=new ActionHelper(); 
	//this method will check the url is correct or not
	public void correctUrlChecker(WebDriver Driver,String Expected_Url){
		try {
			Assert.assertEquals(Driver.getCurrentUrl(),Expected_Url);
			log.logReportMessage("Successfully Validated the correct Url is :"+ Driver.getCurrentUrl());
			logger.log(LogStatus.INFO,"Successfully Validated the correct Url is :" +Driver.getCurrentUrl());
		}catch(AssertionError e) {
			System.out.println("Navigate to wrong Webpage");
			log.logReportMessage("Navigate to wrong Webpage");
			logger.log(LogStatus.INFO, "Navigate to wrong Webpage");
		}	
	}
	//this method validate the page document title
	public void validateTitle(WebDriver driver, String documentTitle){
		try {
			Assert.assertEquals(driver.getTitle(), documentTitle);
			log.logReportMessage("Document title is validated :"+driver.getTitle());
			logger.log(LogStatus.INFO,"Document title is validated :" +driver.getTitle());
		}
		catch(AssertionError e)
		{
			System.out.println("Document title is not match with Expected :"+driver.getTitle());
			log.logReportMessage("Document title is not match with Expected :"+driver.getTitle());
			logger.log(LogStatus.INFO,"Document title is not match with Expected :"+driver.getTitle());	
		}
	}
	//this method will return the inner text of the web elements
	public String webElementgetText(WebDriver webdriver,String locator) {
		WebElement element=webdriver.findElement(By.xpath(locator));
		String content=element.getText();
		return content;
	}
	public String webElementgetAttribute(WebDriver webdriver,String locator) {
		WebElement element=webdriver.findElement(By.xpath(locator));
		String content=element.getAttribute("id");
		return content;
	}
	public void validateElements(WebDriver driver,String locators,String[] footerarray) {
		WebDriverWait wait2 = new WebDriverWait(driver, 20);
		wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(locators)));
		List<WebElement> list=driver.findElements(By.xpath(locators));
		int count=0;
		while(count<1) {
			for(WebElement element:list)
			{
				String[] elementarray=element.getText().split("\n");
				for(int variable=0; variable<elementarray.length; variable++) {
					assertValidate(elementarray[variable],footerarray[count]);
					count++;
				}
			}
		}
	}
	/**
	 * Aseertvalidate is used to validate the actual and expected values is correct or not. 
	 * @param actual
	 * @param expected
	 */
	public void assertValidate(String actual,String expected) {
		try {
			Assert.assertEquals(actual,expected);
			log.logReportMessage("Actual Value :"+actual+" and Expected :"+expected+" is validated succesfully");
			logger.log(LogStatus.INFO,"Actual Value :"+actual+" and Expected :"+expected+" is validated succesfully");	
		}
		catch(AssertionError e)
		{
			System.out.println("Actual Value :"+actual+" not match with the Expected value :"+expected);
			log.logReportMessage("Actual Value :"+actual+" not match with the Expected value :"+expected);
			logger.log(LogStatus.INFO,"Actual Value :"+actual+" not match with the Expected value :"+expected);
		}

	}
	public void validateProduct(WebDriver driver,String locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
			    .withTimeout(30, TimeUnit.SECONDS)
			    .pollingEvery(5, TimeUnit.SECONDS)
			    .ignoring(NoSuchElementException.class);
			List<WebElement> foo = wait.until(new Function<WebDriver, List<WebElement>>() 
			{
			  public List<WebElement> apply(WebDriver driver) {
			  return driver.findElements(By.xpath(locator));
			}
			});
			int count=0;
		while(count<foo.size()) {
			for(WebElement element:foo)
			{
				System.out.println(element.getText());
					count++;
			}
		}
	}
	public boolean containsValidations(WebDriver webdriver,String locator,String expected) {
		boolean bool=false;
		WebElement element=webdriver.findElement(By.xpath(locator));
		String content=element.getText();
		if(content.contains(expected))
		{
			bool=true;
		}
		return bool;
	}
	public int[] validateBlogContent(WebDriver driver,String locator) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date=new Date();
		WebDriverWait wait2 = new WebDriverWait(driver, 20);
		wait2.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
		List<WebElement> list=driver.findElements(By.xpath(locator));
		int count=0;
		int[] datearray=new int[(list.size()/2)];
		for(WebElement element:list) {
			if(count<list.size()/2) {
			String[] tempdate=element.getText().split(" ");	
			String exam=tempdate[1]+"/"+getMonthNumber(tempdate[2])+"/"+tempdate[3];
			exam=exam.replace("\\s", "");
			String secon=formatter.format(date);
			Date first=formatter.parse(exam);
			Date second=formatter.parse(secon);
			long numdays=daysBetween(first,second);
			if(numdays>90) {
				System.out.println(numdays);
				datearray[count]=(int) numdays;
			}
			count++;
			}
		}
		return datearray;
	}
	public int[] ratingValidation(WebDriver driver,String locator,String locators) {
		int length=findListlength(driver,locator);
		int size=length/2;
		int[] ratingarray=new int[size]; 
		for(int initial=0; initial<length/2; initial++) {
			int rating=findListlength(driver,locators.replace("index", (initial+1)+""));
			System.out.println(rating);
			ratingarray[initial]=rating;
		}
		return ratingarray;
	}
	private static long daysBetween(Date one, Date two) {
		long difference =  (one.getTime()-two.getTime())/86400000;
		return Math.abs(difference);
	}
	private int getMonthNumber(String monthName) {
		return Month.valueOf(monthName.toUpperCase()).getValue();
	}
	public void assertValidate(boolean actual,boolean expected) {
		try {
			Assert.assertEquals(actual,expected);
			log.logReportMessage("Actual Value :"+actual+" and Expected :"+expected+" is validated succesfully");
			logger.log(LogStatus.INFO,"Actual Value :"+actual+" and Expected :"+expected+" is validated succesfully");	
		}
		catch(AssertionError e)
		{
			System.out.println("Actual Value :"+actual+" not match with the Expected value :"+expected);
			log.logReportMessage("Actual Value :"+actual+" not match with the Expected value :"+expected);
			logger.log(LogStatus.INFO,"Actual Value :"+actual+" not match with the Expected value :"+expected);
		}

	}
	public int findListlength(WebDriver webdriver,String locator) {
		List<WebElement> list=webdriver.findElements(By.xpath(locator));
		int length=list.size();
		return length;
	}
	public void cutomerReviewValidation(WebDriver driver,Properties prop,String[] proddata) throws ParseException, InterruptedException {		
		helper.clickElement(prop.getProperty("loc.tab.reviews"), driver);
		String prodreviews=webElementgetText(driver, prop.getProperty("loc.tab.reviews").substring(6));
		assertValidate(prodreviews.substring(0, prodreviews.length()-3),proddata[5]);
		int[] datearray=validateBlogContent(driver, prop.getProperty("loc.reviewtxt.date").substring(6));
		arrayValidaterMostRecent(datearray);
		helper.dropDown(prop.getProperty("loc.dopdown.sort"), driver, 1);
		Thread.sleep(3000);
		int[] datearray1=validateBlogContent(driver, prop.getProperty("loc.reviewtxt.date").substring(6));
		arrayValidaterOldest(datearray1);
		Thread.sleep(3000);
		helper.dropDown(prop.getProperty("loc.dopdown.sort"), driver, 2);
		int[] ratingarray=ratingValidation(driver,prop.getProperty("loc.size.review"),prop.getProperty("loc.review.rating"));
		arrayValidaterOldest(ratingarray);
		Thread.sleep(3000);
		helper.dropDown(prop.getProperty("loc.dopdown.sort"), driver, 3);
		int[] ratingarray1=ratingValidation(driver,prop.getProperty("loc.size.review"),prop.getProperty("loc.review.rating"));
		arrayValidaterMostRecent(ratingarray1);
	}
	public void arrayValidaterMostRecent(int[] datearray){
		boolean variable=false;
		for(int count=0; count<datearray.length-1; count++) {
			if(datearray[count+1]-datearray[count]<0) 
			{ 
				variable=false; 
			}
			else {
				variable=true;
			}
		} 
		assertValidate(variable, true);
	}
	public void arrayValidaterOldest(int[] datearray){
		boolean variable=false;
		for(int count=0; count<datearray.length-1; count++) {
			if(datearray[count+1]-datearray[count]>0) 
			{ 
				variable=false; 
			}
			else {
				variable=true;
			}
		} 
		assertValidate(variable, true);
	}
}

