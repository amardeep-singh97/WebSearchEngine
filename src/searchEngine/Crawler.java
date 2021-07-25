package searchEngine;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Crawler {
	private static final int DEPTH = 2;
	private static final String URL = "https://en.wikipedia.org";
	private Set<String> visitedURL = new HashSet<String>();
	static Image smallIcon = Toolkit.getDefaultToolkit().getImage("src\\browser.png");
	JDialog dialog;
	JProgressBar progressBar;
	
	public void start() throws IOException {
		progress();
		crawl(1, URL);
		File folder = new File("downloadedPages//");
		File[] files = folder.listFiles();
		crawlingDoneBox();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread textConversionThread = new Thread() {
			public void run() {
				HTMLToText.start(files);
			}
		};
		textConversionThread.start();
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
				link.contains(".jpg") || link.contains(".swf") || link.contains("?")||
				link.contains("mailto:") || link.contains("javascript:"))
			return false;
		return true;
	}
	
	private void crawl(int level, String url) {
		if(level <= DEPTH) {
			Document doc = requestPage(url);
			if(doc!=null) {
				HTMLToText.writeHTMLDocument(doc);
				for(Element link:doc.select("a[href]")) {
					String next_link = link.absUrl("href");
					if(!visitedURL.contains(next_link) && filterURL(next_link)) {
						File folder = new File("downloadedPages//");
						File[] files = folder.listFiles();
						progressBar.setValue(files.length);
						if(files.length>120) {
							break;
						}else {
							crawl(level++, next_link);
						}
					}
				}
			}
		}
	}
	
	private void crawlingDoneBox() {
		dialog.dispose();
		JDialog doneBox = new JDialog();
		JPanel contentPanelDone = new JPanel();
		
		doneBox.setIconImage(smallIcon);
		doneBox.setBounds(100, 100, 466, 191);
		doneBox.getContentPane().setLayout(new BorderLayout());
		contentPanelDone.setBorder(new EmptyBorder(5, 5, 5, 5));
		doneBox.getContentPane().add(contentPanelDone, BorderLayout.CENTER);
		contentPanelDone.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Crawling Completed");
			lblNewLabel.setBounds(10, 11, 430, 41);
			lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
			contentPanelDone.add(lblNewLabel);
		}
		{
			JLabel label = new JLabel("Text Coversion will start Soon");
			label.setBounds(10,45, 430, 23);
			label.setFont(new Font("Calibri", Font.PLAIN, 14));
			contentPanelDone.add(label);
		}
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doneBox.dispose();
			}
		});
		btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnNewButton.setBounds(351, 111, 89, 30);
		contentPanelDone.add(btnNewButton);
		doneBox.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		doneBox.setVisible(true);
	}
	
	private void progress() {
		dialog = new JDialog();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setIconImage(smallIcon);
		JPanel contentPanel = new JPanel();
		dialog.setBounds(100, 100, 339, 136);
		dialog.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		dialog.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		progressBar = new JProgressBar(0,120);
		progressBar.setStringPainted(true);
		progressBar.setBounds(59, 45, 204, 23);
		contentPanel.add(progressBar);
		
		JLabel lblNewLabel = new JLabel("Fetching Data");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 11, 120, 23);
		contentPanel.add(lblNewLabel);
		dialog.setVisible(true);
	}
	
	public void main(String[] args) {
		crawlingDoneBox();
	}
}
