package JunitTest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import AppiumSupport.AppiumDriverHandler;
import AppiumSupport.Config;
import AppiumSupport.TraceLog;
import Report.ScreenShotOnFailed;
import Report.TestReport;
import Test.TestCaseContainer;
import Utils.DateTimeUtils;
import Utils.PropertiesFileUtils;
import Utils.ReflectionUtils;
import io.appium.java_client.AppiumDriver;

@RunWith(Parameterized.class)
public class TestRunner {

	public static AppiumDriverHandler driverHandler = AppiumDriverHandler.getInstance(); 
	private static HashMap<String, TestCaseContainer> ID_TestDataObjects = new HashMap<String, TestCaseContainer>();
	private static int numFailCases = 0;
	private static int totalTestSuite = 0;
	private static int testSuiteNo = 1;
	private static int totalExecution = 0;
	//initial logon user has no appiumdriver
	private static String initialUID = "yuenlee.hk@gmail.com";
	private String user_id;

	private int thisTestNo;
	private TestCaseContainer testCaseContainer;

	public TestRunner(TestCaseContainer testCaseContainer, String objID) {
		this.testCaseContainer = testCaseContainer;
		testCaseContainer.setTestRunner(this);
		this.user_id=testCaseContainer.getTestCase().getUser_id();
		// set user
	}

	public static void addTestDataObject(String ID, TestCaseContainer obj) {
		if (ID_TestDataObjects.containsKey(ID)) {
			System.out.println("Duplicate ID:" + ID);
		} else {
			ID_TestDataObjects.put(ID, obj);
		}
	}

	public TestCaseContainer getTestDataObject() {
		return testCaseContainer;
	}

	public static TestCaseContainer getTestDataObject(String ID) {
		TestCaseContainer obj_tmp = null;
		if (ID_TestDataObjects.containsKey(ID)) {
			obj_tmp = ID_TestDataObjects.get(ID);
		}
		return obj_tmp;
	}
	

	@Rule
	public TestReport testReport = new TestReport();
	
	@Rule
	public ScreenShotOnFailed screenShootRule = new ScreenShotOnFailed();
	
	@BeforeClass
	public static void executedBeforeAllTestCases() {

	}

	@Before
	public void setUp() throws Exception {
		
	}

	private static boolean isPrevTestResultDeleted() {
		try {
			File file = new File(Config.getInstance().getProperty("test.result"));
			if (file.exists()) {
				if (file.delete()) {
					System.out.println(file.getName() + " is deleted!");
					return true;
				} else {
					System.out.println("Delete " + Config.getInstance().getProperty("test.result") + " operation is failed.");
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Transform each row in excel into java object
	@Parameters(name = "{1}")
	// name = "{1}" = Use TestDataObject.toString() as test case name
	public static Collection<Object[]> data() throws IOException {
		if (!isPrevTestResultDeleted()) {
			return null;
		}
		Collection<Object[]> objList = new ArrayList<Object[]>();
		  
		TestCaseContainer testcase = new TestCaseContainer();
		testcase.setYamlFilePath("ContactSync.yaml");
		testcase.loadYaml();
		TestCaseContainer testcase2 = new TestCaseContainer();
		testcase2.setYamlFilePath("LogoutThenLogin.yaml");
		testcase2.loadYaml();
		
		Object objectArray[]=new Object[] { testcase, testcase.getTest_case_id()};
		Object objectArray2[]=new Object[] { testcase2, testcase2.getTest_case_id()};
		objList.add(objectArray);
		objList.add(objectArray2);


		totalTestSuite = objList.size();
		return objList;
	}

	@Test(timeout = 3000000)
	public void test() {
		double startTime, endtime, totaltime;
		startTime = System.currentTimeMillis();
		// 1. print test case info (author, test case no.)
		System.out.print("Starting: (" + testSuiteNo + "/" + totalTestSuite + ")\t" + testCaseContainer.toString() + "\t");
		System.out.println(DateTimeUtils.getCurrentTimeAsStr());
		thisTestNo = testSuiteNo;
		testSuiteNo++;

		try {
			this.testReport.setAppiumDriver(driverHandler.getAppiumDriver());
			this.screenShootRule.setAppiumDriver(driverHandler.getAppiumDriver());
			
			
			// 2. execute the test case
			if (executeTestMethod(testCaseContainer, driverHandler.getAppiumDriver())) {
				// 3. do tasks after execution, will not reach this code if
				// exception occur
				// logger.succeeded(TestDataObject);
			}
		} catch (Exception e) {
			handFailCaseReporting(e, testCaseContainer);
		} finally {
			// 5. do finish task
			// logger.finished(TestDataObject);
			endtime = System.currentTimeMillis();
			totaltime = (endtime - startTime) / 1000;
			System.out.println("Finished (" + thisTestNo + "/" + totalTestSuite + ") " + testCaseContainer.toString() + " in " + totaltime + " secs ");
		}
	}

	private boolean executeTestMethod(TestCaseContainer testCaseContainer, AppiumDriver driver) throws Exception {
		boolean success = false;
		double startTime, endtime;
		
		this.testReport.setTestDataObject(testCaseContainer);
		this.screenShootRule.setTestDataObject(testCaseContainer);
		
		
		System.out.println(testCaseContainer.getTest_case_id());
		testCaseContainer.runTest(driver);
		
		return success;
	}

	private void handFailCaseReporting(Exception e, TestCaseContainer obj) {
		numFailCases++;
		System.out.println("FAILED (" + thisTestNo + "/" + totalTestSuite + ") " + testCaseContainer.toString());
		org.junit.Assert.fail(e.getMessage());
	}

	@After
	public void after() {

	}

	@AfterClass
	public static void tearDown() throws Exception {
		// close the window that uses plugin container before driver.quit();
		System.out.println("getting into tearDown now");
		// Runtime.getRuntime().exec("taskkill /F /IM plugin-container.exe");
		System.out.println("Total test case run:" + totalExecution);
		System.out.println("Total test case fail:" + numFailCases);
		System.out.println("Total test suite run:" + totalTestSuite);

		driverHandler.quitAppiumDriver();
		Hashtable<String, String> properties = new Hashtable<String, String>();
		properties.put("Total.Cases", String.valueOf(totalExecution));
		properties.put("Total.Pass.Cases", String.valueOf(totalExecution - numFailCases));
		properties.put("Total.Fail.Cases", String.valueOf(numFailCases));
		System.out.println("saving result to " + Config.getInstance().getProperty("test.result"));
		PropertiesFileUtils.SaveAsPropertiesFile(Config.getInstance().getProperty("test.result"), properties);
	}
}
