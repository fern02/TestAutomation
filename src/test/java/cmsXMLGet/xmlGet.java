package cmsXMLGet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import utility.Utils;

public class xmlGet extends TestBase{
	
	//@BeforeClass
	public void preSuite() throws Exception {
	 
	  Utils.NavigatetoLink(getUrl());
	  
	}
	
	
	@Test
	public void Getenrichxml() throws IOException {
	
		
	
	
		
	//RestAssured.baseURI="http://cms-services.live.cf.private.springer.com/b132a45b-61b0-4175-b41b-6131b24f3f19";
	RestAssured.baseURI="http://cms-services.live.cf.private.springer.com/";
	
	RequestSpecification httpRequest = RestAssured.given();
	
	/*Getting exact route and Storing response as string*/
	
	Response response = httpRequest.request(Method.GET, "/enrich_article");
		
	String responseBody = response.getBody().asString();
	
	System.out.println("Complete ResponseBody : "+responseBody);
	
	/*Extracting exact response from responsebody using responsebody object and storing it in a list*/
	
	ResponseBody extractResponse = response.getBody();
	
	List<String> stringArray = extractResponse.jsonPath().getList("_links.href");

	/*Storing the exact .xml URI and printing it*/
	
	String xmlURI = stringArray.get(1);
	
	System.out.println("Exact XML URI : "+xmlURI);
	
	/*Calling the enrich_article URI and comparing statuscode*/
	
	Response enrichArticleresponse = httpRequest.request(Method.GET, xmlURI);
	
	int statusCode = enrichArticleresponse.getStatusCode();
	
	System.out.println("StatusCode : "+statusCode);
	
	Assert.assertEquals(200, statusCode);
}
}
	
   
