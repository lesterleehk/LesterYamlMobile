package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import AppiumSupport.Config;
import Test.TestSuiteContainer;
import au.com.bytecode.opencsv.CSVReader;

public class OpenCSV {
	
	private static File[] files;
	private static String fileExtension=".csv";
	private String filename;
	private String filepath;
	
    public static void main(String[] args) throws Exception {
        OpenCSV oc = new OpenCSV();
        

      
    }

    public String yamlDir = System.getProperty("user.dir") + "/Yaml/";

    public ArrayList<TestSuiteContainer> CreateAllTestSuiteContainer(String csvFile)  {
        
    	ArrayList<TestSuiteContainer> testcases = new ArrayList<TestSuiteContainer>();
    	
//    	ArrayList<File> files= new ArrayList<File>();
//    	for (String fileName:fileNameList) {
//    		files.add(new File(yamlDir+fileName));
//    	}
    	
    	File file= new File(System.getProperty("user.dir")+"/conf/TestSuiteList.csv");
    	
    		try {
    			CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file), "UTF-8"), ',');
				List<String[]> list;
				list = reader.readAll();
				
				String[] s;
				for (int i =0; i<list.size(); i++) {
					s= list.get(i);
					int size = s.length;
					List<String> yamlpath=new ArrayList<String>();;
					
					for (int y=1; y<size; y++) {
						yamlpath.add(s[y]);
					}
					
					testcases.add(new TestSuiteContainer(s[0], yamlpath));
				}
			
			}catch (UnsupportedEncodingException | FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            
    
    	return testcases;

    }
    

}
