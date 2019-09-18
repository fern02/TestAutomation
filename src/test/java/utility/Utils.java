package utility;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.Diff;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.io.Files;

import base.TestBase;


public class Utils {

	

	 public static boolean waitFor(int iSeconds)
	    {
	        try
	        {
	            Thread.sleep(iSeconds * 1000);
	        }
	        catch (Exception e)
	        {
	           
	            return false;
	        }
	        return true;
	    }
	
	
	

	 static public void NavigatetoLink(String sURLLink)
	    {

	        try
	        {
	            long start = System.currentTimeMillis();
	            waitFor(2);
	            TestBase.getDriverInstance().get(sURLLink);
	            long finish = System.currentTimeMillis();
	            double totalTime = (finish - start);
	            double loadtime = (totalTime / 1000);
	            String sTotalTime = Double.toString(loadtime);
	            System.out.println("Total Time for page load : " + sTotalTime + " sec");
	        }
	        catch (AssertionError e)
	        {
	           // Log.warn("ERROR: Cannot navigate to link");
	        	e.printStackTrace();
	        }
	   
	    }	   
	 
	

	 public static void waitForElement(WebElement element,int timeout)
	    {
	        WebDriverWait wait = new WebDriverWait(TestBase.getDriverInstance(), timeout);
	        wait.until(ExpectedConditions.visibilityOf(element));
	    }


HashMap<String, String> fileNameAndPath= new HashMap<String,String>();

public static HashMap<String,String> listFilesAndFilesSubDirectories(String directoryName){
	
	HashMap<String, String> fileNameAndPath= new HashMap<String,String>();
	
	File directory = new File(directoryName);
    //get all the files from a directory
    File[] fList = directory.listFiles();
   // System.out.println(fList);
    for (File file : fList){
        if (file.isFile()){
            fileNameAndPath.put(file.getName(),file.getAbsolutePath());
            //System.out.println(fileNameAndPath);
         } else if (file.isDirectory()){
            listFilesAndFilesSubDirectories(file.getPath());
           // System.out.println(file.getPath());
        }
      }
    return fileNameAndPath;
}

public static void stringToDom(String xmlSource, String FileName) throws SAXException, ParserConfigurationException, IOException, TransformerException{
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));
    // Use a Transformer for output
    TransformerFactory tFactory = TransformerFactory.newInstance();
    Transformer transformer = tFactory.newTransformer();

    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(new File("C:\\AutomationFramework\\TestAutomation\\xml\\"+""+FileName+".xml"));
    transformer.transform(source, result);
}  

static String xmlFileReportName;
private static List <Difference> differences;
private static List <Difference> filterDifferences= new ArrayList<Difference>() ;

public static String returnOuptutXmlFileName(String xmlFileName)
{
	int position;
	
	position=xmlFileName.indexOf("_Input");
	//position=xmlFileName.indexOf(".pdf");
	xmlFileName=xmlFileName.substring(0,position);
	xmlFileReportName=xmlFileName;
	return  xmlFileName;
}

public static void compareXML(String inputXmlPath,String expectedXmlPath) throws
SAXException, IOException{
	
	FileInputStream fis1 = new FileInputStream(inputXmlPath);
    FileInputStream fis2 = new FileInputStream(expectedXmlPath);
 
    // using BufferedReader for improved performance
    BufferedReader  source = new BufferedReader(new InputStreamReader(fis1));
    BufferedReader  target = new BufferedReader(new InputStreamReader(fis2));
 
    //configuring XMLUnit to ignore white spaces
    XMLUnit.setIgnoreWhitespace(true);
    
    Diff xmlDiff = new Diff(source, target);
    
    //for getting detailed differences between two Xml files
    DetailedDiff detailXmlDiff = new DetailedDiff(xmlDiff);
    
    differences= detailXmlDiff.getAllDifferences();

    differences=Utils.filterDifferenceReport(differences);
    Utils.printDifferences(differences);
    
}

public static List<Difference> filterDifferenceReport (List <Difference>differences)
{
	filterDifferences.clear();
	Iterator<Difference> iterator = differences.iterator();
	while (iterator.hasNext()) {
		
		Difference difflist=iterator.next();
	    String differnceToString=difflist.toString();
	   // System.out.println(differnceToString);
	    if(!(differnceToString.contains("<application")))
	    {
	    if(!(differnceToString.contains("xsi:schemaLocation")))
	    {
	    if(!(differnceToString.contains("<abstract")))
	    {
	    if(!(differnceToString.contains("<profileDesc")))	
	    
	        filterDifferences.add(difflist);
	    } 
	}
	    
	}
	}
		return filterDifferences;
	
	
}

public static List<Difference> filterDifferenceReport2 (List <Difference>differences)
{
	filterDifferences.clear();
	Iterator<Difference> iterator = differences.iterator();
	while (iterator.hasNext()) {
		
		Difference difflist=iterator.next();
	    String differnceToString=difflist.toString();
	   // System.out.println(differnceToString);
	    
	    if(!(differnceToString.contains("@type")))
	    {
	    if(!(differnceToString.contains("xsi:schemaLocation")))
	    {
	    if(!(differnceToString.contains("<abstract")))	   
	        filterDifferences.add(difflist);
	    
	}
	
	}
	}
		return filterDifferences;
	
	
}



public static void printDifferences(List allDifferences) throws IOException{
	
	Date date = new Date();
	//date.setHours(date.getHours() + 8);
	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssss");
	//System.out.println("Date");
	//PrintWriter outputfile = new PrintWriter(new BufferedWriter(new FileWriter("C:\\AutomationFramework\\Automation_AccuracyResult_"+""+dateFormat.format(date)+".txt",true)));
	PrintWriter outputfile = new PrintWriter(new BufferedWriter(new FileWriter("C:\\AutomationFramework\\Automation_AccuracyResult_Batch12.txt",true)));
	int totalDifferences = allDifferences.size();
    System.out.println("===============================");
    outputfile.println("===============================");
    System.out.println(xmlFileReportName);
    outputfile.println(xmlFileReportName);
    System.out.println("Total differences : " + totalDifferences);
    outputfile.println("Total differences : " + totalDifferences);
    System.out.println("================================");
    outputfile.println("================================");
    
    Iterator<Difference> iterator = allDifferences.iterator();
	while (iterator.hasNext()) {
		System.out.println(xmlFileReportName);
		  outputfile.println(xmlFileReportName);
	   Difference difflist=iterator.next();
	   // String filterResult=difflist.toString();
	   // if(!(filterResult.contains("application")))
	   // {
	    System.out.println(difflist);
		outputfile.println(difflist);
		 
		//System.out.println(xmlFileReportName);
	    }
	outputfile.close();
}

public static void saveLog(String ManuscriptID, String globalMsID) throws IOException{
	
	Date date = new Date();
	//date.setHours(date.getHours() + 8);
	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssss");
	//System.out.println("Date");
	//PrintWriter outputfile = new PrintWriter(new BufferedWriter(new FileWriter("C:\\AutomationFramework\\Automation_AccuracyResult_"+""+dateFormat.format(date)+".txt",true)));
	PrintWriter outputfile = new PrintWriter(new BufferedWriter(new FileWriter("C:\\AutomationFramework\\InitiateProdcution.txt",true)));
	//int totalDifferences = allDifferences.size();
    
    outputfile.println("===============================");
    outputfile.println(dateFormat.format(date));
    outputfile.println(ManuscriptID +" "+ globalMsID +" " +"Initiate Production is Performed");
    outputfile.close();
}

public static void moveFileAfterProcessing(String directoryPath,String ToLocation) throws IOException
{
	HashMap<String,String> inputTestDataFilesInfo = new HashMap <String,String>();	
	inputTestDataFilesInfo=listFilesAndFilesSubDirectories(directoryPath);
	String FileName,FilePath=null;
	Set<Entry<String, String>> inputFilesEntrySet = inputTestDataFilesInfo.entrySet();

	for (Entry<String, String> FilesInfo : inputFilesEntrySet) 
	{
	  
	    FileName=FilesInfo.getKey();
	    FilePath=FilesInfo.getValue();
	    String alreadyDonePath=ToLocation;
	    File Source= new File(FilePath);
	    File alreadyDone= new File(alreadyDonePath+FileName);
	    Files.move(Source, alreadyDone);

	}

}

public static void moveCountBasedFiles(String directoryPath,String ToLocation, int count) throws IOException
{
	HashMap<String,String> inputTestDataFilesInfo = new HashMap <String,String>();	
	inputTestDataFilesInfo=listFilesAndFilesSubDirectories(directoryPath);
	String FileName,FilePath=null;
	Set<Entry<String, String>> inputFilesEntrySet = inputTestDataFilesInfo.entrySet();
    int initialCount=count ;
  
   
	for (Entry<String, String> FilesInfo : inputFilesEntrySet) 
	{
	  
	    FileName=FilesInfo.getKey();
	    FilePath=FilesInfo.getValue();
	    String alreadyDonePath=ToLocation+FileName;
	    File Source= new File(FilePath);
	    File alreadyDone= new File(alreadyDonePath);
	    Files.move(Source, alreadyDone);
	    --initialCount;
        if(initialCount==0)
        	break;
	}

}

public static void moveFileBasedOnFileName(String directoryPath,String searchDirectory, String ToLocation) throws IOException

{
	
	
	HashMap<String,String> inputTestDataFilesInfo = new HashMap <String,String>();	
	inputTestDataFilesInfo=listFilesAndFilesSubDirectories(directoryPath);
	String FileName,FilePath=null;
	Set<Entry<String, String>> inputFilesEntrySet = inputTestDataFilesInfo.entrySet();

	for (Entry<String, String> FilesInfo : inputFilesEntrySet) 
	{
	  
		FileName=FilesInfo.getKey();
	    int position;
		String goXmlName;
		position=FileName.indexOf(".zip");
		//position=xmlFileName.indexOf(".pdf");
		goXmlName=FileName.substring(0,position)+".go.xml";
		
		File Source= new File(searchDirectory+goXmlName);
	    File ToLoc= new File(ToLocation+goXmlName);
	    Files.move(Source, ToLoc);

		
		
		
		
//	    FileName=FilesInfo.getKey();
//	    FilePath=FilesInfo.getValue();
//	    String alreadyDonePath=ToLocation+FileName;
//	    File Source= new File(FilePath);
//	    File alreadyDone= new File(alreadyDonePath);
//	    Files.move(Source, alreadyDone);

	}
	
}

}













