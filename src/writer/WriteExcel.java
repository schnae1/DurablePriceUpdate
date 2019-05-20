package writer;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;

public class WriteExcel {
	private String outputFile;
	
	public void setOutputFile(String writeFile) {
        this.outputFile = writeFile;
    }
	
	public void writeToExcel(String[] prices, String[] partNums, String[] oldPrices) throws IOException, WriteException{
	      File outputWorkbook = new File(outputFile);
	      WritableWorkbook w;
	            w = Workbook.createWorkbook(outputWorkbook);
	            WritableSheet sheet = w.createSheet("Sheet1", 0);
	            int length = prices.length;

	            sheet.addCell(new Label(0, 0, "Part#"));
	            sheet.addCell(new Label(1, 0, "Price"));
	            
	            for(int i = 1; i < length; i++) {
	            	sheet.addCell(new Label(0, i, partNums[i]));
	            	if(!prices[i-1].equals("No Match"))
	            		sheet.addCell(new Label(1, i, prices[i-1]));
	            	else {
	            		sheet.addCell(new Label(2, i, "No Match"));
	            		sheet.addCell(new Label(1, i, oldPrices[i]));
	            	}
	            }
	      w.write();
	      w.close();
	    }
	}
