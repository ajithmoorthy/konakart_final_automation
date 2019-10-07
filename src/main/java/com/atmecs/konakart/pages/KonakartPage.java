package com.atmecs.konakart.pages;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.atmecs.konakart.helper.ActionHelper;
import com.atmecs.konakart.helper.ValidaterHelper;
import com.atmecs.konakart.logreports.LogReporter;

public class KonakartPage {
	LogReporter log=new LogReporter();
	ActionHelper help=new ActionHelper();
	ValidaterHelper validate=new ValidaterHelper();
	public void konakartValidate(WebDriver driver,Properties prop,String[] data) {
		validate.validateTitle(driver,data[0]);
		help.dropDown(prop.getProperty("loc.dropdown.ecom"), driver, Integer.parseInt(data[4]));
		String temp=data[1]+","+data[2]+","+data[3];
		String[] temparray=temp.split(",");
		recursiveMethod(driver,prop,data[5],temparray);
	}
	public void recursiveMethod(WebDriver driver,Properties prop,String option,String[] temparray)
	{
		help.sendKeys(prop.getProperty("loc.txtfield.search"), driver, option);
		help.clickElement(prop.getProperty("loc.btn.search"), driver);
		WebElement element=driver.findElement(By.xpath(prop.getProperty("loc.container.root")));
		String[] countString=element.getText().split("\n");
		if(countString.length>1) {
		WebElement prodelement=driver.findElement(By.xpath(prop.getProperty("loc.panel.konakartproduct.xpath")));
		String[] elementarray=prodelement.getText().split("\n");
		int count=0;
		for(String string:elementarray) {
			validate.assertValidate(string, temparray[count]);
			count++;
		}
		}else {
		WebElement emptyelement=driver.findElement(By.xpath(prop.getProperty("loc.txt.prodnotavailabe.xpath")));
			validate.assertValidate(emptyelement.getText(), "There are no available products.");	
		}
		}
}
