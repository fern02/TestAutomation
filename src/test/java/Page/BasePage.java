package Page;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.PageFactory;

import base.TestBase;
import base.TimeConstants;

public abstract class  BasePage extends TestBase {

	  public BasePage()
	    {
	        PageFactory.initElements(getDriverInstance(), this);
	        waitForPageLoad();
	        getDriverInstance().manage().timeouts().implicitlyWait(TimeConstants.elementMinorLoadTime, TimeUnit.SECONDS);
	        this.verifyPage();
	    }
	
	  public abstract void verifyPage();
	  
	  
}
