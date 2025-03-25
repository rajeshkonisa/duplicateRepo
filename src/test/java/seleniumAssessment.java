import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;

public class seleniumAssessment {

	public static WebDriver driver;

	static String expectedReimburementValue = "$110700";

	public static void main(String[] args) throws InterruptedException {

		// Set up WebDriver and navigate to FitPeo homepage

		// Navigate to the revenue calculator tab
		try {
			driver = new EdgeDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
			driver.get("https://www.fitpeo.com/revenue-calculator");
		} catch (Exception e) {
			System.out.println("Unable to launch requested URL.");
			e.printStackTrace();
			Thread.sleep(2000);
		}
		WebElement revenueCalculatorTab = driver.findElement(By.xpath("//div[contains(text(), 'Revenue Calculator')]"));
		revenueCalculatorTab.click();
		Thread.sleep(2000);

	// Scroll to the slider section
		WebElement sliderSection = driver.findElement(By.xpath("//input[@type='range']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", sliderSection);
		Thread.sleep(1000);

	// Setting slider to 820.
		WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));
	//	Actions action = new Actions(driver);
		
		try {
			for (int i = 0; i < 820; i++) {
				slider.sendKeys(Keys.ARROW_RIGHT);

				String value = driver.findElement(By.xpath("//input[@type='number']")).getAttribute("value");

				if (value.equalsIgnoreCase("820")) {
					i = 821;
				}
			}
		}catch(Exception e) {
			System.out.println("Unable to set slider to requested value.");
			e.printStackTrace();
		}
		
		Thread.sleep(1000);
		
	// Validating the text
		WebElement sliderValue = driver.findElement(By.xpath("//input[@type='number']"));
		if (sliderValue.getAttribute("value").equals("820")) {
			System.out.println("Slider successfully set to 820.");
		} else {
			System.out.println("Slider not successfully set to 820.");
		}

		// Update the text field to 560 and verify the slider updates
		WebElement textField = driver.findElement(By.xpath("//input[@type='number']"));
		textField.clear();
		for (int i = 0; i < 1820; i++) {
			slider.sendKeys(Keys.ARROW_LEFT);

			String value = driver.findElement(By.xpath("//input[@type='number']")).getAttribute("value");

			if (value.equalsIgnoreCase("0")) {
				i = 1821;
			}
		}
		textField.sendKeys("560");
		Thread.sleep(1000); // Wait for slider to update
		if (sliderValue.getAttribute("value").equals("0560")) {
			System.out.println("Slider successfully updated to 560 from text field.");
		}

		// Scroll down to CPT codes section
		WebElement cptSection = driver.findElement(By.xpath("(//p[contains(text(), 'CPT-')])[1]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cptSection);
		Thread.sleep(1000);

		WebElement cpt99091 = driver.findElement(By.xpath("//p[contains(text(), 'CPT-99091')]/parent::div//input"));
		cpt99091.click();
		Thread.sleep(1000);
		WebElement cpt99453 = driver.findElement(By.xpath("//p[contains(text(), 'CPT-99453')]/parent::div//input"));
		cpt99453.click();
		WebElement cpt99454 = driver.findElement(By.xpath("//p[contains(text(), 'CPT-99454')]/parent::div//input"));
		cpt99454.click();
		Thread.sleep(1000);
		WebElement cpt99474 = driver.findElement(By.xpath("//p[contains(text(), 'CPT-99474')]/parent::div//input"));
		cpt99474.click();
		Thread.sleep(1000);
		
		System.out.println("All CPT checkboxes selected.");
		Thread.sleep(4000);
		// Validate the total recurring reimbursement value
		WebElement totalReimbursement = driver
				.findElement(By.xpath("(//p[contains(text(), 'Total Recurring Reimbursement')])[1]//p"));
		String totalReimbursementVal = totalReimbursement.getText();
		System.out.println("Total Recurring Reimbursement: " + totalReimbursementVal);

		// comparing total reimbursement value with the given value in the requirement

		if (totalReimbursementVal.equalsIgnoreCase(expectedReimburementValue)) {
			System.out.println("Calculated Reimbursement amount matches expected Reimbursement amount");
		} else {
			System.out.println("Calculated Reimbursement amount not matches expected Reimbursement amount");
		}

		// Close the browser
		driver.quit();

	}

}
