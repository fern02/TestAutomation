package Page.GLADIUS;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import Page.BasePage;

public class ProcessPage extends BasePage {

	@FindBy(xpath="//button[@id='btn-browse']")
	public static WebElement uploadL1A_btn;
	
	@FindBy(xpath="//button[@id='btn-process']")
	public static WebElement process_btn;
	
	@FindBy(xpath="//a[contains(text(),'Click here to render processed article')]")
	public static WebElement processedarticle_link;
	
	public WebElement getUploadL1AButton()
	{
		return uploadL1A_btn;
	}
	
	public WebElement getProcessButton()
	{
		return process_btn;
	}
	
	public WebElement getProcessedArticleLink()
	{
		return processedarticle_link;
	}
	
	@Override
	public void verifyPage() {
		// TODO Auto-generated method stub
		waitForPageLoad();
	}

}
