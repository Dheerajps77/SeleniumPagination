package amazon.com.tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Testbase.BaseTest;
import amazon.com.pages.AmazonSearch;
import flipkart.com.search.FlipkartProductSearchPage;

public class AmazonSearchProducts extends BaseTest{

	AmazonSearch amazonSearch;
	FlipkartProductSearchPage flipkartProductSearchPage;
	
	@Test(enabled=false)
	@Parameters({"browser", "Amazonurl"})
	public void searchProduct(String browser, String Amazonurl) throws Exception
	{
		try {
			
			WebDriverManager(browser, Amazonurl);
			amazonSearch=new AmazonSearch(driver);
			amazonSearch.collectAllProductsName();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test(enabled=true)
	@Parameters({"browser", "Flipkarturl"})
	public void searchProductOnFlipkart(String browser, String Flipkarturl) throws Exception
	{
		try {
			
			WebDriverManager(browser, Flipkarturl);
			flipkartProductSearchPage=new FlipkartProductSearchPage(driver);
			flipkartProductSearchPage.getProductsName();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
