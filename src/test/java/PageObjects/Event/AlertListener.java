package PageObjects.Event;  
  
import java.util.logging.Level;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.security.Credentials;

import AppiumSupport.TraceLog;
import JunitTest.TestRunner;
import io.appium.java_client.events.api.general.AlertEventListener;

  
public class AlertListener implements AlertEventListener {  
	
	
  
    public void afterAlertAccept(WebDriver arg0, Alert arg1) {  
        // TODO �Զ����ɵķ������ 
    	
    	TraceLog.logger.log(Level.INFO,"test afterAlertAccept");
          
    }  
  
    public void afterAlertDismiss(WebDriver arg0, Alert arg1) {  
        // TODO �Զ����ɵķ������  
          
    }  
  
    public void afterAlertSendKeys(WebDriver arg0, Alert arg1, String arg2) {  
        // TODO �Զ����ɵķ������  
          
    }  
  
    public void afterAuthentication(WebDriver arg0, Alert arg1, Credentials arg2) {  
        // TODO �Զ����ɵķ������  
          
    }  
  
    public void beforeAlertAccept(WebDriver arg0, Alert arg1) {  
        // TODO �Զ����ɵķ������  
          
    }  
  
    public void beforeAlertDismiss(WebDriver arg0, Alert arg1) {  
        // TODO �Զ����ɵķ������  
          
    }  
  
    public void beforeAlertSendKeys(WebDriver arg0, Alert arg1, String arg2) {  
        // TODO �Զ����ɵķ������  
          
    }  
  
    public void beforeAuthentication(WebDriver arg0, Alert arg1,  
            Credentials arg2) {  
        // TODO �Զ����ɵķ������  
          
    }  
  
}  
