package GLADIUS.Tests;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Page.GLADIUS.HomePage;
import Page.GLADIUS.ProcessPage;
import base.TestBase;
import junit.framework.Assert;
import utility.Utils;

public class VerifyGladiusProcess extends TestBase {

	HomePage homePage;
	ProcessPage processPage;
		
		
       @BeforeClass
		public void preSuite() throws Exception {
		 
		  Utils.NavigatetoLink(getUrl());
		  
		}
        @Test(priority=0)
		public void VerifyHomePage()
		{
			homePage= new HomePage();
			Assert.assertEquals("Click here to process L1 A++", homePage.getProcessL1ALink().getText());
		}

        @Test(priority=1)
    	public void ClickLinkPage() throws IOException
    	{
    		
    		homePage.getProcessL1ALink().click();
    		processPage= new ProcessPage();
    		processPage.getUploadL1AButton().click();
    		Utils.waitFor(1);
    		Runtime.getRuntime().exec("C:\\AutomationFramework\\TestAutomation\\SuiteFiles\\FileUpload.exe");
    		Utils.waitFor(5);
    		processPage.getProcessButton().click();
    		Utils.waitFor(50);
    		Utils.waitForElement(processPage.getProcessedArticleLink(),500);
    		Utils.waitFor(2);
    		processPage.getProcessedArticleLink().click();
    		Utils.waitFor(10);
    	}
        
       
}
