package ACDCWebPortal;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.io.Files;

import base.TestBase;
import utility.Utils;

public class processManuscriptUsingWebortal extends TestBase{
	
	@BeforeClass
	public void preSuite() throws Exception {
	 
	  Utils.NavigatetoLink(getUrl());
	  
	}
	
	@Test
	public void processManuscriptFile() throws IOException {
		
//		
		Utils utila= new Utils();
		
		HashMap<String,String> inputTestDataFilesInfo = new HashMap <String,String>();
		String FileName,FilePath = null;
		WebElement selectFileBtn;
	    //WebElement selectFileBtn=getDriverInstance().findElement(By.xpath("//span[@class='btn btn-file']"));
	    
	    //ArrayList<String> results = new ArrayList<String>();
	    
	    String directoryPath="D:\\MsWordFiles\\Batch1";
	    inputTestDataFilesInfo=Utils.listFilesAndFilesSubDirectories(directoryPath);
	    
	    //System.out.println(inputTestDataFilesInfo);
	    String inputXmlPath= null;
	    String expectedXmlPath=null;

	    Set<Entry<String, String>> inputFilesEntrySet = inputTestDataFilesInfo.entrySet();
	    //System.out.println(inputFilesEntrySet);
	  
	    for (Entry<String, String> FilesInfo : inputFilesEntrySet) 
	    {
	      try
		    {
	        FileName=FilesInfo.getKey();
	        FilePath=FilesInfo.getValue();
	        //System.out.println(FileName);
	        //Utils.waitForElement(selectFileBtn, 10); 
	        isElementPresent(By.xpath("/html/body/app-root/div[2]/div[1]/div/form/div[2]/div/input"));
	        selectFileBtn=getDriverInstance().findElement(By.xpath("/html/body/app-root/div[2]/div[1]/div/form/div[2]/div/input"));
		    Utils.waitFor(5);
	        selectFileBtn.click();
	        System.out.println("Processing of "+FileName+" is started");
	        Utils.waitFor(5);
	        Runtime.getRuntime().exec("C:\\AutomationFramework\\TestAutomation\\SuiteFiles\\FileUploadpara.exe"+" "+FilePath);
	        Utils.waitFor(5);
	        getDriverInstance().findElement(By.xpath("/html/body/app-root/div[2]/div[1]/div/form/div[3]/div[2]/button")).click();
	       
	        //Utils.waitFor(10);
	        
	       // WebElement errorMsg=getDriverInstance().findElement(By.xpath("/html/body/app-root/div[2]/div[1]/div[2]/span/h5"));
	       
	        boolean elemPresent=isElementPresent(By.xpath("//a[contains(text(),'Download')]"));
	        //String errortxt=errorMsg.getText();
	        //errorMsg.isDisplayed();
	        //System.out.println(errortxt);
	       
	        if(elemPresent)
	        {
	        //WebDriverWait wait = new WebDriverWait(getDriverInstance(),100);
	        //wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//a[contains(text(),'Download')]"))));
	        WebElement downloadBtn=getDriverInstance().findElement(By.xpath("//a[contains(text(),'Download')]")); 
	        //Utils.waitForElement(downloadBtn, 10000);
	        downloadBtn.click();
	        Utils.waitFor(5);
	        Alert alert= getDriverInstance().switchTo().alert();
	        alert.accept();
	        Utils.waitFor(20);
	       
	        createDirectorywithFileName(FileName,FilePath);
		    }
	           
	        else
	        {
	        	System.out.println(FileName+ " is not processed");
	        	getDriverInstance().navigate().refresh();
	        	Utils.waitFor(10);
	        }
	        }
	        catch(Exception e)
	        {
	    		System.out.println("File is not processed");
	        }		
	    } 
	    
	
	}
		
	
	    

		    
	   public void createDirectorywithFileName(String FileName,String FilePath ) throws IOException
	   {
	   	String downloadpath="C:\\Users\\pso4578\\Downloads\\26March\\NewXmlOutput\\";
	    
	   	String destpath="C:\\Users\\pso4578\\Downloads\\26March\\FinalOutput\\";
	   	
	   	String alreadyDonePath="D:\\MsWordFiles\\AlreadyDoneAutomated\\"+FileName;
	   	
	  	File Source= new File(FilePath);
	    
	  	String foldername=returnFolderName(FileName);
	  	
	  	File dest= new File (destpath+foldername +"\\"+FileName);
	  	
	  	File alreadyDone= new File(alreadyDonePath);
	  	
	  	String xmlOutputName= "11103_"+ foldername +"_0_structuring.xml";
	  	
	  	File xmlSource= new File(downloadpath+xmlOutputName);
	  	
	  	File xmlDest= new File(destpath+foldername +"\\"+foldername +"_0_structuring.xml");
	  	
	    
	    
	    if(xmlSource.exists())
	    {	
	    new File(destpath+foldername).mkdirs();	
	    Files.copy(Source, dest);
	    
	    Files.move(Source, alreadyDone);
	    
	    Files.copy(xmlSource, xmlDest);
	    }
	    else
	    {	
	    	System.out.println("File is not processed in create Directory");
	    }
	    }

	   public static String returnFolderName(String xmlFileName)
	   {
	   	int position;
	   	
	   	position=xmlFileName.indexOf(".");
	   	//position=xmlFileName.indexOf(".pdf");
	   	xmlFileName=xmlFileName.substring(0,position);
	  // 	xmlFileReportName=xmlFileName;
	   	return  xmlFileName;
	   }
	   
	   
	   public boolean isElementPresent(By by) {
		   boolean isPresent = true;
		   try {
			   WebDriverWait wait = new WebDriverWait(getDriverInstance(),1000);
		       wait.until(ExpectedConditions.visibilityOfElementLocated(by));	   
			   
		   
		   } catch (TimeoutException e) {
		    isPresent = false;
		   }
		  return isPresent;
		  }
	   
	   
	   
}
