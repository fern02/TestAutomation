package base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import org.apache.log4j.xml.DOMConfigurator;
import org.json.simple.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.google.common.base.Function;

import utility.ConfigProperties;

import java.io.FileNotFoundException;
import java.net.URL;

public class TestBase {


private static HashMap<Long, WebDriver> driverMap;
private WebDriver webDriver;
public static long id = Thread.currentThread().getId();
static String browser;
DateFormat dateFormat;
protected static String sFinalResult = "PASSED!";
public static ConfigProperties configProperties;
private String resourcePath = "src\\test\\resources\\";
public static String ConfigPath = null;
public Date date;

@BeforeSuite
public void testConfigReader() throws FileNotFoundException
{
    configProperties = new ConfigProperties();
    ConfigPath = resourcePath + configProperties.configPath;
    DOMConfigurator.configure(".\\SetupFiles\\log4j.xml");
    dateFormat = new SimpleDateFormat("M/d/yyyy");
    date = new Date();
    System.out.println(dateFormat.format(date));
    
}



@BeforeClass
@Parameters("browser")
public void createWebDriver(String browser) throws Exception
{
     
    if (webDriver == null)
    {
        try
        {
            switch (BrowserTypes.valueOf(browser))
            {
                case CHROME:
                    System.setProperty("webdriver.chrome.driver", ".\\SetupFiles\\chromedriver.exe");
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("chrome.switches", "--disable-extensions");
                    DesiredCapabilities capabilitiesChrome = DesiredCapabilities.chrome();
                    //String downloadFilepath = System.getProperty("user.dir") + "\\src\\test\\resources\\Download\\NewXmlOutput"; // setting
                    String downloadFilepath = "C:\\Users\\pso4578\\Downloads\\26March\\NewXmlOutput";
                    // download
                    
            		//Map<String, Object> prefs = new HashMap<String, Object>(); 
            	//	prefs.put("safebrowsing.enabled", "false"); options.setExperimentalOption("prefs", prefs);                                                                                              // directory
                                                                                                                   // for
                                                                                                                   // chrome
                    HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                    chromePrefs.put("safebrowsing.enabled", "false"); options.setExperimentalOption("prefs", chromePrefs); 
                    capabilitiesChrome.setPlatform(Platform.WINDOWS);
                    if (configProperties.isRemote().equalsIgnoreCase("true"))
                    {
                        capabilitiesChrome.setBrowserName("chrome");
                        webDriver = new RemoteWebDriver(new URL(configProperties.getNodeUrl()), capabilitiesChrome);
                        System.out.println("Launching grid for Chrome browser.");
                    }
                    else
                    {
                        chromePrefs.put("download.default_directory", downloadFilepath);
                        chromePrefs.put("credentials_enable_service", false);
                        chromePrefs.put("profile.password_manager_enabled", false);
                        options.setExperimentalOption("prefs", chromePrefs);
                        capabilitiesChrome.setCapability(ChromeOptions.CAPABILITY, options);
                        webDriver = new ChromeDriver(capabilitiesChrome);
                        System.out.println("Going to launch Chrome driver!");
                    }

                    Reporter.log("Going to launch Chrome driver!");
                    break;

                case FIREFOX:
                    System.setProperty("webdriver.gecko.driver", ".\\SetupFiles\\geckodriver.exe");
                    DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                    capabilities.setCapability("marionette", true);
                    webDriver = new FirefoxDriver(capabilities);
                    System.out.println("Going to launch Firefox driver!");
                    break;

                case IE:
                    System.setProperty("webdriver.ie.driver", ".\\SetupFiles\\IEDriverServer.exe");
                    DesiredCapabilities capabilitiesIE = DesiredCapabilities.internetExplorer();
                    capabilitiesIE.setPlatform(org.openqa.selenium.Platform.WINDOWS);
                    if (configProperties.isRemote().equalsIgnoreCase("true"))
                    {
                        System.out.println("Inside remote::" + configProperties.getNodeUrl());
                        capabilitiesIE.setPlatform(Platform.WINDOWS);
                        capabilitiesIE.setCapability(CapabilityType.BROWSER_NAME, "internet explorer");
                        capabilitiesIE.setCapability(CapabilityType.VERSION, "11");
                        capabilitiesIE.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                        capabilitiesIE.setJavascriptEnabled(true);
                        capabilitiesIE.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                        webDriver = new RemoteWebDriver(new URL(configProperties.getNodeUrl()), capabilitiesIE);
                        System.out.println("Launching grid for IE browser.");
                    }
                    else
                    {
                        capabilitiesIE.setCapability(
                                InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                        webDriver = new InternetExplorerDriver(capabilitiesIE);
                        System.out.println("Going to launch IE driver!");
                    }
                    Reporter.log("Going to launch IE driver!");
                    break;

                default:
                    new RuntimeException("Unsupported browser type");
            }
            webDriver.manage().window().maximize();
            webDriver.manage().timeouts().implicitlyWait(TimeConstants.implicitWaitTime, TimeUnit.SECONDS);
            driverMap = new HashMap<Long, WebDriver>();
            driverMap.put(id, webDriver);
        }
        catch (Exception e)
        {
            System.out.println("Unable to acquire the webdriver ." + e);
            throw e;
        }
    }

}

public static String getUrl()
{
    String url = null;
    switch (Application.valueOf(configProperties.getApplication()))
    {

        case GLADIUS:
            switch (Environment.valueOf(configProperties.getEnvironment()))
            {
                case QA01:
                    url = configProperties.getQa01Url();

                    break;

               
                default:
                    break;
            }
            break;
        default:
            throw new RuntimeException("Un supported application");
    }
    System.out.println("Launching the url  :" + url);
    return url;
}

public static WebDriver getDriverInstance()
{
    return driverMap.get(id);
}


/**
 * Explicit wait.
 */
public static void waitForPageLoad()
{
    WebDriverWait wait = new WebDriverWait(getDriverInstance(), TimeConstants.pageLoadTime);
    wait.until(new Function<WebDriver, Boolean>()
    {
        public Boolean apply(WebDriver driver)
        {
            return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
                    .equals("complete");
        }
    });
}



@AfterClass
public void destroy()
{
    getDriverInstance().quit();
    driverMap = null;
}


}
