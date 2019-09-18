package EMbot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import base.TestBase;
import utility.Utils;

public class emInitProdBot extends TestBase {
	@BeforeClass
	public void preSuite() throws Exception {
	 
	  Utils.NavigatetoLink(getUrl());
	  
	}
   
   @Test
   public void emInitiateProduction() throws Exception{
      Utils.waitFor(5);
      getDriverInstance().switchTo().frame("content");
      getDriverInstance().switchTo().frame("login");
      getDriverInstance().findElement(By.xpath("//*[@id='username']")).sendKeys("joeharw");
      getDriverInstance().findElement(By.xpath("//*[@id='rightCol']/form/div/fieldset/input[2]")).sendKeys("joe123");
      getDriverInstance().findElement(By.xpath("//input[@value='Editor Login']")).click();
      getDriverInstance().switchTo().frame("content");
      getDriverInstance().findElement(By.xpath("//*[@id='ctl00_Folders_NewSubmissions_FolderLink']")).click();
      Utils.waitFor(10);
      
      String [] manuscript= {"HARWBeta150-D-18-00477","HARWBeta150-D-18-00474"};
      //String manuScriptVal=null;
      //getDriverInstance().switchTo().frame("content");
      for (String manuScriptVal:manuscript) {
      String manuScriptID=getDriverInstance().findElement(By.xpath("//td[contains(text(),'"+manuScriptVal+"')]/ancestor::tr")).getAttribute("id");
      //System.out.println(manuScriptID);
      //String manuScriptID=getDriverInstance().findElement(By.xpath("//td[contains(text(),'HARWBeta150-D-18-00448')]")).getText();
      String manuScriptUpdated= manuScriptID.replace("row", "menu");
      //Utils.waitFor(5);
      //getDriverInstance().findElement(By.xpath("//*[@id='menu4']/a[11]")).click();
      String mainWindow=getDriverInstance().getWindowHandle();
      getDriverInstance().findElement(By.xpath("//*[@id='"+manuScriptUpdated+"']/a[2]")).click();
      Utils.waitFor(10);
      Set<String> set =getDriverInstance().getWindowHandles();
      Iterator<String> itr= set.iterator();
      String GlbMsID=null;
      while(itr.hasNext()){
      String childWindow=itr.next();
      // Compare whether the main windows is not equal to child window. If not equal, we will close.
      
     if(!mainWindow.equals(childWindow)){
      getDriverInstance().switchTo().window(childWindow);
      GlbMsID=getDriverInstance().findElement(By.xpath("//*[@id='MSIDControl_lblMSID']")).getText();
      System.out.println(GlbMsID);
      getDriverInstance().findElement(By.xpath("//*[@id='div0']/input")).sendKeys(GlbMsID);
      getDriverInstance().findElement(By.xpath("//*[@id='btnSaveAndClose1']")).click();
      Utils.waitFor(5);
      getDriverInstance().switchTo().window(mainWindow);
      Utils.waitFor(5);
     }
     }
      getDriverInstance().switchTo().frame("content");
      getDriverInstance().findElement(By.xpath("//*[@id='"+manuScriptUpdated+"']/a[11]")).click();
      Utils.waitFor(5);
      getDriverInstance().findElement(By.xpath("//*[@id='btnConfirm']")).click();
      System.out.println(manuScriptVal +" "+ GlbMsID +" " +"Initiate Production is Performed");
      Utils.saveLog(manuScriptVal,GlbMsID);
      }
      /*WebElement newSubmissionTable= getDriverInstance().findElement(By.xpath("//table[@id='datatable']"));
      List<WebElement> rowsnewSubmissionTable= newSubmissionTable.findElements(By.tagName("tr"));
      int rows_count = rowsnewSubmissionTable.size();
      List<WebElement> colnewSubmissionTable;
      for (int row=0; row<2; row++){
    	  
    	   //(int col=0;
    	   //To locate columns(cells) of that specific row.
    	   colnewSubmissionTable = rowsnewSubmissionTable.get(row).findElements(By.tagName("td"));
    	   //To calculate no of columns(cells) In that specific row.
    	   int columns_count = colnewSubmissionTable.size();
    	 //  System.out.println("Number of cells In Row "+row+" are "+columns_count);
    	   }
      //getDriverInstance().findElements(By.xpath("//a[contains(text(),'Initiate Production')]"));
     System.out.println(rowsnewSubmissionTable.get(1));
     int elementpresent = rowsnewSubmissionTable.get(1).findElements(By.xpath("//a[contains(text(),'Initiate Production')]")).size();
     System.out.println(elementpresent); */
   
   }
   }
//}