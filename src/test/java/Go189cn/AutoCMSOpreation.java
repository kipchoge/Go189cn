package Go189cn;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.beust.jcommander.Parameters;

public class AutoCMSOpreation {

	public WebDriver driver;

	public static void main(String[] args) throws Exception {
		AutoCMSOpreation aco = new AutoCMSOpreation();
		//aco.autoRR();//发布物料、销售品，审核物料、销售品流程跑一遍。
		//aco.autoOrdering();//自动下单，截取微信与支付宝支付与微信图片并保存
		//aco.autoDelivery();// 自动发货。
		//aco.checkOrder("80513328613000000");
	}

	/**
	 * 自动下单
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	private void autoOrdering() throws IOException, InterruptedException {
		this.initDriver();
		Properties properties = new BaseOpreation().getProperties("D:\\eclipse-workspace\\Go189cn\\src\\test\\resources\\emallGoodsId.txt");
		String url = properties.getProperty(String.valueOf("goodsID"));
		driver.get(url);
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
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='orders-action']/a[2]")).click();
		Thread.sleep(2000);
		WebElement ele1 = driver.findElement(By.xpath("//div[@id='J_qrPayArea']/div[3]/div[1]"));
		new TesseractOCR().getVerifyCodeJPG(driver, ele1, "支付宝二维码");
		Thread.sleep(1000);
		driver.navigate().back();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='payment-select']//tr[1]/td[2]//label[2]/span")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='orders-action']/a[2]")).click();
		Thread.sleep(1000);
		WebElement ele2 = driver.findElement(By.xpath("//img[@id='qrcode']"));
		new TesseractOCR().getVerifyCodeJPG(driver, ele2, "微信二维码");
		Thread.sleep(2000);
		driver.close();
	}
	
	/**
	 * 自动发货
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	private void autoDelivery() throws InterruptedException, IOException {
		this.initDriver();
		this.autoUserLogin();
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[2]/span[1]")).click();//点击订单管理（商户）
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[2]/ul/li[1]/span")).click();//点击“订单处理”
		Thread.sleep(2000);// 没有这个SLEEP就找不到iframe里面的元素！！！
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='info']/div[2]/div[2]/iframe")));
		driver.findElement(By.xpath("//div[@id='tabs']/ul/li[3]/a")).click();//点击待发货按钮
		Thread.sleep(3000);
		try {
			String fullText = driver.findElement(By.xpath("//div[@class='content01']//tr[2]/td[1]")).getText();//获取订单信息
			String orderNo = fullText.substring(8, 22);
			System.out.println(orderNo);
			Thread.sleep(500);
			driver.findElement(By.xpath("//table[@id='tableList']//tr[3]/td[6]/input[2]")).click();//点击去发货
			System.out.println(orderNo);
			Thread.sleep(1000);
			driver.findElement(By.xpath("//form[@name='orderForm']/table//tr[4]//input")).sendKeys(this.getDate());
			Thread.sleep(500);
			driver.findElement(By.xpath("//form[@name='orderForm']/table//tr[6]//input[1]")).click();
			Thread.sleep(2000);
			this.checkOrder(orderNo);
		}catch(Exception NoSuchElementException) {
			System.out.println("没有待发货的商品");
		}
		Thread.sleep(500);
	}
	/**
	 * 根据订单号查询是否发货并返回信息
	 * */
	public void checkOrder(String OrderNo) throws InterruptedException, IOException {
		this.initDriver();
		this.autoAdminLogin();
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[6]/span[1]")).click();//点击订单管理
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[6]/ul/li[3]")).click();//点击订单查询
		Thread.sleep(4000);
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='info']/div[2]/div[2]/iframe")));
		driver.findElement(By.xpath("//form[@id='mainForm']/fieldset/table//tr[1]/td[2]/input")).sendKeys(OrderNo);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//form[@id='mainForm']/fieldset/table//tr[5]//input[1]")).click();//搜索订单号
		Thread.sleep(3000);
		//try {
		driver.findElement(By.xpath("//table[@id='tableList']/tbody/tr[1]/td[7]/a")).click();//点击查看详情
		Thread.sleep(2000);
		try {
		String operation = "//form[@name='orderForm']/table//table[@id='tableList2']//tr[3]/td[4]";
		String isDelivery = driver.findElement(By.xpath(operation)).getText();
		System.out.println(isDelivery);
		if(isDelivery.equals("商户发货")) {
			System.out.println("发货成功");
		}
		}catch(Exception NoSuchElementException){
			System.out.println("发货信息未查到");
		}
	}
	/**
	 * 自动发布物料、销售品，然后审核物料、销售品
	 * */
	public void autoRR() throws Exception {
		this.initDriver();
		this.autoUserLogin();
		this.releaseMateriel();
		// this.releaseGoods();
		// this.removeGoods();
		this.editGoods();
		this.autoAdminLogin();
		this.reviewMateriel();
		this.reviewGoods();
		this.closeDriver();
		}

	/**
	 * 初始化浏览器
	 */
	@BeforeClass
	public void initDriver() {
		System.setProperty("webdriver.chrome.driver", "D:\\eclipse-workspace\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		System.out.println("浏览器初始化完成");
	}

	/**
	 * 通过输入用户名，密码自动登录
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@Test(priority = 1)
	public void autoUserLogin() throws InterruptedException, IOException {
		this.autoLogin("mafei", "xwtec@JSDX2016");
		System.out.println("用户登录成功");
	}
	
	/**
	 * 通过输入管理员名，密码自动登录
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@Test(priority = 4)
	public void autoAdminLogin() throws InterruptedException, IOException {
		this.autoLogin("admin", "xwtec@JSDX2016");
		System.out.println("管理员登录成功");
	}
	
	
	public void autoLogin(String user, String passWord) throws InterruptedException, IOException {
		do {
			driver.get("http://go189.cn/emall/hdscOP/jsp/openter.jsp");
			driver.findElement(By.id("loginName")).clear();
			driver.findElement(By.id("loginName")).sendKeys(user);
			driver.findElement(By.id("loginPwd")).clear();
			driver.findElement(By.id("loginPwd")).sendKeys(passWord);
			driver.findElement(By.id("_checkCodeImg")).click();
			Thread.sleep(2000);
			WebElement ele = driver.findElement(By.id("_checkCodeImg"));
			new TesseractOCR().getVerifyCodeJPG(driver, ele,"test");
			String verifyCode = new TesseractOCR().recognizeText("D:\\eclipse-workspace\\Go189cn\\src\\test\\resources\\test.jpg");
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
	@Test(priority = 2)
	public void releaseMateriel() throws InterruptedException {
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/ul/li[3]/span")).click();//点击“我的物料”
		Thread.sleep(2000);// 没有这个SLEEP就找不到iframe里面的元素！！！
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='info']/div[2]/div[2]/iframe")));
		driver.findElement(By.xpath("//body/div[2]/table//tr[2]/td[4]/a")).click();//点击编辑
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='mainul']/li[2]/input[1]")).clear();
		driver.findElement(By.xpath("//ul[@id='mainul']/li[2]/input[1]")).sendKeys("测试物料"+this.getDate());
		driver.findElement(By.xpath("//form[@id='form']/div[2]/input[1]")).click();//点击确认
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
		System.out.println("物料发布成功");
	}

	/**
	 * 发布销售品
	 */

	public void releaseGoods() throws Exception {
			driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
			Thread.sleep(500);
			driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/ul/li[2]/span")).click();
			Thread.sleep(4000);
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
		Thread.sleep(2000);
		// 上传三张商品图片
		String jpgPath = "C:\\Users\\Administrator\\Desktop\\kipsang.jpg";
		driver.findElement(By.xpath("//ul[@class='list_img']/li[1]/span[1]/input[1]")).sendKeys(jpgPath);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ul[@class='list_img']/li[2]/span[1]/input[1]")).sendKeys(jpgPath);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//ul[@class='list_img']/li[3]/span[1]/input[1]")).sendKeys(jpgPath);
		Thread.sleep(2000);
		// 点击提交审核
		driver.findElement(By.xpath("//div[@class='content']/div[2]/input[2]")).click();
		Thread.sleep(1000);
		Alert alt = driver.switchTo().alert();
		alt.accept();
		Thread.sleep(2000);
		driver.switchTo().defaultContent(); // 退出iframe，回到主界面
		Thread.sleep(1000);
		driver.findElement(By.xpath("//ul[@id='list']/li[2]/em")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
		System.out.println("销售品发布成功");
	}
	/**
	 * 编辑销售品
	 * @throws InterruptedException 
	 */
	@Test(priority = 3)
	public void editGoods() throws Exception{
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/ul/li[4]/span")).click();//点击“我的销售品”
		Thread.sleep(2000);// 没有这个SLEEP就找不到iframe里面的元素！！！
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='info']/div[2]/div[2]/iframe")));
		driver.findElement(By.xpath("//body/div[2]/table//tr[2]/td[7]/a[2]")).click();//点击编辑
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='mainul']/li[2]/input[1]")).clear();
		driver.findElement(By.xpath("//ul[@id='mainul']/li[2]/input[1]")).sendKeys("测试"+this.getDate()+"-1");
		driver.findElement(By.xpath("//form[@id='form']/div/div[6]/input[2]")).click();//点击保存
		Thread.sleep(500);
		Alert alt = driver.switchTo().alert();
		alt.accept();
		Thread.sleep(500);
		driver.switchTo().defaultContent(); // 退出iframe，回到主界面
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='list']/li[2]/em")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
		Thread.sleep(500);
		System.out.println("销售品编辑成功");
	}
	/**
	 * 下架销售品
	 * @throws InterruptedException 
	 */

	public void removeGoods() throws InterruptedException {
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/ul/li[4]/span")).click();//点击“我的销售品”
		Thread.sleep(2000);// 没有这个SLEEP就找不到iframe里面的元素！！！
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='info']/div[2]/div[2]/iframe")));
		try {
		driver.findElement(By.xpath("//table[@id='tableList']//tr[2]/td[7]/a[3]")).click();//点击下架按钮
		Thread.sleep(500);
		Alert alt = driver.switchTo().alert();
		alt.accept();
		}catch (Exception NoSuchElementException) {
			System.out.println("要下架的商品不存在");
		}
		driver.switchTo().defaultContent(); // 退出iframe，回到主界面
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='list']/li[2]/em")).click();
		Thread.sleep(500);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[4]/span[1]")).click();
		System.out.println("销售品下架成功");
	}
	
	/**
	 * 审核物料
	 */
	@Test(priority = 5)
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
		System.out.println("物料审核成功");
	}

	/**
	 * 审核销售品
	 * @throws FileNotFoundException 
	 */
	@Test(priority = 6)
	public void reviewGoods() throws InterruptedException, FileNotFoundException {
		//输出审核通过的销售品ID到TXT文本
		PrintStream oldPrintStream = System.out;
		String filename = "D:\\eclipse-workspace\\Go189cn\\src\\test\\resources\\emallGoodsId.txt";
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
		String isNew = driver.findElement(By.xpath("//body/div[2]/table//tr[2]/td[4]")).getText();
		if(isNew.equals("申请新增")) {
		System.out.println("goodsID=http://go189.cn/emall/web/product.do?productId="+goodsID);
		}
		if(isNew.equals("申请修改")) {
			System.out.println("goodsID=http://go189.cn/emall/web/product.do?productId="+goodsID);
			}
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
		Thread.sleep(1000);
		driver.switchTo().defaultContent(); // 退出iframe，回到主界面
		Thread.sleep(1000);
		driver.findElement(By.xpath("//ul[@id='list']/li[2]/em")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//ul[@id='drop-menu']/li[15]/span[1]")).click();
		System.out.println("销售品审核成功");
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
	 * 关闭浏览器
	 */
	@AfterClass
	public void closeDriver() {
		driver.close();
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
