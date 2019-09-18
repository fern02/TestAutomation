package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import com.csvreader.CsvReader;
import base.TestBase;

public class ConfigProperties
{

    private final String defaultEnvironment = "QA01";
    private final String defaultApplication = "GLADIUS";

    public String userName;
    public String password;
    public String channel;
  
    private String environment;
    private String application;
 
    public String configPath;
    public String testDatapath;
    public String inRulesPartialValidation;
    public String inRulesFullValidation;
    public String nodeUrl;
    public String testDataDirectory;
	
	
	public String testDataFile;

    public String qa01Url;
    
    public String getTestDataDirectory()
    {
    	return testDataDirectory;
    }
    
    public String getQa01Url()
    {
        return qa01Url;
    }

    public String getUserName()
    {
        return userName;
    }

    public String isRemote;
    

    public String getEnvironment()
    {
        return environment;
    }

    public String getApplication()
    {
        return application;
    }

    public String getTestDataFile()
    {
        return testDataFile;
    }

    public String getTestDataPath()
    {
        return testDatapath;
    }

    public String isRemote()
    {
        return isRemote;
    }

    public String getNodeUrl()
    {
        return nodeUrl;
    }

    public String getUser()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }

  

    public String getDefaultEnvironment()
    {
        return defaultEnvironment;
    }

    public String getDefaultApplication()
    {
        return defaultApplication;
    }

 
   public String getConfigPath()
    {
        return configPath;
    }

    public String getTestDatapath()
    {
        return testDatapath;
    }

    public String getChannel()
    {
        return channel;
    }

    

    public ConfigProperties()
    {
        File file = new File(System.getProperty("user.dir") + "\\SetupFiles\\configure.properties");

        FileInputStream fileInput = null;
        try
        {
            fileInput = new FileInputStream(file);
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Properties prop = new Properties();
        try
        {
            prop.load(fileInput);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            isRemote= prop.getProperty("isRemote");
            String browser = prop.getProperty("browser");
            environment = prop.getProperty("environment");
            application = prop.getProperty("application");
            channel = prop.getProperty("channel");
            userName = prop.getProperty("userName");
            password = prop.getProperty("password");
            qa01Url = prop.getProperty("qa01Url");
            testDatapath = prop.getProperty("testDatapath");

            System.setProperty("browser", browser);
            System.setProperty("environment", environment);
            System.setProperty("application", application);
            System.setProperty("channel", channel);
            System.setProperty("userName", userName);
            System.setProperty("password", password);
            System.setProperty("qa01Url", qa01Url);
            System.setProperty("testDatapath", testDatapath);
           
        }
    }

  
