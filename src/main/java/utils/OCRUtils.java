package utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OCRUtils {

    public static String extractTextFromImage(WebDriver driver, WebElement imageElement) {
        // Capture the screenshot of the image element
        File screenshotFile = imageElement.getScreenshotAs(OutputType.FILE);

        // Use Tesseract to extract text from the image
        Tesseract tesseract = new Tesseract();
        try {
            String extractedText = tesseract.doOCR(screenshotFile);
            return extractedText;
        } catch (TesseractException e) {
            System.err.println("Error extracting text from image: " + e.getMessage());
            return null;
        } finally {
            // Delete the temporary screenshot file
            if (screenshotFile != null) {
                try {
                    FileUtils.forceDelete(screenshotFile);
                } catch (IOException e) {
                    System.err.println("Error deleting temporary screenshot file: " + e.getMessage());
                }
            }
        }
    }
}