package ACDCWebPortal;

import org.testng.TestNG;

public class WebPortalMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestNG testng = new TestNG();
        Class[] classes = new Class[] { processManuscriptUsingWebortal.class };
        testng.setTestClasses(classes);
        testng.run();

	}

}
