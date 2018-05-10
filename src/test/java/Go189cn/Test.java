package Go189cn;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class Test {
	

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver", "D:\\eclipse-workspace\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
	
		driver.get("http://go189.cn/emall/web/product.do?productId=03008624");
	}

}
