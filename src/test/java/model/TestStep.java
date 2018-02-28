package model;

import java.util.List;
import java.util.Map;

public class TestStep {
    private String element_info; ////*[@id="new_user"]/div[4]/input
    private String find_type;// id,xpath,name,classname
    private String operate_type; // click,send_keys...
    private String text; //文本框内�?,或�?�其他内�?
    
    private CheckPoint checkpoint;
	private String switch_context; 
    
    public String getSwitch_context() {
    	if (switch_context==null) {
			return "";
		}
		return switch_context;
	}

	public void setSwitch_context(String switch_context) {
		this.switch_context = switch_context;
	}
    
    public void setElement_info(String element_info) {
        this.element_info = element_info;
    }

    public void setFind_type(String find_type) {
        this.find_type = find_type;
    }

    public void setOperate_type(String operate_type) {
        this.operate_type = operate_type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getElement_info() {

        return element_info;
    }

    public String getFind_type() {
        return find_type;
    }

    public String getOperate_type() {
        return operate_type;
    }

    public String getText() {
        return text;
    }

	public CheckPoint getCheckpoint() {
		return checkpoint;
	}

	public void setCheckpoint(CheckPoint checkpoint) {
		this.checkpoint = checkpoint;
	}



}
