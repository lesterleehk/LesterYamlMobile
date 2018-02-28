package PageObjects.Event;

import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import AppiumSupport.TraceLog;
import JunitTest.TestRunner;
import io.appium.java_client.events.api.general.ElementEventListener;  
  
public class ElementListener implements  
        ElementEventListener {  
	
	
  
    public void beforeClickOn(WebElement arg0, WebDriver arg1) {  
  
        //messages.add("Attempt to click on the element");  
    	
    	TraceLog.logger.log(Level.INFO,"׼�����:"+splitElement(arg0));
    
    }  
      
    public void afterClickOn(WebElement arg0, WebDriver arg1) {  
        //messages.add("The element was clicked");  
    	TraceLog.logger.log(Level.INFO,"���:" +splitElement(arg0));  
  
    }  
  
    public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {  
        //messages.add("Attempt to change value of the element");  
    	TraceLog.logger.log(Level.INFO,"׼���ı�ؼ�:" +splitElement(arg0)+"��ֵ");  
          
  
    }  
  
    public void afterChangeValueOf(WebElement arg0, WebDriver arg1) {  
        //messages.add("The value of the element was changed");  
    	TraceLog.logger.log(Level.INFO,"�ؼ�:" + splitElement(arg0) + "��ֵ�Ѹı�");  
  
    }  
  
//  @Override  
//  protected void add() {  
//      // SingleListeners.listeners.put(ElementListener.class, this);  
//  
//  }  
  
    
    public void beforeChangeValueOf(WebElement element, WebDriver driver,  
            CharSequence[] keysToSend) {  
        // TODO �Զ����ɵķ������  
  
    }  
  
 
    public void afterChangeValueOf(WebElement element, WebDriver driver,  
            CharSequence[] keysToSend) {  
        // TODO �Զ����ɵķ������  
  
    }  
      //��ȡ�����Ŀؼ��ַ���  
      private String splitElement(WebElement element) {    
            String str = element.toString().split("-> ")[1];    
            return str.substring(0, str.length() - 1);    
        }    
}  
