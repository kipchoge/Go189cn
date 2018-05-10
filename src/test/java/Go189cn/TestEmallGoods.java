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
	 * ͨ����֤��ť�Ƿ���ڣ��ж���Ʒ�Ƿ���Թ���
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
		// ��TXT�ı�������Խ��
		PrintStream oldPrintStream = System.out;
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
		String date = time.format(new Date());
		String filename = "D:\\eclipse-workspace\\Go189cn\\src\\test\\resources\\" + date + "��è��sssƷ���Խ��.txt";
		FileOutputStream bos = new FileOutputStream(filename);
		MultiOutputStream multi = new MultiOutputStream(new PrintStream(bos), oldPrintStream);
		System.setOut(new PrintStream(multi));
		// ������������
		System.setProperty("webdriver.chrome.driver", "D:\\eclipse-workspace\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		// ��ӡ���Կ�ʼʱ��
		SimpleDateFormat time1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = time1.format(new Date());
		System.out.println(date1 + " ��ʼ����");
		for (int i = 1; i < 169; i++) {
			// goodsId.txt�Ǵ�Ŵ�����ƷURL���ı��ĵ�
			Properties properties = new BaseOpreation()
					.getProperties("D:\\eclipse-workspace\\Go189cn\\src\\test\\resources\\goodsId.txt");
			String url = properties.getProperty(String.valueOf(i));
			driver.get(url);
			// ��ȡ��Ʒ���Ʋ�������
			String name = driver.findElement(By.className("name")).findElements(By.tagName("span")).get(0).getText();
			System.out.print(name + " ");
			// ��ȡ��Ʒ�۸�������
			String price = driver.findElement(By.id("shangchengjia")).getText();
			System.out.print("����" + price + "�� ");
			// �ж���Ʒ�Ƿ���Թ���������
			boolean a = doesWebElementExist("btn-blue");
			if (a) {
				System.out.println("���Թ���");
			} else {
				System.out.println("�޷�������Ʒ����Ϊ��" + url);
			}
		}
		// ���Խ����ر������
		driver.close();
		// ��ӡ���Խ���ʱ��
		SimpleDateFormat time2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date2 = time2.format(new Date());
		System.out.println(date2 + " ���Խ���");
	}
}