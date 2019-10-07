package com.atmecs.konakart.pages;

import java.text.ParseException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import com.atmecs.konakart.helper.ActionHelper;
import com.atmecs.konakart.helper.ValidaterHelper;
import com.atmecs.konakart.logreports.LogReporter;

public class HeroPage {
	LogReporter log=new LogReporter();
	ActionHelper help=new ActionHelper();
	ValidaterHelper validate=new ValidaterHelper();
	public void validateHeropPage(WebDriver driver,Properties prop,String[] proddata) throws InterruptedException, ParseException{
		validate.validateTitle(driver,proddata[0]);
		help.clickElement(prop.getProperty("loc.img.hero"),driver);
		if(proddata[1].equalsIgnoreCase(driver.getTitle())) 
		{
			validate.validateTitle(driver,proddata[1]);
			help.scrollPage(driver,prop.getProperty("loc.tab.proddiscrip") );
			help.clickElement(prop.getProperty("loc.tab.proddiscrip"), driver);
			String proddiscrip=validate.webElementgetText(driver, prop.getProperty("loc.tab.proddiscrip").substring(6));
			validate.assertValidate(proddiscrip,proddata[3]);
			validate.containsValidations(driver, prop.getProperty("loc.proddiscrip.content").substring(6) ,proddata[6]);
			help.clickElement(prop.getProperty("loc.tab.specification"), driver);
			String prodspecific=validate.webElementgetText(driver, prop.getProperty("loc.tab.specification").substring(6));
			validate.assertValidate(prodspecific,proddata[4]);
			validate.containsValidations(driver, prop.getProperty("loc.prodspecific.content").substring(6) ,proddata[7]);
			validate.cutomerReviewValidation(driver, prop, proddata);
		}else {
			validate.validateTitle(driver,proddata[2]);
			help.scrollPage(driver,prop.getProperty("loc.tab.proddiscrip") );
			help.clickElement(prop.getProperty("loc.tab.proddiscrip"), driver);
			String proddiscrip=validate.webElementgetText(driver, prop.getProperty("loc.tab.proddiscrip").substring(6));
			validate.assertValidate(proddiscrip,proddata[3]);
			validate.containsValidations(driver, prop.getProperty("loc.proddiscrip.content").substring(6) ,proddata[8]);
			help.clickElement(prop.getProperty("loc.tab.specification"), driver);
			String prodspecific=validate.webElementgetText(driver, prop.getProperty("loc.tab.specification").substring(6));
			validate.assertValidate(prodspecific,proddata[4]);
			validate.containsValidations(driver, prop.getProperty("loc.prodspecific.content").substring(6) ,proddata[7]);
			validate.cutomerReviewValidation(driver, prop, proddata);
		}
	}
	
}

