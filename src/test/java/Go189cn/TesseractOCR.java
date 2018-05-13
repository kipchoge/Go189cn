package Go189cn;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TesseractOCR {

	public void getVerifyCodeJPG(WebDriver driver, WebElement ele, String jpgName) throws IOException {

		// Get entire page screenshot
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage fullImg = ImageIO.read(screenshot);

		// Get the location of element on the page
		Point point = ele.getLocation();

		// Get width and height of the element
		int eleWidth = ele.getSize().getWidth();
		int eleHeight = ele.getSize().getHeight();

		// Crop the entire page screenshot to get only element screenshot
		BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
		ImageIO.write(eleScreenshot, "png", screenshot);

		// Copy the element screenshot to disk
		File screenshotLocation = new File("D:\\eclipse-workspace\\Go189cn\\src\\test\\resources\\"+jpgName+".jpg");
		FileUtils.copyFile(screenshot, screenshotLocation);
	}

	public String recognizeText(String imageFile) {
		String result = ""; // 保存读取到的识别内容并返回
		String tesseractExe = "D:\\Test\\Tesseract-OCR\\tesseract.exe";
		String output = "D:\\Test\\Tesseract-OCR\\result";
		// 根据选项组装执行命令的字符串，注意参数之间需要加空格分隔开
		String command = tesseractExe + " " + imageFile + " " + output;
		// System.out.println(command); // 对输出命令进行确认，成功后该行代码 可删除
		try {
			// 使用Process来获取执行命令的结果，并对其结果进行判断
			Process process = Runtime.getRuntime().exec(command);
			int exeCode = process.waitFor();
			// 执行的结果代码如果为0，表示命令执行成功
			if (exeCode == 0) {
				// 读取到输出文件中的内容，并将其赋值给变量result
				InputStream fis = new FileInputStream(output + ".txt");
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				result = br.readLine();
				br.close();
			} else {
				System.out.println("本次识别操作命令未正常执行.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String Str = result.replaceAll(" ", "");
		return Str;
	}
}
