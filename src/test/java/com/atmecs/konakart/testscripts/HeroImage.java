package com.atmecs.konakart.testscripts;

import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.atmecs.konakart.constants.FileConstants;
import com.atmecs.konakart.helper.ActionHelper;
import com.atmecs.konakart.logreports.LogReporter;
import com.atmecs.konakart.pages.HeroPage;
import com.atmecs.konakart.testbase.TestBase;
import com.atmecs.konakart.utils.ExcelReader;
import com.atmecs.konakart.utils.PropertiesReader;

public class HeroImage extends TestBase {
	LogReporter log=new LogReporter();
	ActionHelper help=new ActionHelper();
	ExcelReader excelread=new ExcelReader();
	HeroPage hero=new HeroPage();
	PropertiesReader propread=new PropertiesReader();
	@DataProvider(name = "productdata")
	public String[][] getValidationData() throws IOException {
		String Excelarray[][] = null;
		Excelarray = excelread.excelDataProviderArray(FileConstants.PROD_TEST_PATH);
		return Excelarray;
	}
	@Test(dataProvider="productdata")
	public void validateHeroImage(String[] data) throws IOException, InterruptedException, ParseException {
		Properties prop=propread.KeyValueLoader(FileConstants.HERO_PATH);
		logger=extentObject.startTest("Validate hero img");
		hero.validateHeropPage(driver, prop,data);
	}
}
