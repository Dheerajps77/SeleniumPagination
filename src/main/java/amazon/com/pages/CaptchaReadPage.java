package amazon.com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.OCRUtils;
import utils.SeleniumUtils;

public class CaptchaReadPage {

	WebDriver driver;
	SeleniumUtils seleniumUtils = new SeleniumUtils();
	OCRUtils ocrUtils = new OCRUtils();

	public CaptchaReadPage(WebDriver driver) {
		this.driver = driver;
	}

	private final static By CAPTCHA_BOX = By.xpath("//div[@class='a-box']");
	private final static By CAPTCHA_TEXTBOX = By.xpath("//input[@id='captchacharacters']");
	private final static By CONTINUE_SHOPPING_BTN = By.xpath("//button[@class='a-button-text']");
	private final static By CAPTCHA_IMG = By.xpath("//div[@class='a-row a-text-center']/img");

	public boolean verifyCaptchaBoxAppearAndPerformCaptchaOperation(WebDriver driver) throws Exception {
		boolean flag = false;
		String captchaValue;
		try {

			if (seleniumUtils.isElementDisplayed(driver, CAPTCHA_BOX)) {
				flag = true;
				captchaValue = OCRUtils.extractTextFromImage(driver, driver.findElement(CAPTCHA_IMG));
				System.out.println("Captcha value is : " + captchaValue);
				SeleniumUtils.waitForElementClickable(driver, CAPTCHA_TEXTBOX, AmazonSearch.waitDuration);
				SeleniumUtils.enterTextInTextBox(driver, driver.findElement(CAPTCHA_TEXTBOX), AmazonSearch.waitDuration,
						captchaValue);
				SeleniumUtils.clickElement(driver, CONTINUE_SHOPPING_BTN);
				
			}

		} catch (Exception e) {
			System.err.println("It seems Captcha Box is not appear : " + e.getMessage());
			return flag;
		}
		return flag;
	}

}
