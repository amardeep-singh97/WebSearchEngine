package searchEngine;

import java.io.IOException;
import java.util.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Crawler {
	private static final int DEPTH = 2;
	private static final String URL = "https://en.wikipedia.org";
	private Set<String> visitedURL = new HashSet<String>();
	
	public void start() throws IOException {
		crawl(1, URL);
	}

	private Document requestPage(String url) {
		try {
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			if(con.response().statusCode() == 200) {
				visitedURL.add(url);
				return doc;
			}
			return null;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	private boolean filterURL(String link) {
		if(link.contains(".svg") || link.contains("#") || link.contains(".png") ||
				link.contains(".jpeg") || link.contains(".pdf") || link.contains(".gif")||
				link.contains(".jpg") || link.contains(".swf"))
			return false;
		return true;
	}
	
	private void crawl(int level, String url) {
		if(level <= DEPTH) {
			Document doc = requestPage(url);
			if(doc!=null) {
				HTMLToText.writeDocuments(doc);
				for(Element link:doc.select("a[href]")) {
					String next_link = link.absUrl("href");
					if(!visitedURL.contains(next_link) && filterURL(next_link)) {
						crawl(level++, next_link);
					}
				}
			}
		}
	}
}
