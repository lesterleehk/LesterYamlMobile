package PageObjects;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import AppiumSupport.AppiumDriverHandler;
import AppiumSupport.TraceLog;
import AppiumSupport.AppiumDriverHandler.Context;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class AppContext {

		AppiumDriver driver;
		private static final int MAX_TIMEOUT_SECONDS = 30;
	    public AppContext(AppiumDriver driver2) {

           this.driver=driver2;
		}

		/**
	    * Switch to NATIVE_APP or WEBVIEW
	    * @param sWindow window name
	    */
	    private void switchContext(Context context) {
	    	//TraceLog.TraceLog.logger.log(Level.INFO,"Swith to Context: " + context.toString());
	    	switch(context){
		        case NATIVE_APP:
		        	switchToNativeView();
		        	break;
		        case WEBVIEW:
		        	switchToLastWebView();
		        	break;
	    	}
	    }
	    
	    public void switchToLastWebView() {
	        Set<String> contextNames = driver.getContextHandles();
	        List<String> webViewContextNames =  contextNames
	                .stream()
	                .filter(contextName -> contextName.contains("WEBVIEW_"))
	                .collect(Collectors.toList());
	        String currentContextView = "";

	        if (webViewContextNames.size() > 0){
	            currentContextView = (String) webViewContextNames
	                .toArray()[webViewContextNames.size()-1];
	            driver.context(currentContextView);
	        }
	        TraceLog.logger.log(Level.INFO,"All contexts:" + contextNames);
	        TraceLog.logger.log(Level.INFO,"All webview contexts:" + webViewContextNames);
	        TraceLog.logger.log(Level.INFO,"current context:" + driver.getContext());
	    }
	    
	    public void switchToNativeView() {
	        Set<String> contextNames = driver.getContextHandles();
	        List<String> nativeViewContextNames = contextNames
	                .stream()
	                .filter(contextName -> contextName.contains(AppiumDriverHandler.Context.NATIVE_APP.toString()))
	                .collect(Collectors.toList());
	        String currentContextView = "";

	        if (nativeViewContextNames.size() > 0) {
	            currentContextView = (String) nativeViewContextNames
	                .toArray()[nativeViewContextNames.size() - 1];
	            driver.context(currentContextView);
	        }
	       
	      TraceLog.logger.log(Level.INFO,"All contexts:" + contextNames);
	      TraceLog.logger.log(Level.INFO,"All native contexts:" + nativeViewContextNames);
	      TraceLog.logger.log(Level.INFO,"current context:" + driver.getContext());
	    }

	    public void switchToSpecificWebView(By selector) {
	        switchToLastWebView();
	        long begin = System.currentTimeMillis();
	        do {
	            try {
	                List<MobileElement> elements = driver.findElements(selector);
	                if (null != elements && elements.size() > 0) {
	                    return;
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            switchToLastWebView();
	            try {
	                Thread.sleep(300);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        } while ((System.currentTimeMillis() - begin) < MAX_TIMEOUT_SECONDS * 1000);
	    }

	    /**
	     * 滚动条移动到指定元素位置
	     *
	     */
	    public void executeScript(String elementInfo, WebElement webElement){
	        System.out.println("executeScript==");
//	        ((JavascriptExecutor) this.driver).executeScript("arguments[0].scrollIntoView();", webElement);
	        ((JavascriptExecutor) this.driver).executeScript("arguments[0].style.height='auto';arguments[0].style.width='auto';", webElement);
//	        ((JavascriptExecutor) this.driver).executeScript(elementInfo);
	    }
	


}
