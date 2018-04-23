package com.crm.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.crm.qa.util.TestUtil;
import com.crm.qa.util.WebEventListener;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	public static Logger log = Logger.getLogger(TestBase.class);
	
	
	public TestBase() throws IOException {
		
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream("/Users/sami/FreeCRM_TestNG_Maven/FreeCRM/src/main/java/com/crm/qa/config/configuration.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		
		}
			
		
	}
	
	
	//initialization method 'this is called in every "BeforeMethod" Method'
	public static void initialization() throws IOException {
		
		log.info("****************************** Starting test cases execution  *****************************************");

		
		String browserName = prop.getProperty("browser");
		if(browserName.equalsIgnoreCase("chrome")) {
	        System.setProperty("webdriver.chrome.driver", "/Users/sami/Desktop/RocketLauncher/Mac/Drivers/chromedriver_Mac_V237");
	        	driver = new ChromeDriver();
	        	
		}else if(browserName.equalsIgnoreCase("firefox")){
	       //System.setProperty("webdriver.firefox.driver", "/Users/sami/Desktop/RocketLauncher/Mac/Drivers/geckodriverMAC");
	       driver = new FirefoxDriver();
	       
		}else if(browserName.equalsIgnoreCase("phantomjs")){
			System.setProperty("phantomjs.binary.path", "/Users/sami/Desktop/RocketLauncher/Mac/phantomjs-2.1.1-mac/bin/phantomjs");
			driver = new PhantomJSDriver();
		}
		
		e_driver = new EventFiringWebDriver(driver);
		eventListener= new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		
		 
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT	, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));

	}
	
	

	

}
