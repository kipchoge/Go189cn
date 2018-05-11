package Go189cn;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.MultiOutputStream;
import org.openqa.selenium.support.ui.Select;

public class AutoLoginOpenter {

	public WebDriver driver;

	public static void main(String[] args) throws Exception {
		AutoLoginOpenter autoLogin = new AutoLoginOpenter();
		autoLogin.initDriver();
//		autoLogin.autoLogin("mafei", "xwtec@JSDX2016");
//		autoLogin.releaseMateriel();
//		autoLogin.releaseGoods();
//		autoLogin.initDriver();
		autoLogin.autoLogin("admin", "xwtec@JSDX2016");
//		autoLogin.reviewMateriel();
		autoLogin.reviewGoods();
	}

	/**
	 * ��ʼ�������
	 */
	public void initDriver() {
		System.setProperty("webdriver.chrome.driver", "D:\\eclipse-workspace\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("http://go189.cn/emall/hdscOP/jsp/openter.jsp");
	}

	/**
	 * ͨ�������û����������Զ���¼
	 */
	public void autoLogin(String user, String passWord) throws InterruptedException, IOException {
		do {
			driver.findElement(By.id("loginName")).clear();
			driver.findElement(By.id("loginName")).sendKeys(user);
			driver.findElement(By.id("loginPwd")).clear();
			driver.findElement(By.id("loginPwd")).sendKeys(passWord);
			driver.findElement(By.id("_checkCodeImg")).click();
			Thread.sleep(2000);
			WebElement ele = driver.findElement(By.id("_checkCodeImg"));
			new TesseractOCR().getVerifyCodeJPG(driver, ele);
			String verifyCode = new TesseractOCR().recognizeText("D:\\Test\\Tesseract-OCR\\test.jpg");
			System.out.println(verifyCode);
			driver.findElement(By.id("checkCode")).clear();
			Thread.sleep(500);
			driver.findElement(By.id("checkCode")).sendKeys(verifyCode);
			Thread.sleep(500);
			driver.findElement(By.xpath("//div[@class='login-action clearfix']/input")).click();
			Thread.sleep(4000);
		} while (driver.getCurrentUrl().equals("http://go189.cn/emall/hdscOP/jsp/openter.jsp"));
	}

	/**
	 * ��������
	 */
	public void releaseMateriel() throws InterruptedException {
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/ul/li[3]/span")).click();//������ҵ����ϡ�
		Thread.sleep(2000);// û�����SLEEP���Ҳ���iframe�����Ԫ�أ�����
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='info']/div[2]/div[2]/iframe")));
		driver.findElement(By.xpath("//body/div[2]/table//tr[2]/td[1]"));
		driver.findElement(By.xpath("//body/div[2]/table//tr[2]/td[4]/a")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='mainul']/li[2]/input[1]")).clear();
		driver.findElement(By.xpath("//ul[@id='mainul']/li[2]/input[1]")).sendKeys("��������"+this.getDate());
		driver.findElement(By.xpath("//form[@id='form']/div[2]/input[1]")).click();
		Thread.sleep(500);
		Alert alt = driver.switchTo().alert();
		alt.accept();
		driver.switchTo().defaultContent(); // �˳�iframe���ص�������
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='list']/li[2]/em")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
		/*���������޷�ɾ�����������Ϸ������˺����ۣ����޸�Ϊÿ���޸���һ���������Ϣ
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/ul/li[1]/span")).click();
		Thread.sleep(2000);// û�����SLEEP���Ҳ���iframe�����Ԫ�أ�����
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='info']/div[2]/div[2]/iframe")));
		WebElement classify = driver.findElement(By.id("fmaterialgroupid"));
		Select downlist1 = new Select(classify);
		downlist1.selectByIndex(2);
		driver.findElement(By.xpath("//form[@id='form']/div[2]/input[1]")).click();
		Thread.sleep(500);
		// ��д������Ϣ
		driver.findElement(By.id("fname")).sendKeys("��������");
		driver.findElement(By.id("fshortname")).sendKeys("����");
		driver.findElement(By.id("fdescription")).sendKeys("����ר�ã�����ѡ��!");
		WebElement color = driver.findElement(By.id("fidones"));
		Select downlist2 = new Select(color);
		int a = downlist2.getOptions().size();
		Random rand1 = new Random();
		int i = rand1.nextInt(a - 1) + 1;// Ϊ�˲�ѡ����һ���һ���ǡ���ѡ��
		downlist2.selectByIndex(i);
		driver.findElement(By.xpath("//input[@value='ȷ��']")).click();
		Alert alt = driver.switchTo().alert();
		alt.accept();
		driver.switchTo().defaultContent(); // �˳�iframe���ص�������
		Thread.sleep(500);*/
	}

	/**
	 * ��������Ʒ
	 */
	public void releaseGoods() throws Exception {
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/ul/li[2]/span")).click();
		Thread.sleep(500);
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='info']/div[2]/div[2]/iframe")));
		driver.findElement(By.id("cfname")).sendKeys("����" + this.getDate());
		driver.findElement(By.id("cfprice")).sendKeys("1");
		driver.findElement(By.id("cfpriceend")).sendKeys("2");
		driver.findElement(By.id("cfsalepoint")).sendKeys("������Ʒ�������򣡣���");
		String currentWindow = driver.getWindowHandle();// ��ȡ��ǰ���ھ��
		driver.findElement(By.id("addMater1")).click();
		this.checkNewPage();
		driver.findElement(By.id("materialName")).sendKeys("��������");
		Thread.sleep(200);
		driver.findElement(By.xpath("//form[@id='mainForm']//td[2]/input[1]")).click();// �������
		Thread.sleep(200);
		driver.findElement(By.xpath("//table[@id='tableList']//tr[2]/td[1]")).click();// ���ѡ��
		driver.findElement(By.xpath("//div[@class='content01']/input")).sendKeys(Keys.SPACE);// ȷ����ѡ
		Thread.sleep(200);
		driver.switchTo().window(currentWindow);// �ص�ԭ��ҳ��
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='info']/div[2]/div[2]/iframe")));// ����iframe
		driver.findElement(By.xpath("//select[@id='leftselect1']/option")).click();// ȷ����ѡ
		driver.findElement(By.xpath("//div[@id='materSelect1']/input[2]")).click();// ��ӵ��Ҳ����
		Thread.sleep(200);
		driver.findElement(By.xpath("//div[@id='materSelectSure1']/input[2]")).click();// ȷ����ѡ
		Thread.sleep(200);
		driver.findElement(By.xpath("//table[@id='matettablelist1']//tr[2]/td[9]/a[2]")).click();// �༭
		this.checkNewPage();
		driver.findElement(By.id("CFCanSaleQty")).sendKeys("100");
		driver.findElement(By.id("cfprice")).sendKeys("0.01");
		driver.findElement(By.xpath("//form[@id='form']/div/input[2]")).click();
		driver.switchTo().window(currentWindow);// �ص�ԭ��ҳ��
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='info']/div[2]/div[2]/iframe")));// ����iframe
		Thread.sleep(200);
		driver.findElement(By.xpath("//form[@id='form']//input[@value='��һ��']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@id='subCategoryId0']/ul[2]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//div[@id='subCategoryId1']/ul[5]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//div[@id='subCategoryId2']/ul[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//form[@id='form']/div/div/input[3]")).click();
		Thread.sleep(500);
		// �Ѿ���������ҳ��......
		driver.findElement(By.xpath("//input[@id='quantity']")).clear();
		driver.findElement(By.xpath("//ul[@id='mainul']/li[10]/input")).sendKeys("100");
		WebElement channel = driver.findElement(By.xpath("//select[@id='channelId']"));
		Select downlist3 = new Select(channel);
		downlist3.selectByIndex(1);
		// �ϴ�������ƷͼƬ
		String jpgPath = "C:\\Users\\Administrator\\Desktop\\kipsang.jpg";
		driver.findElement(By.xpath("//ul[@class='list_img']/li[1]/span[1]/input[1]")).sendKeys(jpgPath);
		driver.findElement(By.xpath("//ul[@class='list_img']/li[2]/span[1]/input[1]")).sendKeys(jpgPath);
		driver.findElement(By.xpath("//ul[@class='list_img']/li[3]/span[1]/input[1]")).sendKeys(jpgPath);
		// ����ύ���
		driver.findElement(By.xpath("//div[@class='content']/div[2]/input[2]")).click();
		Thread.sleep(500);
		Alert alt = driver.switchTo().alert();
		alt.accept();
		driver.switchTo().defaultContent(); // �˳�iframe���ص�������
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='list']/li[2]/em")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
	}

	/**
	 * �������
	 */
	public void reviewMateriel() throws InterruptedException {
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[15]/span[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[15]/ul/li[4]/span")).click();
		Thread.sleep(2000);// û�����SLEEP���Ҳ���iframe�����Ԫ�أ�����
		driver.switchTo()
				.frame(driver.findElement(By.xpath("//div[@class='info']//div[@class='right']/div[2]/iframe")));
		Thread.sleep(500);
		driver.findElement(By.xpath("//form[@id='mainForm']/table//tr/td[1]/input[1]"))
				.sendKeys("��������" + this.getDate());
		driver.findElement(By.xpath("//form[@id='mainForm']/table//tr/td[2]/input[1]")).click();
		Thread.sleep(2000);
		try {
			driver.findElement(By.xpath("//body/div[2]//table[@id='tableList']//tr[2]/td[6]/a")).click();
			Thread.sleep(2500);
			driver.findElement(By.xpath("//form[@id='form']/div[2]/input[2]")).click();
			Alert alt = driver.switchTo().alert();
			alt.accept();
		} catch (Exception NoSuchElementException) {
			System.out.println("Ҫ��˵����ϲ�����");
		}
		driver.switchTo().defaultContent(); // �˳�iframe���ص�������
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='list']/li[2]/em")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[15]/span[1]")).click();
	}

	/**
	 * �������Ʒ
	 * @throws FileNotFoundException 
	 */
	public void reviewGoods() throws InterruptedException, FileNotFoundException {
		//������ͨ��������ƷID��TXT�ı�
		PrintStream oldPrintStream = System.out;
		String filename = "D:\\eclipse\\eclipse-workspace\\Go189cn\\src\\test\\resources\\" + this.getDate() + "emallGoodsId.txt";
		FileOutputStream bos = new FileOutputStream(filename);
		MultiOutputStream multi = new MultiOutputStream(new PrintStream(bos), oldPrintStream);
		System.setOut(new PrintStream(multi));
		//��ʼ���в���
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[15]/span[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[15]/ul/li[6]/span")).click();
		Thread.sleep(3000);// û�����SLEEP���Ҳ���iframe�����Ԫ�أ�����
		driver.switchTo()
				.frame(driver.findElement(By.xpath("//div[@class='info']//div[@class='right']/div[2]/iframe")));
		Thread.sleep(500);
		driver.findElement(By.xpath("//form[@id='mainForm']/table//tr[3]/td[1]/input[1]")).sendKeys("����Ƽ�");
		Thread.sleep(500);
		driver.findElement(By.xpath("//form[@id='mainForm']/table//tr[5]/td[1]/input[1]")).click();// �������
		Thread.sleep(2000);
		try {
		String goodsID = driver.findElement(By.xpath("//body/div[2]/table//tr[2]/td[1]")).getText();
		System.out.println("goodsID="+goodsID);
		driver.findElement(By.xpath("//body/div[2]//table[@id='tableList']//tr[2]/td[6]/a")).click();// �������
		Thread.sleep(500);
		driver.findElement(By.xpath("//form[@id='form']/div/div[6]/input[2]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//form[@id='form']/../div[2]/input[3]")).click();
		Alert alt = driver.switchTo().alert();
		alt.accept();
		}catch (Exception NoSuchElementException) {
			System.out.println("Ҫ��˵���Ʒ������");
		}
		Thread.sleep(500);
		driver.switchTo().defaultContent(); // �˳�iframe���ص�������
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='list']/li[2]/em")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[15]/span[1]")).click();
	}

	/**
	 * ��ʽ�ȴ���һ�㲻��
	 */
	public void waitForElement() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);// ��ʽ�ȴ�5����
	}

	/**
	 * ��ת����ҳ��
	 */
	public void checkNewPage() throws Exception {
		String currentWindow = driver.getWindowHandle();// ��ȡ��ǰ���ھ��
		Set<String> handles = driver.getWindowHandles();// ��ȡ���д��ھ��
		Iterator<String> it = handles.iterator();
		while (it.hasNext()) {
			if (currentWindow == it.next()) {
				continue;
			}
			driver.switchTo().window(it.next());// �л����´���
			//WebDriver window = driver.switchTo().window(it.next());// �л����´���
			// driver.switchTo().window(currentWindow);// �ص�ԭ��ҳ��
		}
	}
	
	/**
	 * �������
	 * @return 
	 */
	public String getDate() {
		SimpleDateFormat time = new SimpleDateFormat("MMdd");
		String date = time.format(new Date());
		return date;
	}
	
	/*���*/
}
