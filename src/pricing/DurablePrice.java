package pricing;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.BrowserVersion;

import jxl.write.WriteException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import java.net.MalformedURLException;
import java.io.IOException;

import reader.ReadExcel;
import writer.WriteExcel;

import java.util.Scanner;

public class DurablePrice {
	
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		// Turn off warnings
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);
		
		// Get username and password
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the username:");
		String username = sc.next();
		
		System.out.println("Enter the password");
		String password = sc.next();    
		
		sc.close();
		
		// Set up browser
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(true);
		HtmlPage mainPage = webClient.getPage("http://duraweb.durableusa.com/Acct%20Mgmt/Login.aspx");
		
		// Get username input field and set the value
		HtmlTextInput usernameInput = mainPage.getHtmlElementById("htmUserName");
		usernameInput.setValueAttribute(username);
		
		// Get password input field and set the value
		HtmlInput passwordInput = mainPage.getHtmlElementById("htmPwd");
		passwordInput.setValueAttribute(password);
		
		// Get submit button
		HtmlInput submit = mainPage.getHtmlElementById("ctl00_ContentPlaceHolder1_btLogin");
		
		// Submit form and get result
		HtmlPage menuPage = submit.click();
		
		// Get link to click on
		HtmlAnchor priceLink = menuPage.getAnchorByHref("Customer/CustomerPriceInquiry.aspx");
		HtmlPage inquiryPage = priceLink.click();
		
		// Read data from excel file
		ReadExcel inputFile = new ReadExcel();
		inputFile.setInputFile("C:\\Users\\Schnackenberg1\\Documents\\DurableUpdate\\durableInput.xls");
		inputFile.read();
		String[] partNums = inputFile.returnArray();
		String[] oldPrices = inputFile.returnPrices();
		
		// Set up write-to file
		WriteExcel outputFile = new WriteExcel();
		outputFile.setOutputFile("C:\\Users\\Schnackenberg1\\Documents\\DurableUpdate\\durableOutput.xls");
		String[] itemPrices = new String[partNums.length];
		
		// submit part nums and retrieve the prices
		HtmlTextInput inputItem1, inputItem2, inputItem3, inputItem4, inputItem5, inputItem6, inputItem7, inputItem8;
		int length = partNums.length;
		HtmlTable table;
		
		String str = "Unit Price";
		int count = 0;
		int i = 1;
		while(i < length) {
			if(i < length) {
				inputItem1 = inquiryPage.getHtmlElementById("ctl00_ContentPlaceHolder1_tbEnterItemNo1");
				inputItem1.setValueAttribute(partNums[i]);
				i++;
			}
			if(i < length) {
				inputItem2 = inquiryPage.getHtmlElementById("ctl00_ContentPlaceHolder1_tbEnterItemNo2");
				inputItem2.setValueAttribute(partNums[i]);
				i++;
			}
			if(i < length) {
				inputItem3 = inquiryPage.getHtmlElementById("ctl00_ContentPlaceHolder1_tbEnterItemNo3");
				inputItem3.setValueAttribute(partNums[i]);
				i++;
			}
			if(i < length) {
				inputItem4 = inquiryPage.getHtmlElementById("ctl00_ContentPlaceHolder1_tbEnterItemNo4");
				inputItem4.setValueAttribute(partNums[i]);
				i++;
			}
			if(i < length) {
				inputItem5 = inquiryPage.getHtmlElementById("ctl00_ContentPlaceHolder1_tbEnterItemNo5");
				inputItem5.setValueAttribute(partNums[i]);
				i++;
			}
			if(i < length) {
				inputItem6 = inquiryPage.getHtmlElementById("ctl00_ContentPlaceHolder1_tbEnterItemNo6");
				inputItem6.setValueAttribute(partNums[i]);
				i++;
			}
			if(i < length) {
				inputItem7 = inquiryPage.getHtmlElementById("ctl00_ContentPlaceHolder1_tbEnterItemNo7");
				inputItem7.setValueAttribute(partNums[i]);
				i++;
			}
			if(i < length) {
				inputItem8 = inquiryPage.getHtmlElementById("ctl00_ContentPlaceHolder1_tbEnterItemNo8");
				inputItem8.setValueAttribute(partNums[i]);
				i++;
			}
			submit = inquiryPage.getHtmlElementById("ctl00_ContentPlaceHolder1_btGetPrice");
			//System.out.println(i); // Debug purposes
			inquiryPage = submit.click();
			
			int j = 0;
			table = inquiryPage.getHtmlElementById("ctl00_ContentPlaceHolder1_tabPriceList");
			
			for (final HtmlTableRow row : table.getRows()) {
			    j = 0;
			    for (final HtmlTableCell cell : row.getCells()) {
			    	if(j == 1) {
			    		if(count < length && !str.equals(cell.asText())) {
			    			itemPrices[count] = cell.asText();
			    			count++;
			    		}

			    	}
			    	
			    	j++;
			    }
			}
		}
		
		try {
			outputFile.writeToExcel(itemPrices, partNums, oldPrices);
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// For debugging *****
		//String pageContent = inquiryPage.asText();
		//System.out.println(pageContent);
		webClient.close();
	}
}