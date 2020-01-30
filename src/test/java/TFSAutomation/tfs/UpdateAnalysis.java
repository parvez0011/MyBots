package TFSAutomation.tfs;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UpdateAnalysis {
	
	static WebDriver driver;

	@Test
	public void main1() throws InterruptedException, FileNotFoundException, IOException {
		
		Map<String, String> config=Session._getSessionConfig();
		String Release=config.get("Release");
		String Sprint=config.get("Sprint");
		String AppUrl=config.get("AppUrl");
		String uname=config.get("UserName");
		String pword=config.get("Password");
				
//		String Release="Release 2.1";
//		String Sprint="Sprint 29";
		System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\selenium-drivers\\chromedriver.exe"); 
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();	
		driver.get(AppUrl);
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(uname.trim());
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Thread.sleep(10000);
		
		driver.findElement(By.id("passwordInput")).sendKeys(pword.trim());
		Thread.sleep(5000);
		driver.findElement(By.xpath("//span[@id='submitButton']")).click();
		Thread.sleep(20000);
//		
		driver.findElement(By.xpath(".//span[contains(text(),'Test Plans')]")).click();
		Thread.sleep(5000);
//		List<WebElement> sprint= driver.findElements(By.xpath(".//div[contains(text(),'Release 2.1')]/parent::a/following-sibling::ul/li"));
		List<WebElement> sprint= driver.findElements(By.xpath(".//div[contains(text(),'"+Release+"')]/parent::a/following-sibling::ul/li"));
//		
		for(WebElement sprintEle : sprint)
		{String sprintId=sprintEle.getAttribute("id");
			String loc=".//li[@id='"+sprintId+"']/a//span";
			
			List<WebElement> expand = driver.findElements(By.xpath(".//li[@id='"+sprintId+"']/ul"));
			
			if (expand.size()==1)
				driver.findElement(By.xpath(loc)).click();
			Thread.sleep(1000);
			if(Sprint.trim().equalsIgnoreCase(driver.findElement(By.xpath(".//li[@id='"+sprintId+"']/a/div")).getText().trim()))
			{
				
			driver.findElement(By.xpath(loc)).click();
			Thread.sleep(5000);
			List<WebElement> story = driver.findElements(By.xpath(".//li[@id='"+sprintId+"']/ul/li"));
			
			for(WebElement storyEle :  story)
			{
				storyEle.click();
				Thread.sleep(3000);
				List<WebElement> testcase=driver.findElements(By.className("grid-cell-contents-container"));
			
				for(WebElement testcaseEle : testcase)
				{
					Actions rClick=new Actions(driver); 
					rClick.contextClick(testcaseEle).build().perform();
					
					Thread.sleep(1000);
					driver.findElement(By.xpath(".//span[contains(text(),'View test result')]")).click();
					Thread.sleep(2000);
					driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
					Thread.sleep(2000);
					List<WebElement> attachment = driver.findElements(By.xpath(".//td[@class='iteration-attachment-name']/a"));
					List<WebElement> comment = driver.findElements(By.xpath(".//p[contains(text(),'Post Review Done')]"));
					
					System.out.println("#########attachment - "+attachment.size());
					System.out.println("#########comment - "+comment.size());
					if(attachment.size()!=0 && comment.size()==0)
					{
					driver.findElement(By.xpath(".//span[contains(text(),'Update analysis')]")).click();

					Thread.sleep(5000);
					WebElement ele1=driver.findElement(By.xpath(".//*[@class='identity-picker-dropdown identity-picker-fixed-position']"));
			
					driver.findElement(By.xpath(".//div[@class='identity-picker-container']/div/span[2]")).click();
					
					
					
					WebElement ele2=driver.findElement(By.xpath(".//div[@class='item-text-container']//span[contains(text(),'Mohammad Parvez')]/parent::div/parent::div/preceding-sibling::div[2]"));
			
					
					Thread.sleep(1000);
					WebElement element = driver.findElement(By.xpath(".//div[@class='item-text-container']//span[contains(text(),'Mohammad Parvez')]"));
					rClick.moveToElement(element).build().perform();
					
					driver.findElement(By.xpath(".//div[@class='item-text-container']//span[contains(text(),'Mohammad Parvez')]")).click();
					driver.findElement(By.id("comment")).sendKeys("Post Review Done.");
					driver.findElement(By.id("ok")).click();
					Thread.sleep(1000);
					driver.close();
					Thread.sleep(1000);
					driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
					}
					else
					{
						driver.close();
						Thread.sleep(1000);
						driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
					}
					}
			
			}
			
		}}
		
	}
	static void setAttributeByJavaScript(WebElement element, String attName, String attValue) {
		((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", 
                element, attName, attValue);
   }

}
