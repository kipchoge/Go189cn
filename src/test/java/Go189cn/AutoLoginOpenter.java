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
	 * 初始化浏览器
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
	 * 通过输入用户名，密码自动登录
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
	 * 发布物料
	 */
	public void releaseMateriel() throws InterruptedException {
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/ul/li[3]/span")).click();//点击“我的物料”
		Thread.sleep(2000);// 没有这个SLEEP就找不到iframe里面的元素！！！
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='info']/div[2]/div[2]/iframe")));
		driver.findElement(By.xpath("//body/div[2]/table//tr[2]/td[1]"));
		driver.findElement(By.xpath("//body/div[2]/table//tr[2]/td[4]/a")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='mainul']/li[2]/input[1]")).clear();
		driver.findElement(By.xpath("//ul[@id='mainul']/li[2]/input[1]")).sendKeys("测试物料"+this.getDate());
		driver.findElement(By.xpath("//form[@id='form']/div[2]/input[1]")).click();
		Thread.sleep(500);
		Alert alt = driver.switchTo().alert();
		alt.accept();
		driver.switchTo().defaultContent(); // 退出iframe，回到主界面
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='list']/li[2]/em")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
		/*由于物料无法删除，导致物料发布多了后会积累，后修改为每次修改上一天的物料信息
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/ul/li[1]/span")).click();
		Thread.sleep(2000);// 没有这个SLEEP就找不到iframe里面的元素！！！
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='info']/div[2]/div[2]/iframe")));
		WebElement classify = driver.findElement(By.id("fmaterialgroupid"));
		Select downlist1 = new Select(classify);
		downlist1.selectByIndex(2);
		driver.findElement(By.xpath("//form[@id='form']/div[2]/input[1]")).click();
		Thread.sleep(500);
		// 填写物料信息
		driver.findElement(By.id("fname")).sendKeys("测试物料");
		driver.findElement(By.id("fshortname")).sendKeys("测试");
		driver.findElement(By.id("fdescription")).sendKeys("测试专用，请勿选择!");
		WebElement color = driver.findElement(By.id("fidones"));
		Select downlist2 = new Select(color);
		int a = downlist2.getOptions().size();
		Random rand1 = new Random();
		int i = rand1.nextInt(a - 1) + 1;// 为了不选到第一项，第一项是“请选择”
		downlist2.selectByIndex(i);
		driver.findElement(By.xpath("//input[@value='确认']")).click();
		Alert alt = driver.switchTo().alert();
		alt.accept();
		driver.switchTo().defaultContent(); // 退出iframe，回到主界面
		Thread.sleep(500);*/
	}

	/**
	 * 发布销售品
	 */
	public void releaseGoods() throws Exception {
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/ul/li[2]/span")).click();
		Thread.sleep(500);
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='info']/div[2]/div[2]/iframe")));
		driver.findElement(By.id("cfname")).sendKeys("测试" + this.getDate());
		driver.findElement(By.id("cfprice")).sendKeys("1");
		driver.findElement(By.id("cfpriceend")).sendKeys("2");
		driver.findElement(By.id("cfsalepoint")).sendKeys("测试商品，请勿购买！！！");
		String currentWindow = driver.getWindowHandle();// 获取当前窗口句柄
		driver.findElement(By.id("addMater1")).click();
		this.checkNewPage();
		driver.findElement(By.id("materialName")).sendKeys("测试物料");
		Thread.sleep(200);
		driver.findElement(By.xpath("//form[@id='mainForm']//td[2]/input[1]")).click();// 点击搜索
		Thread.sleep(200);
		driver.findElement(By.xpath("//table[@id='tableList']//tr[2]/td[1]")).click();// 点击选项
		driver.findElement(By.xpath("//div[@class='content01']/input")).sendKeys(Keys.SPACE);// 确认所选
		Thread.sleep(200);
		driver.switchTo().window(currentWindow);// 回到原来页面
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='info']/div[2]/div[2]/iframe")));// 进入iframe
		driver.findElement(By.xpath("//select[@id='leftselect1']/option")).click();// 确认所选
		driver.findElement(By.xpath("//div[@id='materSelect1']/input[2]")).click();// 添加到右侧框中
		Thread.sleep(200);
		driver.findElement(By.xpath("//div[@id='materSelectSure1']/input[2]")).click();// 确认所选
		Thread.sleep(200);
		driver.findElement(By.xpath("//table[@id='matettablelist1']//tr[2]/td[9]/a[2]")).click();// 编辑
		this.checkNewPage();
		driver.findElement(By.id("CFCanSaleQty")).sendKeys("100");
		driver.findElement(By.id("cfprice")).sendKeys("0.01");
		driver.findElement(By.xpath("//form[@id='form']/div/input[2]")).click();
		driver.switchTo().window(currentWindow);// 回到原来页面
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='info']/div[2]/div[2]/iframe")));// 进入iframe
		Thread.sleep(200);
		driver.findElement(By.xpath("//form[@id='form']//input[@value='下一步']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@id='subCategoryId0']/ul[2]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//div[@id='subCategoryId1']/ul[5]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//div[@id='subCategoryId2']/ul[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//form[@id='form']/div/div/input[3]")).click();
		Thread.sleep(500);
		// 已经到了最后的页面......
		driver.findElement(By.xpath("//input[@id='quantity']")).clear();
		driver.findElement(By.xpath("//ul[@id='mainul']/li[10]/input")).sendKeys("100");
		WebElement channel = driver.findElement(By.xpath("//select[@id='channelId']"));
		Select downlist3 = new Select(channel);
		downlist3.selectByIndex(1);
		// 上传三张商品图片
		String jpgPath = "C:\\Users\\Administrator\\Desktop\\kipsang.jpg";
		driver.findElement(By.xpath("//ul[@class='list_img']/li[1]/span[1]/input[1]")).sendKeys(jpgPath);
		driver.findElement(By.xpath("//ul[@class='list_img']/li[2]/span[1]/input[1]")).sendKeys(jpgPath);
		driver.findElement(By.xpath("//ul[@class='list_img']/li[3]/span[1]/input[1]")).sendKeys(jpgPath);
		// 点击提交审核
		driver.findElement(By.xpath("//div[@class='content']/div[2]/input[2]")).click();
		Thread.sleep(500);
		Alert alt = driver.switchTo().alert();
		alt.accept();
		driver.switchTo().defaultContent(); // 退出iframe，回到主界面
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='list']/li[2]/em")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
	}

	/**
	 * 审核物料
	 */
	public void reviewMateriel() throws InterruptedException {
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[15]/span[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[15]/ul/li[4]/span")).click();
		Thread.sleep(2000);// 没有这个SLEEP就找不到iframe里面的元素！！！
		driver.switchTo()
				.frame(driver.findElement(By.xpath("//div[@class='info']//div[@class='right']/div[2]/iframe")));
		Thread.sleep(500);
		driver.findElement(By.xpath("//form[@id='mainForm']/table//tr/td[1]/input[1]"))
				.sendKeys("测试物料" + this.getDate());
		driver.findElement(By.xpath("//form[@id='mainForm']/table//tr/td[2]/input[1]")).click();
		Thread.sleep(2000);
		try {
			driver.findElement(By.xpath("//body/div[2]//table[@id='tableList']//tr[2]/td[6]/a")).click();
			Thread.sleep(2500);
			driver.findElement(By.xpath("//form[@id='form']/div[2]/input[2]")).click();
			Alert alt = driver.switchTo().alert();
			alt.accept();
		} catch (Exception NoSuchElementException) {
			System.out.println("要审核的物料不存在");
		}
		driver.switchTo().defaultContent(); // 退出iframe，回到主界面
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='list']/li[2]/em")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[15]/span[1]")).click();
	}

	/**
	 * 审核销售品
	 * @throws FileNotFoundException 
	 */
	public void reviewGoods() throws InterruptedException, FileNotFoundException {
		//输出审核通过的销售品ID到TXT文本
		PrintStream oldPrintStream = System.out;
		String filename = "D:\\eclipse\\eclipse-workspace\\Go189cn\\src\\test\\resources\\" + this.getDate() + "emallGoodsId.txt";
		FileOutputStream bos = new FileOutputStream(filename);
		MultiOutputStream multi = new MultiOutputStream(new PrintStream(bos), oldPrintStream);
		System.setOut(new PrintStream(multi));
		//开始进行操作
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[15]/span[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[15]/ul/li[6]/span")).click();
		Thread.sleep(3000);// 没有这个SLEEP就找不到iframe里面的元素！！！
		driver.switchTo()
				.frame(driver.findElement(By.xpath("//div[@class='info']//div[@class='right']/div[2]/iframe")));
		Thread.sleep(500);
		driver.findElement(By.xpath("//form[@id='mainForm']/table//tr[3]/td[1]/input[1]")).sendKeys("三体科技");
		Thread.sleep(500);
		driver.findElement(By.xpath("//form[@id='mainForm']/table//tr[5]/td[1]/input[1]")).click();// 点击搜索
		Thread.sleep(2000);
		try {
		String goodsID = driver.findElement(By.xpath("//body/div[2]/table//tr[2]/td[1]")).getText();
		System.out.println("goodsID="+goodsID);
		driver.findElement(By.xpath("//body/div[2]//table[@id='tableList']//tr[2]/td[6]/a")).click();// 点击详情
		Thread.sleep(500);
		driver.findElement(By.xpath("//form[@id='form']/div/div[6]/input[2]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//form[@id='form']/../div[2]/input[3]")).click();
		Alert alt = driver.switchTo().alert();
		alt.accept();
		}catch (Exception NoSuchElementException) {
			System.out.println("要审核的商品不存在");
		}
		Thread.sleep(500);
		driver.switchTo().defaultContent(); // 退出iframe，回到主界面
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='list']/li[2]/em")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[15]/span[1]")).click();
	}

	/**
	 * 隐式等待，一般不用
	 */
	public void waitForElement() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);// 隐式等待5秒钟
	}

	/**
	 * 跳转到新页面
	 */
	public void checkNewPage() throws Exception {
		String currentWindow = driver.getWindowHandle();// 获取当前窗口句柄
		Set<String> handles = driver.getWindowHandles();// 获取所有窗口句柄
		Iterator<String> it = handles.iterator();
		while (it.hasNext()) {
			if (currentWindow == it.next()) {
				continue;
			}
			driver.switchTo().window(it.next());// 切换到新窗口
			//WebDriver window = driver.switchTo().window(it.next());// 切换到新窗口
			// driver.switchTo().window(currentWindow);// 回到原来页面
		}
	}
	
	/**
	 * 获得日期
	 * @return 
	 */
	public String getDate() {
		SimpleDateFormat time = new SimpleDateFormat("MMdd");
		String date = time.format(new Date());
		return date;
	}
	
	/*获得*/
}
