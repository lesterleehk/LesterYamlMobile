package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class OpenCSV {
	
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

}
