package searchEngine;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLToText {
	
	static JDialog dialog2;
	static JProgressBar progressBar2;
	static Image smallIcon = Toolkit.getDefaultToolkit().getImage("src\\browser.png"); 
	
	public static void writeHTMLDocument(Document doc) {
		//System.out.println("Saving document: " + doc.title());
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
	
	private static void convertToText(File file) {
		org.jsoup.nodes.Document doc = null; //initializing jsoup document.
		try {
			doc = Jsoup.parse(file,"UTF-8"); //parsing file
			String name = file.getName().substring(0, file.getName().lastIndexOf('.')); //getting name of the file
			FileWriter textfile = new FileWriter("ConvertedText\\"+name+".txt"); //Creating new text file.
			textfile.write(doc.text()); //writing the file with document i parsed using jsoup
			textfile.close(); 
		}catch(Exception e){
			
		}
	}
	
	
	public static void start(File[] listFiles) {
		progress();
		int len = listFiles.length;
		for(int i = 0; i<len; i++) {
			//jb.setValue(i);
			convertToText(listFiles[i]);
			if(i==len-1) {
				doneBox();
			}
		}
	}
	
	private static void progress() {
		dialog2 = new JDialog();
		dialog2.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog2.setIconImage(smallIcon);
		JPanel contentPanel = new JPanel();
		dialog2.setBounds(100, 100, 339, 136);
		dialog2.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		dialog2.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		progressBar2 = new JProgressBar(0,120);
		progressBar2.setIndeterminate(true);
		progressBar2.setBounds(59, 45, 204, 23);
		contentPanel.add(progressBar2);
		
		JLabel lblNewLabel = new JLabel("Converting");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 11, 120, 23);
		contentPanel.add(lblNewLabel);
		dialog2.setVisible(true);
	}
	
	private static void doneBox() {
		dialog2.dispose();
		JDialog doneBox = new JDialog();
		JPanel contentPanelDone = new JPanel();
		
		doneBox.setIconImage(smallIcon);
		doneBox.setBounds(100, 100, 466, 191);
		doneBox.getContentPane().setLayout(new BorderLayout());
		contentPanelDone.setBorder(new EmptyBorder(5, 5, 5, 5));
		doneBox.getContentPane().add(contentPanelDone, BorderLayout.CENTER);
		contentPanelDone.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Text Coversion Completed");
			lblNewLabel.setBounds(10, 11, 430, 41);
			lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
			contentPanelDone.add(lblNewLabel);
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
}
