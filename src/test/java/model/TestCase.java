package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

public class TestCase {

	private String user_id;
	private String user_pwd;
	private String test_case_id;
	private List<TestStep> testSteps;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public String getTest_case_id() {
		return test_case_id;
	}
	public void setTest_case_id(String test_case_id) {
		this.test_case_id = test_case_id;
	}

	public List<TestStep> getTestSteps() {
		return testSteps;
	}
	public void setTestSteps(List<TestStep> testSteps) {
		this.testSteps = testSteps;
	}
	public static void main(String[] args) {
		YamlReader reader = null; 
		try {
			String path =System.getProperty("user.dir")+"/conf/ContactSync.yaml";
			System.out.println(path);
			FileReader fr = new FileReader(path); 

			reader = new YamlReader(fr);
			//only array need to set as below
			reader.getConfig().setPropertyElementType(TestCase.class, "testSteps", TestStep.class);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestCase testCase = null;
		try {
			testCase = reader.read(TestCase.class);
		} catch (YamlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List a = (List)testCase.getTestSteps();
		TestStep s=((TestStep)a.get(0));
		
		CheckPoint cp =s.getCheckpoint();
		System.out.println(cp.getElementPresented());
	}


}
