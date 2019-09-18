package TestRegression;

import org.testng.annotations.Test;

import base.TestBase;
import utility.Utils;

import org.testng.annotations.BeforeClass;

public class emPortalRegressionSuite extends TestBase {
 
  @BeforeClass
	public void preSuite() throws Exception {
	 
	  Utils.NavigatetoLink(getUrl());
	  
	}
@Test
  public void testapp()
  {
	  
  }
}
