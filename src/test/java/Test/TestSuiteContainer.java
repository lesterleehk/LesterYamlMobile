package Test;

import java.util.ArrayList;

public class TestSuiteContainer {
	
	String testSuiteName;
	ArrayList testCases= new ArrayList();
	
	public TestSuiteContainer(String testSuiteName, String[] yamlPath) {
		this.testSuiteName=testSuiteName;
		for (String yamlfilepath: yamlPath) {
			testCases.add(new TestCaseContainer(yamlfilepath));
		}
		
	}
	

}
