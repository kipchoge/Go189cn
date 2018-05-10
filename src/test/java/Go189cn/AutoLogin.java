package Go189cn;


import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AutoLogin {

	private static  ChromeDriver driver;

	public static void main(String[] args) throws IOException, InterruptedException {
		System.setProperty("webdriver.chrome.driver","D:\\eclipse-workspace\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		/*µÁ–≈Õ¯Ã¸µ«¬º
		driver.get("http://js.189.cn/nservice/login/toLogin?favurl=http://js.189.cn/index");
		driver.findElementByXPath("//ul[@id='menu1']/li[1]").click();
		driver.findElementByXPath("//input[@id='cellphone']").sendKeys("17312813155");
		driver.findElementByXPath("//div[@class='login_con_line select']/input").sendKeys("371516");
		WebElement ele = driver.findElementByXPath("//p[@class='login_con_line']/span");
		*/
		//“Ì√®…Ã≥«µ«¬º
		driver.get("http://go189.cn/emall/emall/member/login.html");
		driver.findElement(By.id("input_username")).sendKeys("17312813155");
		driver.findElement(By.id("input_userpwd")).sendKeys("371516");
		WebElement ele = driver.findElement(By.id("_checkCodeImg"));
	
		new TesseractOCR().getVerifyCodeJPG(driver,ele);
		String verifyCode = new TesseractOCR().recognizeText("D:\\Test\\Tesseract-OCR\\test.jpg");
		System.out.println(verifyCode);
		Thread.sleep(1000);
		
		/*Õ¯Ã¸
		driver.findElementByXPath("//input[@name='validateCodeNumber']").sendKeys(verifyCode);
		Thread.sleep(1000);
		driver.findElement(By.id("login_byPhone")).click();
		*/
		//“Ì√®
		
	}

}
