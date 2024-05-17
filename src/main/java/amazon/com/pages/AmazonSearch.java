package amazon.com.pages;

import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.SeleniumUtils;

public class AmazonSearch {

	private WebDriver driver;
	SeleniumUtils seleniumUtils = new SeleniumUtils();
	CaptchaReadPage captchaReadPage = new CaptchaReadPage(driver);
	static Duration waitDuration = Duration.ofSeconds(10);

	public AmazonSearch(WebDriver driver) {
		this.driver = driver;
	}

	public final static By AMAZON_SEARCH_TEXTBOX = By.xpath("//input[@id='twotabsearchtextbox']");
	public final static By SEARCH_BTN = By.xpath("//input[@id='nav-search-submit-button']");
	private final static By RESULTS_LABEL = By
			.xpath("//span[@data-component-type='s-search-results']/div[1]/child::div[2]/div/span/div/div/span");
	private final static By TOTAL_SEARCHED_PRODUCTS_NAME = By.xpath(
			"//*[@id='search']/div[1]/div[1]/div/span[1]/div[1]/div/div/div/span/div/div/div/div[2]/div/div/div[1]/h2/a/span");

	private final static By LAST_COUNT_PAGINATION_ELEMENT = By.xpath(
			"//div[@class='a-section a-text-center s-pagination-container']/span[@class='s-pagination-strip']/span[4]");
	private final static By PAGINATION_NEXT_BTN = By
			.xpath("//a[@class='s-pagination-item s-pagination-next s-pagination-button s-pagination-separator']");

	public int getLastCountOfPagination() {
		int lastLengthValue;
		String textValue;
		try {
			SeleniumUtils.scrollToViewElement(driver, LAST_COUNT_PAGINATION_ELEMENT);
			SeleniumUtils.waitForElementClickable(driver, LAST_COUNT_PAGINATION_ELEMENT, waitDuration);
			textValue = driver.findElement(LAST_COUNT_PAGINATION_ELEMENT).getText();
			lastLengthValue = Integer.parseInt(textValue);
		} catch (Exception e) {
			throw e;
		}
		return lastLengthValue;
	}

	public void clickOnNextButton() {
		try {
			SeleniumUtils.waitForElementClickable(driver, PAGINATION_NEXT_BTN, waitDuration);
			SeleniumUtils.clickElement(driver, PAGINATION_NEXT_BTN);

		} catch (Exception e) {
			throw e;
		}
	}

	public void searchProductsNameUsingPagination() throws Exception {
		int temp = 0, lastCountOfPagination;
		try {
			searchProducts();
			getAllProductsCodeName();
			lastCountOfPagination = getLastCountOfPagination();
			System.out.println("Total length of pagination is : " + lastCountOfPagination);
			for (int i = 0; i <= lastCountOfPagination; i++) {
				clickOnNextButton();
				Thread.sleep(2000);
				System.out.println("Current pagination : " + temp++);
				SeleniumUtils.scrollToViewElement(driver, RESULTS_LABEL);
				getAllProductsCodeName();
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public void collectAllProductsName() throws Exception {

		try {
			if (captchaReadPage.verifyCaptchaBoxAppearAndPerformCaptchaOperation(driver)) {
				searchProductsNameUsingPagination();
			} else {
				searchProductsNameUsingPagination();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void searchProducts() {
		try {
			SeleniumUtils.waitForElementClickable(driver, AMAZON_SEARCH_TEXTBOX, waitDuration);
			SeleniumUtils.enterTextInTextBox(driver, driver.findElement(AMAZON_SEARCH_TEXTBOX), waitDuration, "Iphone");
			SeleniumUtils.waitForElementClickable(driver, SEARCH_BTN, waitDuration);
			SeleniumUtils.clickElement(driver, SEARCH_BTN);
			SeleniumUtils.waitForElementClickable(driver, RESULTS_LABEL, waitDuration);

		} catch (Exception e) {
			throw e;
		}
	}

	public ArrayList<String> getAllProductsCodeName() throws Exception {
		int totalLen;
		ArrayList<String> arrayList = new ArrayList<String>();
		try {

			totalLen = driver.findElements(TOTAL_SEARCHED_PRODUCTS_NAME).size();
			System.out.println("total products are in a current page " + totalLen);
			
			int totalSizeOfProducts=driver.findElements(By.xpath("//*[@id='search']/div[1]/div[1]/div/span[1]/div[1]/div/div/div/span/div/div/div/div[2]/div/div/div[1]/h2/a/span")).size();
			for (int i = 5; i < totalSizeOfProducts-2; i++) {

				SeleniumUtils.scrollToViewElement(driver, By.xpath(
						"//*[@id='search']/div[1]/div[1]/div/span[1]/div[1]/div["+i+"]/div/div/span/div/div/div/div[2]/div/div/div[1]/h2/a/span"));
				SeleniumUtils.waitForElementClickable(driver, By.xpath(
						"//*[@id='search']/div[1]/div[1]/div/span[1]/div[1]/div["+i+"]/div/div/span/div/div/div/div[2]/div/div/div[1]/h2/a/span"),
						waitDuration);

				String productName = driver.findElement(By.xpath(
						"//*[@id='search']/div[1]/div[1]/div/span[1]/div[1]/div["+i+"]/div/div/span/div/div/div/div[2]/div/div/div[1]/h2/a/span"))
						.getText();
				arrayList.add(productName);
				System.out.println(productName);
			}

		} catch (Exception e) {
			throw e;
		}
		return arrayList;
	}
}
