package Go189cn;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import WangTing.TesseractOCR;

public class AutoLoginEmall {

	private static  ChromeDriver driver;

	public static void main(String[] args) throws IOException, InterruptedException {
		System.setProperty("webdriver.chrome.driver","D:\\selenium\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
	
		//打开翼猫商城登录页面
		driver.get("http://go189.cn/emall/emall/member/login.html");
		
		do {
		driver.findElement(By.id("input_username")).clear();
		driver.findElement(By.id("input_username")).sendKeys("17312813155");
		driver.findElement(By.id("input_userpwd")).clear();
		driver.findElement(By.id("input_userpwd")).sendKeys("371516");
		driver.findElement(By.id("_checkCodeImg")).click();
		Thread.sleep(2000);
		WebElement ele = driver.findElement(By.id("_checkCodeImg"));
		new TesseractOCR().getVerifyCodeJPG(driver,ele);
		String verifyCode = new TesseractOCR().recognizeText("D:\\Test\\Tesseract-OCR\\test.jpg");
		System.out.println(verifyCode);
		driver.findElement(By.id("input_code_1")).clear();
		Thread.sleep(500);
		driver.findElement(By.id("input_code_1")).sendKeys(verifyCode);
		Thread.sleep(500);
		driver.findElement(By.linkText("立即登录")).click();
		Thread.sleep(1000);
		}while(driver.getCurrentUrl().equals("http://go189.cn/emall/emall/member/login.html"));
	}

}
