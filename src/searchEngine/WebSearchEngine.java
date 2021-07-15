package searchEngine;

import java.io.File;
import java.io.IOException;

public class WebSearchEngine {

	public static void main(String[] args) throws IOException {
		File file = new File("downloadedPages//");
		File[] files = file.listFiles();
		if(files == null || files.length == 0) {
			System.out.println("Crawling Started... Saving web pages");
			Crawler cr = new Crawler();
			cr.start();
		}
		Searcher.start();
	}
}
