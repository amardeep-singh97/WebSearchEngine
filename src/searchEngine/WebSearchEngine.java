package searchEngine;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class WebSearchEngine {
	Image smallIcon = Toolkit.getDefaultToolkit().getImage("src\\browser.png");  
	JFrame frame;
	
	public static void main(String[] args) throws IOException {
		WebSearchEngine engine = new WebSearchEngine();
		engine.initialRun();
	}
	
	private void initialRun() {
		
		frame = new JFrame("Welcome");
		frame.setBounds(100, 100, 450, 505);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel headline = new JLabel("Welcome to Titan Browser");
		headline.setFont(new Font("Calibri", Font.PLAIN, 25));
		headline.setBounds(76, 11, 280, 31);
		frame.getContentPane().add(headline);
		
		JLabel member1 = new JLabel("Developed by - Amardeep Singh");
		member1.setFont(new Font("Calibri", Font.PLAIN, 14));
		member1.setBounds(117, 40, 182, 14);
		frame.getContentPane().add(member1);
		
		JLabel member2 = new JLabel(" Harjinder Singh");
		member2.setFont(new Font("Calibri", Font.PLAIN, 14));
		member2.setBounds(165, 57, 103, 14);
		frame.getContentPane().add(member2);
		
		JLabel member3 = new JLabel("Kanika Aggarwal");
		member3.setFont(new Font("Calibri", Font.PLAIN, 14));
		member3.setBounds(165, 75, 103, 14);
		frame.getContentPane().add(member3);
		
		JLabel member4 = new JLabel(" Manik Sharma\r\n");
		member4.setFont(new Font("Calibri", Font.PLAIN, 14));
		member4.setBounds(165, 94, 91, 14);
		frame.getContentPane().add(member4);
		
		JLabel member5 = new JLabel("Tanya Aggarwal");
		member5.setFont(new Font("Calibri", Font.PLAIN, 14));
		member5.setBounds(165, 111, 91, 14);
		frame.getContentPane().add(member5);
		
		frame.setIconImage(smallIcon);
		
		JButton startBrowser = new JButton("Start Browser");
		startBrowser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File textFolder = new File("ConvertedText//");
				File[] textFiles = textFolder.listFiles();
				try {
					if(textFiles.length<2) {
						dialogBoxRunBrowser();
					}else {
						Indexer.GUI();
						frame.dispose();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		startBrowser.setFont(new Font("Calibri", Font.PLAIN, 14));
		startBrowser.setBounds(151, 398, 127, 38);
		frame.getContentPane().add(startBrowser);
		
		JLabel checkLabel = new JLabel("Check if you have enough data to Start Browser");
		checkLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		checkLabel.setBounds(76, 136, 287, 23);
		frame.getContentPane().add(checkLabel);
		
		JButton checkButton = new JButton("Check");
		checkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File htmlFolder = new File("downloadedPages//");
				File textFolder = new File("ConvertedText//");
				File localFolder = new File("localHtmlPages//");
				
				File[] htmlFiles = htmlFolder.listFiles();
				File[] textFiles = textFolder.listFiles();
				File[] localFiles = localFolder.listFiles();
				
				if(htmlFiles.length<=2&&textFiles.length<=2) {
					dialogBoxCrawler();
				}else if(textFiles.length>=2&&htmlFiles.length<=2) {
					limitedDataBox();
				}else if(textFiles.length>=2&&textFiles.length<=130&&htmlFiles.length>=2) {
					limitedDataBox2();
				}else {
					enoughDataPresentBox();
				}
			}
		});
		checkButton.setFont(new Font("Calibri", Font.PLAIN, 14));
		checkButton.setBounds(165, 171, 89, 31);
		frame.getContentPane().add(checkButton);
		
		ImageIcon icon = new ImageIcon("src\\browser.png");
		icon.setImage(getScaledImage(icon.getImage(),170,170));
		JLabel panel = new JLabel(icon);
		panel.setBounds(112, 213, 198, 174);
		
		frame.getContentPane().add(panel);
		
		frame.setVisible(true);
	}
	
	private final void limitedDataBox2() {
		JPanel contentPanellimitedData2 = new JPanel();
		JDialog limitedDataBox2 = new JDialog();
		limitedDataBox2.setIconImage(smallIcon);
		limitedDataBox2.setBounds(100, 100, 444, 194);
		limitedDataBox2.getContentPane().setLayout(new BorderLayout());
		contentPanellimitedData2.setBorder(new EmptyBorder(5, 5, 5, 5));
		limitedDataBox2.getContentPane().add(contentPanellimitedData2, BorderLayout.CENTER);
		contentPanellimitedData2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Seems like you haven't converted local html files");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 418, 25);
		contentPanellimitedData2.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limitedDataBox2.dispose();
			}
		});
		btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 13));
		btnNewButton.setBounds(323, 112, 89, 32);
		contentPanellimitedData2.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Please click on Convert");
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 46, 270, 25);
		contentPanellimitedData2.add(lblNewLabel_1);
		
		JButton btnNewButton_1 = new JButton("Convert");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File localHtmlFolder = new File("localHtmlPages//");
				File[] listlocalfiles = localHtmlFolder.listFiles();
				Thread textConversionThread = new Thread() {
					public void run() {
						HTMLToText.start(listlocalfiles);
					}
				};
				textConversionThread.start();
				limitedDataBox2.dispose();
			}
		});
		btnNewButton_1.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnNewButton_1.setBounds(215, 112, 98, 32);
		contentPanellimitedData2.add(btnNewButton_1);
		
		limitedDataBox2.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		limitedDataBox2.setVisible(true);
	}
	
	private final void limitedDataBox() {
		JPanel contentPanellimitedData = new JPanel();
		JDialog limitedDataBox = new JDialog();
		limitedDataBox.setIconImage(smallIcon);
		limitedDataBox.setBounds(100, 100, 414, 194);
		limitedDataBox.getContentPane().setLayout(new BorderLayout());
		contentPanellimitedData.setBorder(new EmptyBorder(5, 5, 5, 5));
		limitedDataBox.getContentPane().add(contentPanellimitedData, BorderLayout.CENTER);
		contentPanellimitedData.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("You have limited Data");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 215, 25);
		contentPanellimitedData.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Crawl");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread crawlerThread1 = new Thread() {
					public void run() {
						Crawler cr = new Crawler();
						try {
							cr.start();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				};
				crawlerThread1.start();
				limitedDataBox.dispose();
			}
		});
		btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 13));
		btnNewButton.setBounds(299, 112, 89, 32);
		contentPanellimitedData.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("You can run browser with this amount of data");
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 33, 270, 25);
		contentPanellimitedData.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("You can choose to crawl to fetch more data from internet");
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(10, 69, 334, 25);
		contentPanellimitedData.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("OR");
		lblNewLabel_3.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(106, 57, 46, 14);
		contentPanellimitedData.add(lblNewLabel_3);
		
		JButton btnNewButton_1 = new JButton("Run");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Indexer.GUI();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame.dispose();
				limitedDataBox.dispose();
				
				//do
			}
		});
		btnNewButton_1.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnNewButton_1.setBounds(191, 112, 98, 32);
		contentPanellimitedData.add(btnNewButton_1);
		
		limitedDataBox.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		limitedDataBox.setVisible(true);
	}
	
	private final void enoughDataPresentBox() {
		JPanel contentPanelenoughData = new JPanel();
		JDialog enoughDataPresentBox = new JDialog();
		enoughDataPresentBox.setIconImage(smallIcon);
		enoughDataPresentBox.setBounds(100, 100, 414, 156);
		enoughDataPresentBox.getContentPane().setLayout(new BorderLayout());
		contentPanelenoughData.setBorder(new EmptyBorder(5, 5, 5, 5));
		enoughDataPresentBox.getContentPane().add(contentPanelenoughData, BorderLayout.CENTER);
		contentPanelenoughData.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enough Data is Present");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 215, 25);
		contentPanelenoughData.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enoughDataPresentBox.dispose();
			}
		});
		btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 13));
		btnNewButton.setBounds(299, 74, 89, 32);
		contentPanelenoughData.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("You can run browser");
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 36, 137, 25);
		contentPanelenoughData.add(lblNewLabel_1);
		
		enoughDataPresentBox.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		enoughDataPresentBox.setVisible(true);
	}
	
	private final void dialogBoxRunBrowser() {
		JPanel contentPanel = new JPanel();
		JDialog box = new JDialog();
		box.setIconImage(smallIcon);
		
		box.setBounds(100, 100, 466, 175);
		box.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		box.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("You don't have enough data to run the Browser");
			lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
			lblNewLabel.setBounds(10, 11, 430, 41);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Please click on Check before running the browser");
			lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 14));
			lblNewLabel_1.setBounds(10, 53, 279, 14);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			box.getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						box.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				box.getRootPane().setDefaultButton(okButton);
			}
		}
		
		box.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		box.setVisible(true);
	}
	
	private final void dialogBoxCrawler() {
		JPanel contentPanelCrawler = new JPanel();
		JDialog boxCrawler = new JDialog();
		boxCrawler.setIconImage(smallIcon);
		
		boxCrawler.setBounds(100, 100, 466, 191);
		boxCrawler.getContentPane().setLayout(new BorderLayout());
		contentPanelCrawler.setBorder(new EmptyBorder(5, 5, 5, 5));
		boxCrawler.getContentPane().add(contentPanelCrawler, BorderLayout.CENTER);
		contentPanelCrawler.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Data is missing");
			lblNewLabel.setBounds(10, 11, 430, 41);
			lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
			contentPanelCrawler.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("You can either click on crawl to fetch data from web");
			lblNewLabel_1.setBounds(10, 45, 310, 23);
			lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 14));
			contentPanelCrawler.add(lblNewLabel_1);
		}
		{
			JButton okButton = new JButton("Crawl");
			okButton.setFont(new Font("Calibri", Font.PLAIN, 13));
			okButton.setBounds(358, 109, 82, 32);
			contentPanelCrawler.add(okButton);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Thread crawlerThread = new Thread() {
							public void run() {
								Crawler cr = new Crawler();
								try {
									cr.start();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						};
						crawlerThread.start();
						boxCrawler.dispose();
					}
				});
			okButton.setActionCommand("OK");
			boxCrawler.getRootPane().setDefaultButton(okButton);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("You can click on Convert to convert local HTML files(Fast & Recommended)");
			lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 14));
			lblNewLabel_2.setBounds(10, 84, 420, 23);
			contentPanelCrawler.add(lblNewLabel_2);
		}
		{
			JButton btnNewButton = new JButton("Convert");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					File localHtmlFolder = new File("localHtmlPages//");
					File[] listlocalfiles = localHtmlFolder.listFiles();
					Thread textConversionThread = new Thread() {
						public void run() {
							HTMLToText.start(listlocalfiles);
						}
					};
					textConversionThread.start();
					boxCrawler.dispose();
				}
			});
			btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 13));
			btnNewButton.setBounds(259, 109, 89, 32);
			contentPanelCrawler.add(btnNewButton);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("OR");
			lblNewLabel_3.setFont(new Font("Calibri", Font.PLAIN, 14));
			lblNewLabel_3.setBounds(116, 70, 46, 14);
			contentPanelCrawler.add(lblNewLabel_3);
		}
		boxCrawler.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		boxCrawler.setVisible(true);
	}
	
	
	//for Resizing the image icon
	private Image getScaledImage(Image Img, int width, int height){
	    BufferedImage rimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = rimg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(Img, 0, 0, width, height, null);
	    g2.dispose();

	    return rimg;
	}
		
}
