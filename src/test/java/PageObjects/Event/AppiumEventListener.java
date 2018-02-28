package PageObjects.Event;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import AppiumSupport.Config;
import AppiumSupport.TraceLog;
import io.appium.java_client.events.api.general.AppiumWebDriverEventListener;

/** 
 * @version:1.0 
 * @description:appium�¼����� 
 * @author songer.xing 
 * @date:2017-4-19 
 * @history: 
 */  
  
public class AppiumEventListener implements AppiumWebDriverEventListener {  
  

    private String locator = null;  

    private String splitElement(WebElement element) {  
        String str = element.toString().split("-> ")[1];  
        return str.substring(0, str.length() - 1);  
    }  
  
//    private String splitBy(By by) {  
//        String str = by.toString().split("-")[1].toString().split(":")[3];  
//        return str.substring(0, str.length() - 3);  
//    }


	public void afterAlertAccept(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void afterAlertDismiss(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
		// TODO Auto-generated method stub
		
		TraceLog.logger.log(Level.INFO,"�ؼ�:" + splitElement(arg0) + "��ֵ�Ѹı�");  
		
	}

	
	public void afterClickOn(WebElement arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		TraceLog.logger.log(Level.INFO,"after ���:"+splitElement(arg0));
		
	}

	
	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		// TODO Auto-generated method stub
		
	}

	
	public void afterNavigateBack(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void afterNavigateForward(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void afterNavigateRefresh(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void afterNavigateTo(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	
	public void afterScript(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	
	public void beforeAlertAccept(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void beforeAlertDismiss(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
		// TODO Auto-generated method stub
		TraceLog.logger.log(Level.INFO,"׼���ı�ؼ�:" +splitElement(arg0)+"��ֵ");  
		
	}

	
	public void beforeClickOn(WebElement arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		TraceLog.logger.log(Level.INFO,"׼�����:"+splitElement(arg0));
	}

	
	    public void beforeFindBy(final By by, WebElement element, WebDriver driver) {  
	         
	        try {  
	            WebDriverWait wait = new WebDriverWait(driver,  
	            		Long.parseUnsignedLong(Config.getInstance().getProperty("wait_seconds")) );  
	            wait.until(new ExpectedCondition<WebElement>() {  
	               
	                public WebElement apply(WebDriver driver) {  
	                    return driver.findElement(by);  
	                }  
	            }).isDisplayed();  
	            TraceLog.logger.log(Level.INFO,"beforeFindBy:seaching............"  
	                    + driver.findElement(by));  
	        } catch (Exception e) {  
	           /* try {  
	            	TraceLog.logger.log(Level.SEVERE,"beforeFindBy����" +  Config.getInstance().getProperty("wait_seconds") 
	                        + "��" + " " + splitElement(element) + "���ɼ�");  
	            } catch (Exception e2) {  
	            	TraceLog.logger.log(Level.SEVERE,"beforeFindBy����" + Config.getInstance().getProperty("wait_seconds")  
	                        + "��,by���ܰ���ʽ�и");  
	            }  */
	        }  
	    }  


	public void beforeNavigateBack(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}


	public void beforeNavigateForward(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}


	public void beforeNavigateRefresh(WebDriver arg0) {
		// TODO Auto-generated method stub
		
	}


	public void beforeNavigateTo(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	
	public void beforeScript(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}

	
	public void onException(Throwable arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
		
	}


	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		TraceLog.logger.log(Level.INFO,"׼���ı�ؼ�:" +splitElement(element)+"��ֵ");  
	}

	
	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
		TraceLog.logger.log(Level.INFO,"�ؼ�:" + splitElement(element) + "��ֵ�Ѹı�");  
		
	}  
  
}  