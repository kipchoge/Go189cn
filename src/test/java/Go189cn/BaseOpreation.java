package Go189cn;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseOpreation {
	Properties properties = new Properties();
	
	public void initDriver(ChromeDriver driver) {
		System.setProperty("webdriver.chrome.driver", "D:\\Test\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
	}

	public Properties getProperties(String filePath) throws IOException {
		InputStream inputStream = new FileInputStream(filePath);
		BufferedInputStream in = new BufferedInputStream(inputStream);
		properties.load(in);
		return properties;
	}

	public String getProperty(String i) {
		if (properties.containsKey(i)) {
			String usename = properties.getProperty(i);
			return usename;
		} else {
			System.out.println("你获取的key值不对!!!");
			return null;
		}
	}

}
