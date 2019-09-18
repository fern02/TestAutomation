package TestRegression;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import base.TestBase;
import utility.ConfigProperties;
import utility.Utils;
public class TestRegression extends TestBase {

	private String[] as;
	@BeforeClass
	public void preSuite() throws Exception {
	 
	  Utils.NavigatetoLink(getUrl());
	  
	}
@Test()
 public void ClickLinkPage() throws IOException, SAXException, ParserConfigurationException, TransformerException
	  {
	
	HashMap<String,String> inputTestDataFilesInfo = new HashMap <String,String>();
	String FileName,FilePath = null;
    getDriverInstance().findElement(By.xpath(".//*[@id='rest']")).click();
    Utils.waitFor(5);
    WebElement selectFileBtn=getDriverInstance().findElement(By.xpath("//span[@class='btn btn-file']"));
    
    //ArrayList<String> results = new ArrayList<String>();
    
    String directoryPath="C:\\AutomationFramework\\TestData\\";
    inputTestDataFilesInfo=Utils.listFilesAndFilesSubDirectories(directoryPath);
    
    System.out.println(inputTestDataFilesInfo);
    String inputXmlPath= null;
    String expectedXmlPath=null;

    Set<Entry<String, String>> inputFilesEntrySet = inputTestDataFilesInfo.entrySet();
    //System.out.println(inputFilesEntrySet);
    for (Entry<String, String> FilesInfo : inputFilesEntrySet) 
    {
        FileName=FilesInfo.getKey();
        FilePath=FilesInfo.getValue();
        //System.out.println(FileName+" : "+FilePath);
        Utils.waitForElement(selectFileBtn, 10);
        selectFileBtn.click();
        Utils.waitFor(5);
        Runtime.getRuntime().exec("C:\\AutomationFramework\\TestAutomation\\SuiteFiles\\FileUploadpara.exe"+" "+FilePath);
	    Utils.waitFor(5);
	    getDriverInstance().findElement(By.xpath(".//*[@id='submitRequest']")).click();
	    //Utils.waitFor(5);
	    Utils.waitForElement((getDriverInstance().findElement(By.xpath(".//*[@id='xmlCode']"))), 10);
	    String xml=(getDriverInstance().findElement(By.xpath(".//*[@id='xmlCode']"))).getText();
	    //System.out.println(xml);
	    getDriverInstance().findElement(By.xpath("//*[@id=\"fileInputDiv\"]/div/span/a")).click();
	    Utils.waitFor(1);
	    String xmlFileName=Utils.returnOuptutXmlFileName(FileName);
	    Utils.stringToDom(xml,xmlFileName);
	    inputXmlPath="C:\\AutomationFramework\\TestAutomation\\xml\\"+""+xmlFileName+".xml";
	    expectedXmlPath="C:\\AutomationFramework\\TestAutomation\\expectedXml\\"+""+xmlFileName+".xml";
	    Utils.compareXML(inputXmlPath, expectedXmlPath);
	  
	 }
      Utils.compareXML(inputXmlPath, expectedXmlPath);
 }
}
	
	

