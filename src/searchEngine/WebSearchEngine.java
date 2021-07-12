package searchEngine;

import java.io.File;
import java.io.IOException;

public class
qwWebSearchEngine {

	public static void main(String[] args) throws IOException {
		File file = new File("downloadedPages//");
		File[] files = file.listFiles();
		if(files == null) {
			Crawler cr = new Crawler();
			cr.start();
		} else {
			System.out.println("Converting Web Pages.....");
			HTMLToText htmltext = new HTMLToText(files);
			htmltext.start();
			System.out.println("Done.");
		}
		Searcher srch = new Searcher();
		srch.start();
	}
}
