package reader;

import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcel {
	private String inputFile;
 	private String[] items;				// Holds the part nums
 	private String[] itemPrices;		// Holds the prices

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String[] returnArray() {
    	return(items);
    }
    
    public String[] returnPrices() {
    	return(itemPrices);
    }
    
    public void read() throws IOException  {
        File inputWorkbook = new File(inputFile);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            
            // Get the first sheet
            Sheet sheet = w.getSheet(0);
            
            items = new String[sheet.getRows()];
            itemPrices = new String[sheet.getRows()];
            
            for(int i = 1; i < sheet.getRows(); i++) {
            	Cell cell = sheet.getCell(0, i);
            	items[i] = cell.getContents();
            	cell = sheet.getCell(1, i);
            	itemPrices[i] = cell.getContents();
            	//System.out.println(items[i]); // used for debugging
            }
            
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }
}


