package Go189cn;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ScreenShotTest {
	WebDriver driver;
	
	public void InitDriver(){
		System.setProperty("webdriver.chrome.driver","D:\\eclipse-workspace\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		driver = new ChromeDriver(options);
		driver.get("https://www.imooc.com");
		driver.manage().window().maximize();
	}
	//@Test
	public void takeScreenShot() throws IOException {
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		String date = time.format(new Date());
		date = date + ".png";
		String curPath = "D:\\eclipse-workspace\\Go189cn\\src\\test\\resources\\screenShots";
		String screenPath = curPath + "\\" + date;
		System.out.println(date);
		File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screen, new File(screenPath));
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		ScreenShotTest login = new ScreenShotTest();
		login.InitDriver();
		Thread.sleep(2000);
		login.takeScreenShot();

	}
}