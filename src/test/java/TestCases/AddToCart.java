package TestCases;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class AddToCart {
	
	@Test
	public static void addToCart() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));

		driver.navigate().to("https://rahulshettyacademy.com/seleniumPractise/#/");
		driver.manage().window().maximize();

		String[] productArray = { "Cucumber", "Beans", "Carrot", "Brocolli" };
		String couponCode="rahulshettyacademy";

		addToCart(driver, productArray);
		applyCouponCode(driver,couponCode,wait);
		driver.findElement(By.xpath("//button[normalize-space()='Place Order']")).click();
		Select sel=new Select(driver.findElement(By.tagName("select")));
		sel.selectByValue("India");
		driver.findElement(By.xpath("//input[@type='checkbox']")).click();
		driver.findElement(By.xpath("//button[normalize-space()='Proceed']")).click();
		
		System.out.println(driver.findElement(By.xpath("//span[contains(text(),'Thank you, your order has been placed successfully')]")).getText());
		
		driver.close();

		

	}

	public static void addToCart(WebDriver driver, String[] productArray) {
		List<WebElement> products = driver.findElements(By.cssSelector("h4.product-name"));
		List<String> productsList = Arrays.asList(productArray);

		for (int i = 0; i < products.size(); i++) {

			String productname = products.get(i).getText();
			String productShortName = productname.split("-")[0].strip();

			int count = 0;
			if (productsList.contains(productShortName)) {
				System.out.println(productname);
				driver.findElements(By.cssSelector("div[class='product-action'] button")).get(i).click();
				count++;

				if (count == productArray.length) {
					break;
				}
			}
		}
	}
	
	public static void applyCouponCode(WebDriver driver,String couponCode,WebDriverWait wait)
	{
		driver.findElement(By.cssSelector("img[alt='Cart']")).click();
		driver.findElement(By.xpath("//button[normalize-space()='PROCEED TO CHECKOUT']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter promo code']")));
		driver.findElement(By.xpath("//input[@placeholder='Enter promo code']")).sendKeys(couponCode);
		driver.findElement(By.className("promoBtn")).click();
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".promoInfo")));
		System.out.println(driver.findElement(By.cssSelector(".promoInfo")).getText());
	}


}
