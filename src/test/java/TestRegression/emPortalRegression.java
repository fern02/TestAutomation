package TestRegression;

import org.testng.annotations.BeforeClass;

import base.TestBase;
import utility.Utils;

public class emPortalRegression extends TestBase {
	
	
	@BeforeClass
	public void preSuite() throws Exception {
	 
	  Utils.NavigatetoLink(getUrl());
	  
	}

}
