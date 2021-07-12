package searchEngine;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.net.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Crawler {
	private static final int DEPTH = 2;
	private static final String URL = "https://en.wikipedia.org";
	private Set<String> visitedURL = new HashSet<String>();
    URL uri;
    InputStream is = null;
    DataInputStream dis;
    String line;
    int count = 0;
    FileWriter fw;
	
	public void start() throws IOException {
		crawl(1, URL);
	}

	private Document requestPage(String url) {
		try {
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			if(con.response().statusCode() == 200) {
				System.out.println(url);
				visitedURL.add(url);
				writeFile(url);
				return doc;
			}
			return null;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	private void writeFile(String url) throws IOException {
		try {
		uri = new URL(url);
		is = uri.openStream();
		dis = new DataInputStream(new BufferedInputStream(is));
		line = dis.readLine();
		fw = new FileWriter("downloadedPages\\"+ count +".html");
		count+=1;
		while(line != null) {
			fw.write(line+ System.lineSeparator());
			line = dis.readLine();				}
		} catch (Exception e ) {
			e.setStackTrace(null);
		} finally {
			fw.close();
			dis.close();
			is.close();
		}
	}
	
	private void writeFileText(String url) throws IOException {
		try {
		uri = new URL(url);
		is = uri.openStream();
		dis = new DataInputStream(new BufferedInputStream(is));
		line = dis.readLine();
		fw = new FileWriter("downloadedPages\\"+ count +".html");
		count+=1;
		while(line != null) {
			fw.write(line+ System.lineSeparator());
			line = dis.readLine();				}
		} catch (Exception e ) {
			e.setStackTrace(null);
		} finally {
			fw.close();
			dis.close();
			is.close();
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
