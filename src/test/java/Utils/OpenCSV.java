package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.List;

import AppiumSupport.Config;
import au.com.bytecode.opencsv.CSVReader;

public class OpenCSV {
	
	private static File[] files;
	private static String fileExtension=".csv";
	private String filename;
	private String filepath;
	
    public static void main(String[] args) throws Exception {
        OpenCSV oc = new OpenCSV();
        oc.CSVReadAll();

      
    }

    public String dir = System.getProperty("user.dir") + "/conf";

    public void CSVReadAll() throws Exception {
        File csv = new File(dir , "TestCaseList.csv");

        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(csv), "UTF-8"), ',');
        List<String[]> list = reader.readAll(); //此时读取的已经是第二行了
        
        
        for (String[] s : list) {
            System.out.print(s + ",");
            
        }

    }
    
    public File[] getFileList(){
		File dir = new File(getTargetCsvFilePath());
		File[] files = dir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(fileExtension);
		    }
		});
		return files;
	}
    
	public String getTargetCsvFilePath(){
		
		Config instance=Config.getInstance();
		return instance.getProperty("resource.csv");
		
	}

}
