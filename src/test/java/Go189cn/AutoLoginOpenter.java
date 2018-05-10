package Go189cn;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import WangTing.TesseractOCR;

public class AutoLoginOpenter {

	private static  ChromeDriver driver;

	public static void main(String[] args) throws IOException, InterruptedException {
		System.setProperty("webdriver.chrome.driver","D:\\selenium\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
	
		//打开翼猫后台登录页面
		driver.get("http://go189.cn/emall/hdscOP/jsp/openter.jsp");
		
		do {
		driver.findElement(By.id("loginName")).clear();
		driver.findElement(By.id("loginName")).sendKeys("sandian");
		driver.findElement(By.id("loginPwd")).clear();
		driver.findElement(By.id("loginPwd")).sendKeys("xwtec@JSDX2016");
		driver.findElement(By.id("_checkCodeImg")).click();
		Thread.sleep(2000);
		WebElement ele = driver.findElement(By.id("_checkCodeImg"));
		new TesseractOCR().getVerifyCodeJPG(driver,ele);
		String verifyCode = new TesseractOCR().recognizeText("D:\\Test\\Tesseract-OCR\\test.jpg");
		System.out.println(verifyCode);
		driver.findElement(By.id("checkCode")).clear();
		Thread.sleep(500);
		driver.findElement(By.id("checkCode")).sendKeys(verifyCode);
		Thread.sleep(500);
		driver.findElementByXPath("//div[@class='login-action clearfix']/input").click();
		Thread.sleep(4000);
		}while(driver.getCurrentUrl().equals("http://go189.cn/emall/hdscOP/jsp/openter.jsp"));
		driver.findElementByXPath("//ul[@id='drop-menu']/li[4]/span[1]").click();
		Thread.sleep(500);
		driver.findElementByXPath("//ul[@id='drop-menu']/li[4]/ul/li[1]/span").click();
	}
	
	
}
