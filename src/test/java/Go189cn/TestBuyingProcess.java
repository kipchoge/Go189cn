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
		//�����ռ�������
		driver.findElement(By.id("address-name-new")).sendKeys("����");
		//�����������ѡ��һ��ʡ��(��2��Ϊ�˲�ѡ����ۺͰ���)
		WebElement province = driver.findElement(By.id("address-province-new"));
		Select downList1 = new Select(province);
		int a = downList1.getOptions().size();
		Random rand1 = new Random();
		int i = rand1.nextInt(a-1)+1-2;
		downList1.selectByIndex(i);
		//�����������ѡ��һ������
		WebElement city = driver.findElement(By.id("address-city-new"));
		Select downList2 = new Select(city);
		int b = downList2.getOptions().size();
		Random rand2 = new Random();
		int j = rand2.nextInt(b-1)+1;
		downList2.selectByIndex(j);
		//�����������ѡ��һ������
		WebElement district = driver.findElement(By.id("address-district-new"));
		Select downList3 = new Select(district);
		int c = downList3.getOptions().size();
		Random rand3 = new Random();
		int k = rand3.nextInt(c-1)+1;
		downList3.selectByIndex(k);
		//������ϸ��ַ
		driver.findElement(By.id("address-homeAddress-new")).sendKeys("���ǲ��Ե�ַ");
		//�����ֻ�����
		driver.findElement(By.id("address-mobile-new")).sendKeys("18012345678");
		//����ύ����
		driver.findElement(By.className("orange-btn")).click();
		
		
		
		
		
		
		
		/*
		int x = 5;
		while(x>0) {
			//goodsId.txt�Ǵ�Ŵ�����ƷURL���ı��ĵ�
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
