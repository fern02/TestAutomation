package performanceTest;




import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import org.testng.annotations.Test;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import Page.BasePage;
import utility.Utils;

public class automatedPerformanceTest {
	int count=30;
	HashMap<String,String> inputTestDataFilesInfo = new HashMap <String,String>();	
	
@Test	
public void performanceTest() throws FileNotFoundException, IOException {

	
try {
	
	SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy");
	Date curDate1 = new Date();
	String strDate1 = sdf1.format(curDate1);
	
	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_hhmmss");
	Date curDate = new Date();
	String strDate = sdf.format(curDate);
	String fileName = "logFile" + strDate1 +".txt" ;
	File newFile = new File(fileName);
	
	
	PrintWriter outputfile;


HashMap<String,String> inputTestDataFilesInfo = new HashMap <String,String>();	
	
JSch jsch = new JSch();
Session session = jsch.getSession("acdc_live", "ftp.springernature.com");
session.setPassword("bV25J19n");
java.util.Properties config = new java.util.Properties();
config.put("StrictHostKeyChecking", "no");
config.put("PreferredAuthentications",
"publickey,keyboard-interactive,password");

session.setConfig(config);
session.connect();
Channel channel = session.openChannel("sftp");
channel.connect();
System.out.println("sftp channel opened and connected.");
ChannelSftp channelSftp = (ChannelSftp) channel;

String sftpDirectory = "/ForEarlyProof";

String inputFileDirectory="D:\\LiveProcess_8thJune\\LivePackageReprocessed\\";

String directoryPath="D:\\LiveProcess_8thJune\\inputToProcess\\";

String searchDirectory="D:\\LiveProcess_8thJune\\LivePackagegoXml\\";
String ToLocation="D:\\LiveProcess_8thJune\\LivePackagesReprocessedDone\\";

while(count!=0) {
	outputfile = new PrintWriter(new BufferedWriter(new FileWriter("D:\\LiveProcess_8thJune\\logs\\"+newFile,true)));	

	outputfile.println("===============================");
	outputfile.println("sftp channel opened and connected."+" "+"TimeStamp:"+ strDate);
	
	
Utils.moveCountBasedFiles(inputFileDirectory, directoryPath,4);

inputTestDataFilesInfo=Utils.listFilesAndFilesSubDirectories(directoryPath);



String FileName,FilePath = null;
//File directory = new File("D:\\LoadTest");
//File[] fList = directory.listFiles();
//String filepath=null;
//File file1 = new File(filepath);

//for (File file : fList){ 
//if (file.isFile()){
//	
//	String filename=file.getAbsolutePath();
	
//InputStream ins = new FileInputStream(file1);

 Utils.moveFileBasedOnFileName(directoryPath, searchDirectory, directoryPath);

 inputTestDataFilesInfo=Utils.listFilesAndFilesSubDirectories(directoryPath);
 Set<Entry<String, String>> inputFilesEntrySet = inputTestDataFilesInfo.entrySet();

for (Entry<String, String> FilesInfo : inputFilesEntrySet) 
{
  
    FileName=FilesInfo.getKey();
    FilePath=FilesInfo.getValue();
    
   if(FileName.toLowerCase().endsWith(".zip"))
	//OutputStream out = FileOutputStream(file);
//	OutputStream out = new FileOutputStream(filename1);
{
channelSftp.put(FilePath, sftpDirectory, ChannelSftp.OVERWRITE);
System.out.println(FileName + " transferred to " + sftpDirectory );
outputfile.println(FileName + " transferred to " + sftpDirectory+ " "+"TimeStamp:"+ strDate);
System.out.println("Transfer Process Completed for zip file...");
outputfile.println("Transfer Process Completed for zip file..."+" "+"TimeStamp:"+ strDate);

}
//else {
//	System.out.println("zip File not found.");
//}
}

for (Entry<String, String> FilesInfo : inputFilesEntrySet) 
{
  
    FileName=FilesInfo.getKey();
    FilePath=FilesInfo.getValue();

if(FileName.toLowerCase().endsWith(".xml"))
{
channelSftp.put(FilePath, sftpDirectory, ChannelSftp.OVERWRITE);
System.out.println(FileName + " transferred to " + sftpDirectory );
outputfile.println(FileName + " transferred to " + sftpDirectory+" "+"TimeStamp:"+ strDate);
System.out.println("Transfer Process Completed for xml file...");
outputfile.println("Transfer Process Completed for xml file..."+" "+"TimeStamp:"+ strDate);


}


//else {
//	System.out.println("xml File not found.");
//}

}

outputfile.close();
System.out.println("Count:"+count);
count--;
	//outputfile.close();
	//Thread.sleep(10);
Utils.moveFileAfterProcessing(directoryPath, ToLocation);
	//performanceTest();
}
//outputfile.close();
}


catch (JSchException e) {
e.printStackTrace();
}catch (Exception e) {
e.printStackTrace();
} finally {
	
	//ins.close();
//System.out.println("Transfer Process Completed...");
}
}


//@Override
//public void verifyPage() {
//	// TODO Auto-generated method stub
//	waitForPageLoad();
//}




}
