package searchEngine;
import java.io.FileWriter;

import org.jsoup.nodes.Document;

public class HTMLToText {
	public static void writeDocuments(Document doc) {
		System.out.println("Saving document: " + doc.title());
		convertToHtml(doc);
		convertToText(doc);
	}
	
	private static void convertToHtml(Document doc) {
		String name = doc.title().replace('/', '-');
		try {
			FileWriter htmlfile = new FileWriter("downloadedPages\\"+name+".html"); //Creating new html file.
			htmlfile.write(doc.toString()); //writing the file with document i parsed using jsoup
			htmlfile.close(); 
		}
		catch(Exception e){
			System.out.println("Error Occured while saving the html file"+ name);
		}
	}
	
	private static void convertToText(Document doc) {
		String name = doc.title().replace('/', '-');
		try {
			FileWriter textfile = new FileWriter("ConvertedText\\"+name+".txt"); //Creating new text file.
			textfile.write(doc.body().text().toLowerCase()); //writing the file with document i parsed using jsoup
			textfile.close(); 
		}catch(Exception e){
			System.out.println("Error Occured while parsing the text file"+ name); 
		}
	}
}
