package model;

/***
 * 妫�鏌ョ偣瀹炰綋绫�
 */
public class CheckPoint {
    private String textPresented="";//鏆傛椂娌℃湁鐢�
    private String elementPresented=""; //妫�鏌ョ偣鏂规硶锛岀幇鍦ㄦ敮鎸乬etValue,榛樿鏄煡鎵�,鍙嚜鐢辨嫇灞�
    private String elementNotPresented=""; //妫�鏌ョ偣鏂规硶锛岀幇鍦ㄦ敮鎸乬etValue,榛樿鏄煡鎵�,鍙嚜鐢辨嫇灞�
	public String getTextPresented() {
		return textPresented;
	}
	public void setTextPresented(String textPresented) {
		this.textPresented = textPresented;
	}
	public String getElementPresented() {
		return elementPresented;
	}
	public void setElementPresented(String elementPresented) {
		this.elementPresented = elementPresented;
	}
	public String getElementNotPresented() {
		return elementNotPresented;
	}
	public void setElementNotPresented(String elementNotPresented) {
		this.elementNotPresented = elementNotPresented;
	}


   

}
