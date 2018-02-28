package PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import model.CheckPoint;
import model.TestCase;
import model.TestStep;

public class TestStepExec {
	
	
	AppiumDriver driver;
    private final int TIMEOUT = 60;
    private static final String NAME  = "name";
    private static final String XPATH  = "xpath";
    private static final String CLASSNAME  = "className";
    private static final String CSSSELECTOR  = "cssSelector";
    private static final String ID  = "id";
    WebDriverWait wait;
    By backBy=By.xpath("//android.widget.ImageButton[@content-desc='���Ϟg�[']");
    
    private AppContext appContext;
    private TestCase testCase;



	public TestStepExec(AppiumDriver driver, TestCase testCase) {
		   this.driver = driver;
	        this.appContext = new AppContext(driver);
	        this.testCase=testCase;
	        wait = new WebDriverWait(this.driver,TIMEOUT);
	}

	/***
     *  ִ�в���
     * TestCase ʵ����
     * @param testCase
     */
    public boolean run() throws InterruptedException {
    	List<TestStep> steps =  testCase.getTestSteps();
    	for (TestStep step: steps) {
    		
            MobileElement element;
          
            By by = getElement(step.getFind_type(), step.getElement_info());
            String checkingText;
            CheckPoint cp =step.getCheckpoint();
            if (!(cp==null)) {// has checkpoint do checking with element
	            if (!cp.getTextPresented().isEmpty()) {
	            	element = setElement(by, driver);
	        		checkingText =element.getText().toLowerCase();
	                System.out.println("checkingText "+checkingText );
	                 String expected = cp.getTextPresented().toLowerCase();
	            	if (!checkingText.contains(expected))
	            		throw new RuntimeException("fail in text expectation");
	            }
	            if (!cp.getElementPresented().isEmpty()) {
	            	if (driver.findElements(by).size()<1)
	            		throw new RuntimeException(by.toString()+"element should presented");
	            }
	            if (!cp.getElementNotPresented().isEmpty()) {
	            	if (driver.findElements(by).size()>0)
	            		throw new RuntimeException(by.toString()+"element should not presented");
	            }
            }else {
	            element = setElement(by, driver);
				switch (step.getOperate_type()) {
	                case "click": { // ���
	                	element.click();
	                    System.out.println("�����==" + step.getElement_info());
	                    break;
	                }
	                case "send_keys": {  //��������
	                	element.clear();
	                    element.sendKeys(step.getText());
	                    System.out.println(step.getElement_info() + ":��������==" + step.getText());
	                    break;
	                }
	               
	                case "touch":
	                	System.out.println("�����==" + step.getElement_info());
	                	break;
	                case "keyevent":
	                	break;
	                default:
	                    System.out.println("��֧�ֲ�������=" + step.getElement_info());
	                    System.out.println("��֧�ֲ�������=" + step.getOperate_type());
	                    break;
	            }
            }
        }
		
    	
        return true;
    }
    
    /***
     * �õ�Ԫ��
     * @param find_type (id,xpath,name,classname)
     * @param element_info ����Ԫ����Ϣ
     * @return By
     */
    public By getElement(String find_type, String element_info){
        By by = null;
        switch (find_type) {
            case ID:
                by = By.id(element_info);

                break;
            case NAME:
                by = By.name(element_info);

                break;
            case XPATH:
                by = By.xpath(element_info);

                break;
            case CSSSELECTOR:
                by = new By.ByCssSelector(element_info);
                break;
            default:
                System.out.println("��֧��������������" + element_info);
                break;
        }
        return by;
    }
    
    public MobileElement setElement(By by, AppiumDriver driver){
    	MobileElement webElement = (MobileElement) driver.findElement(by);
        return webElement;

    }



}
