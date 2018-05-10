package Go189cn;
import java.io.IOException;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;



public class AutoLoginOpenter {

	public static ChromeDriver driver;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		AutoLoginOpenter autoLogin = new AutoLoginOpenter();
		System.setProperty("webdriver.chrome.driver","D:\\eclipse-workspace\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("http://go189.cn/emall/hdscOP/jsp/openter.jsp");
		autoLogin.login();
		autoLogin.releaseMateriel();
	}

	public void initDriver() {
		System.setProperty("webdriver.chrome.driver","D:\\eclipse-workspace\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
	}
	
	public void login() throws InterruptedException, IOException {
		do {
		driver.findElement(By.id("loginName")).clear();
		driver.findElement(By.id("loginName")).sendKeys("mafei");
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
	}
	
	public void releaseMateriel() throws InterruptedException {
		driver.findElementByXPath("//ul[@id='drop-menu']/li[4]/span[1]").click();
		Thread.sleep(500);
		driver.findElementByXPath("//ul[@id='drop-menu']/li[4]/ul/li[1]/span").click();
		driver.switchTo().frame(driver.findElementByXPath("//div[@class='info']/div[2]/div[2]/iframe"));
		WebElement classify = driver.findElement(By.xpath("//select[@id='fmaterialgroupid']"));		
		Select downlist1 = new Select(classify);
		int a = downlist1.getOptions().size();
		Random rand2 = new Random();
		int i = rand2.nextInt(a-1)+1;//为了不选到第一项，第一项是“请选择”
		downlist1.selectByIndex(i);
		driver.findElementByXPath("//form[@id='form']/div[2]/input[1]").click();
	}

	public void releaseGoods() throws InterruptedException {
		driver.findElementByXPath("//ul[@id='drop-menu']/li[4]/span[1]").click();
		Thread.sleep(500);
		driver.findElementByXPath("//ul[@id='drop-menu']/li[4]/ul/li[1]/span").click();
	}
	
	
	
	
}
