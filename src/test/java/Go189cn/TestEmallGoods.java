package Go189cn;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.MultiOutputStream;

public class TestEmallGoods {
	public static WebDriver driver;

	/**
	 * 通过验证按钮是否存在，判断商品是否可以购买
	 */
	public static boolean doesWebElementExist(String selector) {

		try {
			driver.findElement(By.className(selector));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		// 以TXT文本输出测试结果
		PrintStream oldPrintStream = System.out;
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
		String date = time.format(new Date());
		String filename = "D:\\eclipse-workspace\\Go189cn\\src\\test\\resources\\" + date + "翼猫商sss品测试结果.txt";
		FileOutputStream bos = new FileOutputStream(filename);
		MultiOutputStream multi = new MultiOutputStream(new PrintStream(bos), oldPrintStream);
		System.setOut(new PrintStream(multi));
		// 打开浏览器并最大化
		System.setProperty("webdriver.chrome.driver", "D:\\eclipse-workspace\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		// 打印测试开始时间
		SimpleDateFormat time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = time1.format(new Date());
		System.out.println(date1 + " 开始测试");
		for (int i = 1; i < 169; i++) {
			// goodsId.txt是存放待测商品URL的文本文档
			Properties properties = new BaseOpreation()
					.getProperties("D:\\eclipse-workspace\\Go189cn\\src\\test\\resources\\goodsId.txt");
			String url = properties.getProperty(String.valueOf(i));
			driver.get(url);
			// 获取商品名称并输出结果
			String name = driver.findElement(By.className("name")).findElements(By.tagName("span")).get(0).getText();
			System.out.print(name + " ");
			// 获取商品价格并输出结果
			String price = driver.findElement(By.id("shangchengjia")).getText();
			System.out.print("【￥" + price + "】 ");
			// 判断商品是否可以购买并输出结果
			boolean a = doesWebElementExist("btn-blue");
			if (a) {
				System.out.println("可以购买");
			} else {
				System.out.println("无法购买！商品链接为：" + url);
			}
		}
		// 测试结束关闭浏览器
		driver.close();
		// 打印测试结束时间
		SimpleDateFormat time2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date2 = time2.format(new Date());
		System.out.println(date2 + " 测试结束");
	}
}