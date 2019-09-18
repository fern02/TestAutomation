package Page.GLADIUS;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Page.BasePage;

public class ProcessedArticlePage extends BasePage {

	@FindBy(xpath="//a[contains(text(),'Click here to process L1 A++')]")
	public static WebElement processL1A_link;
	
	
	public WebElement getProcessL1ALink()
	{
		return processL1A_link;
	}
	
	@Override
	public void verifyPage() {
		// TODO Auto-generated method stub
		waitForPageLoad();
	
}
}