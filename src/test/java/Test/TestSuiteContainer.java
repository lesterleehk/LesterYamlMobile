package Test;

import java.util.ArrayList;
import java.util.List;

import JunitTest.TestRunner;

public class TestSuiteContainer {
	
	String testSuiteName;
	public String getTestSuiteName() {
		return testSuiteName;
	}

	public void setTestSuiteName(String testSuiteName) {
		this.testSuiteName = testSuiteName;
	}

	ArrayList<TestCaseContainer> testCases= new ArrayList<TestCaseContainer>();
	
	public ArrayList<TestCaseContainer> getTestCases() {
		return testCases;
	}

	public void setTestCases(ArrayList<TestCaseContainer> testCases) {
		this.testCases = testCases;
	}

	public TestSuiteContainer(String testSuiteName, List<String>  yamlPath) {
		this.testSuiteName=testSuiteName;
		for (String yamlfilepath: yamlPath) {
			testCases.add(new TestCaseContainer(yamlfilepath));
		}
		
	}

	public void setTestRunner(TestRunner testRunner) {
		// TODO Auto-generated method stub
		for (TestCaseContainer testCase: testCases) {
			testCase.setTestRunner(testRunner);
		}
		
	}
	

}
