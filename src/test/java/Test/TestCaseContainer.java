package Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import org.openqa.selenium.By;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import JunitTest.TestRunner;
import PageObjects.TestStepExec;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.TestCase;
import model.TestStep;

public class TestCaseContainer {
	TestCase testCase = null;
	
	
	public TestCaseContainer(String yamlpath) {
		this.yamlFilePath=yamlpath;
		
	}
	
	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	public String getTest_case_id() {
		return testCase.getTest_case_id();
	}

	public String getYamlFilePath() {
		return yamlFilePath;
	}

	public void setYamlFilePath(String yamlFilePath) {
		this.yamlFilePath = yamlFilePath;
	}
	
	public void loadYaml() {
		YamlReader reader = null; 
		try {
			String path =System.getProperty("user.dir")+"/conf/"+this.yamlFilePath;
			System.out.println(path);
			FileReader fr = new FileReader(path); 

			reader = new YamlReader(fr);
			reader.getConfig().setPropertyElementType(TestCase.class, "testSteps", TestStep.class);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			testCase = reader.read(TestCase.class);
		} catch (YamlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(testCase.getTest_case_id());
	}

	public TestRunner getTestRunner() {
		return testRunner;
	}

	public void setTestRunner(TestRunner testRunner) {
		this.testRunner = testRunner;
	}

	private String yamlFilePath = "";
	
	private TestRunner testRunner;
	
	public void runTest(AppiumDriver driver) {
		TestStepExec testcase = new TestStepExec(driver, testCase);
		try {
			testcase.run();
			backToHome(driver);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String toString() {
		return this.getTest_case_id();
	}
	
    public void backToHome(AppiumDriver driver) {
    	String xpath="//android.widget.ImageButton[@content-desc='ÏòÉÏžgÓ[']";
    	By by = By.xpath(xpath);
        MobileElement   backBtn;
    	do {
    		backBtn= (MobileElement) driver.findElement(by);	
        	     
        	 try {
        		 if (backBtn !=null)backBtn.click(); 
				Thread.sleep(2000);
				
				 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}while(driver.findElements(by).size()>0);
    }
    
}
	
