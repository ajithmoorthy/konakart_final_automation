package com.atmecs.konakart.testscripts;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.atmecs.konakart.constants.FileConstants;
import com.atmecs.konakart.helper.ActionHelper;
import com.atmecs.konakart.logreports.LogReporter;
import com.atmecs.konakart.pages.KonakartPage;
import com.atmecs.konakart.testbase.TestBase;
import com.atmecs.konakart.utils.ExcelReader;
import com.atmecs.konakart.utils.PropertiesReader;

public class WelcomePage extends TestBase {
	LogReporter log=new LogReporter();
	ActionHelper help=new ActionHelper();
	KonakartPage konakart=new KonakartPage();
	PropertiesReader propread=new PropertiesReader();
	ExcelReader excelread=new ExcelReader();
	@DataProvider(name = "welcomedata")
	public String[][] getValidationData() throws IOException {
		String Excelarray[][] = null;
		Excelarray = excelread.excelDataProviderArray(FileConstants.TEST_PATH);
		return Excelarray;
	}
	@Test(dataProvider="welcomedata")
	public void validateKonakart(String[] data) throws IOException, InterruptedException {
		Properties prop=propread.KeyValueLoader(FileConstants.WELCOME_PATH);
		logger=extentObject.startTest("validate konakart");
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.manage().window().maximize();
		konakart.konakartValidate(driver, prop,data);
	}
}
