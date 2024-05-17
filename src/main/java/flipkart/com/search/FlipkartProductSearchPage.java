package flipkart.com.search;

import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.SeleniumUtils;

public class FlipkartProductSearchPage {

	SeleniumUtils seleniumUtils = new SeleniumUtils();
	static Duration waitDuration = Duration.ofSeconds(10);
	WebDriver driver;

	public FlipkartProductSearchPage(WebDriver driver) {
		this.driver = driver;
	}

	private final static By SEARCH_INPUT_TEXTBOX = By.xpath("//input[@name='q']");
	private final static By SEARCH_BTN = By.xpath("//button[@class='MJG8Up']");
	private final static By PAGINATION_COUNT = By.xpath("//div[@class='_1G0WLw']/child::span[1]");
	private final static By NEXT_BTN = By.xpath("//div[@class='_1G0WLw']/child::nav/a[12]");

	public void searchProductsFromTextbox() {
		try {
			SeleniumUtils.waitForElementClickable(driver, SEARCH_INPUT_TEXTBOX, waitDuration);
			SeleniumUtils.enterTextInTextBox(driver, driver.findElement(SEARCH_INPUT_TEXTBOX), waitDuration,
					"Woodland shoes");
			SeleniumUtils.waitForElementClickable(driver, SEARCH_INPUT_TEXTBOX, waitDuration).submit();
		} catch (Exception e) {
			throw e;
		}
	}

	public int getLastCountOfPagination() throws Exception {
		String paginationofLastCount;
		int intValueOfPaginationofLastCount;
		try {
			SeleniumUtils.scrollToViewElement(driver, PAGINATION_COUNT);
			SeleniumUtils.waitForElementClickable(driver, PAGINATION_COUNT, waitDuration);
			paginationofLastCount = SeleniumUtils.getTitleText(driver, PAGINATION_COUNT);

			String[] string = paginationofLastCount.split(" ");
			String extractedString = string[3];

			intValueOfPaginationofLastCount = Integer.valueOf(extractedString);
		} catch (Exception e) {
			throw e;
		}
		return intValueOfPaginationofLastCount;
	}

	public ArrayList<String> getProductsName() throws Exception {
		ArrayList<String> arrayList = new ArrayList<String>();
		String nameOfProducts;
		try {
			searchProductsFromTextbox();
			int countOfLastPage = getLastCountOfPagination();
			System.out.println(countOfLastPage);
			for (int currentPage = 1; currentPage <= countOfLastPage - 1; currentPage++) {
				int totalSize = driver.findElements(By.xpath(
						"//div[@class='DOjaWF YJG4Cf']/descendant::div[@class='DOjaWF gdgoEp']/child::div[@class='cPHDOP col-12-12']"))
						.size();

				for (int i = 2; i < totalSize; i++) {
					Thread.sleep(1000);
					SeleniumUtils.scrollToViewElement(driver,
							By.xpath("//div[@class='DOjaWF YJG4Cf']/descendant::div[@class='DOjaWF gdgoEp']/child::div["
									+ i + "]/child::div[@class='_75nlfW']"));
					for (int j = 1; j <= 4; j++) {
						nameOfProducts = driver.findElement(By.xpath(
								"//div[@class='DOjaWF YJG4Cf']/descendant::div[@class='DOjaWF gdgoEp']/child::div[" + i
										+ "]/child::div[@class='_75nlfW']/div[" + j
										+ "]/child::div[1]/div[@class='hCKiGj']/child::a[contains(@class,'WKTcLC')]"))
								.getText();
						arrayList.add(nameOfProducts);
						System.out.print("Row number " + i + " and column " + j + " ==> " + nameOfProducts + " | ");
					}
					System.out.println();
				}

				if (currentPage != 1) {
					totalSize = totalSize + 1;
				}

				if (currentPage == 1) {
					Thread.sleep(2000);
					SeleniumUtils.clickElement(driver, By.xpath("//div[@class='_1G0WLw']/child::nav/a[11]"));
				} else {
					Thread.sleep(2000);
					SeleniumUtils.clickElement(driver, NEXT_BTN);
				}
			}
			Thread.sleep(2000);
		} catch (Exception e) {
			throw e;
		}
		return arrayList;
	}

	public ArrayList<String> getProductsNameAnotherMethod() throws Exception {
		ArrayList<String> arrayList = new ArrayList<String>();
		String nameOfProducts;
		try {
			searchProductsFromTextbox();
			int countOfLastPage = getLastCountOfPagination();
			System.out.println(countOfLastPage);
			for (int currentPage = 1; currentPage <= countOfLastPage; currentPage++) {
				int totalSize = driver.findElements(By.xpath(
						"//div[@class='DOjaWF YJG4Cf']/descendant::div[@class='DOjaWF gdgoEp']/child::div[@class='cPHDOP col-12-12']"))
						.size();
				for (int i = 1; i <= totalSize; i++) { // Start from 1, not 2
					Thread.sleep(1000);
					SeleniumUtils.scrollToViewElement(driver,
							By.xpath("//div[@class='DOjaWF YJG4Cf']/descendant::div[@class='DOjaWF gdgoEp']/child::div["
									+ i + "]/child::div[@class='_75nlfW']"));
					for (int j = 1; j <= 4; j++) {
						nameOfProducts = driver.findElement(By.xpath(
								"//div[@class='DOjaWF YJG4Cf']/descendant::div[@class='DOjaWF gdgoEp']/child::div[" + i
										+ "]/child::div[@class='_75nlfW']/div[" + j
										+ "]/child::div[1]/div[@class='hCKiGj']/child::a[contains(@class,'WKTcLC')]"))
								.getText();
						arrayList.add(nameOfProducts);
						System.out.print("Row number " + i + " and column " + j + " ==> " + nameOfProducts + " | ");
					}
					System.out.println();
				}
				if (currentPage != countOfLastPage) { // Increment only if not the last page
					Thread.sleep(2000);
					SeleniumUtils.clickElement(driver, NEXT_BTN);
				}
			}
			Thread.sleep(2000);
		} catch (Exception e) {
			throw e;
		}
		return arrayList;
	}

}
