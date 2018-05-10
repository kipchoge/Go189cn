package Go189cn;
import java.io.IOException;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class TestBuyingProcess {
	public static WebDriver driver;

	public static void main(String[] args) throws IOException, InterruptedException {
		System.setProperty("webdriver.chrome.driver","D:\\selenium\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("http://go189.cn/emall/web/product.do?productId=4028f4c84d8c0786014d8e588b730001");
		driver.findElement(By.className("btn-blue")).click();
		//输入收件人姓名
		driver.findElement(By.id("address-name-new")).sendKeys("测试");
		//从下拉框随机选择一个省份(减2是为了不选到香港和澳门)
		WebElement province = driver.findElement(By.id("address-province-new"));
		Select downList1 = new Select(province);
		int a = downList1.getOptions().size();
		Random rand1 = new Random();
		int i = rand1.nextInt(a-1)+1-2;
		downList1.selectByIndex(i);
		//从下拉框随机选择一个城市
		WebElement city = driver.findElement(By.id("address-city-new"));
		Select downList2 = new Select(city);
		int b = downList2.getOptions().size();
		Random rand2 = new Random();
		int j = rand2.nextInt(b-1)+1;
		downList2.selectByIndex(j);
		//从下拉框随机选择一个地区
		WebElement district = driver.findElement(By.id("address-district-new"));
		Select downList3 = new Select(district);
		int c = downList3.getOptions().size();
		Random rand3 = new Random();
		int k = rand3.nextInt(c-1)+1;
		downList3.selectByIndex(k);
		//输入详细地址
		driver.findElement(By.id("address-homeAddress-new")).sendKeys("这是测试地址");
		//输入手机号码
		driver.findElement(By.id("address-mobile-new")).sendKeys("18012345678");
		//点击提交订单
		driver.findElement(By.className("orange-btn")).click();
		
		
		
		
		
		
		
		/*
		int x = 5;
		while(x>0) {
			//goodsId.txt是存放待测商品URL的文本文档
			getUrl properties = new getUrl("C:\\Users\\Administrator\\Desktop\\emall\\goodsId.txt");
			Random rand = new Random();
			int j = rand.nextInt(168)+1;
			System.out.println(j);
			String url = properties.getPro(String.valueOf(j));
			driver.get(url);
			driver.findElement(By.className("btn-blue")).click();
			x--;
		}
		*/
	}
}
