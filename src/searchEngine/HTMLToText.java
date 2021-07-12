package searchEngine;

import java.io.File;
import java.io.FileWriter;

import org.jsoup.Jsoup;

public class HTMLToText {

	File[] listFiles;
	
	public HTMLToText(File[] htmlFile) {
		this.listFiles = htmlFile;
	}
	
	private void convertToText(File file) {
		org.jsoup.nodes.Document doc = null; //initializing jsoup document.
		try {
			doc = Jsoup.parse(file,"UTF-8"); //parsing file
			String name = file.getName().substring(0, file.getName().lastIndexOf('.')); //getting name of the file
			FileWriter textfile = new FileWriter("ConvertedText\\"+name+".txt"); //Creating new text file.
			textfile.write(doc.text()); //writing the file with document i parsed using jsoup
			textfile.close(); 
		}catch(Exception e){
			System.out.println("Error Occured while parsing the HTML file"); 
		}
	}
	
	public void start() {
		for(File f: listFiles) {
			convertToText(f);
		}
	}
}
