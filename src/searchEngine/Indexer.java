package searchEngine;

import textprocessing.TST;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class Indexer {
    Map<Integer, String> sources;
    HashMap<String, HashSet<Integer>> index;
    static JDialog searchBox ;
    static Image icon = Toolkit.getDefaultToolkit().getImage("src\\browser.png");
    
    public static String readFileAsString(String fileName)throws Exception
    {
        String strData = "";
        String strFileName = "ConvertedText\\" + fileName;
        strData = new String(Files.readAllBytes(Paths.get(strFileName)));
        return strData;
    }
    public static TST<Integer> getTST(String path) {

        File wholeFile = new File("ConvertedText/"+path);
        TST<Integer> objTST = new TST<Integer>();
        try {
                if (wholeFile.isFile()) {
                    String strFileText = readFileAsString(wholeFile.getName());
                    StringTokenizer strTokenizer = new StringTokenizer(strFileText);
                    while (strTokenizer.hasMoreTokens()) {
                        String strToken = strTokenizer.nextToken().replaceAll("[|;:.,='<>()%#@*^/&\"]", " ");
                        if (objTST.contains(String.valueOf(strToken))){
                            objTST.put(strToken.toLowerCase(),objTST.get(strToken)+1);
                        }
                        else{
                            objTST.put(strToken,1);
                        }
                    }
                }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return objTST;
    }

    public static HashMap<String, Integer>getFrequency(String word){
        ArrayList<String> textList = new ArrayList<>();
        HashMap<String,Integer > freqList = new HashMap<String, Integer>();
        int abc=0;
        File folder = new File("ConvertedText");
        File[] files = folder.listFiles();
        for (File file:files){
            textList.add(file.getName());
            TST<Integer> tst = new TST<Integer>();
            tst = Indexer.getTST(file.getName());
            int counter = 0;

            if (tst.contains(word)){
                int count = tst.get(word);
                counter = counter + count;
            }

            freqList.put(file.getName(), counter);

        }
        Integer valueToBeRemoved = 0;
        Iterator<Map.Entry<String, Integer>> iterator = freqList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            if (valueToBeRemoved.equals(entry.getValue())) {

                // Remove this entry from HashMap
                iterator.remove();
            }
        }
            return freqList;
    }

    public static HashMap<String, Integer> sortingHashMap(HashMap<String,Integer> freqList)
    {
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(freqList.entrySet());
        Collections.sort(list, (i1,i2) -> i2.getValue().compareTo(i1.getValue()));
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
    
    public static void GUI() throws IOException {

    	Indexer t = new Indexer();
    	
        JFrame jframe = new JFrame("Search Engine");
        
        jframe.setResizable(false);
        
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("Search");

        JLabel subline = new JLabel("Enter the word you want to search");
        subline.setFont(new Font("Calibri", Font.PLAIN, 14));

        final JLabel result = new JLabel();
        result.setFont(new Font("Calibri", Font.PLAIN, 14));

        final JLabel searching = new JLabel("Searching....");
        searching.setFont(new Font("Calibri", Font.PLAIN, 14));

        final JLabel autoSuggestion = new JLabel();
        autoSuggestion.setFont(new Font("Calibri", Font.PLAIN, 14));

        final JTextArea textarea = new JTextArea();

        final JScrollPane scrollPane = new JScrollPane(textarea);


        scrollPane.setBounds(50,155, 750, 250);

        autoSuggestion.setBounds(50,135,500,20);

        result.setBounds(50,115,500,20);

        searching.setBounds(50,100,500,20);

        subline.setBounds(50, 20, 250 , 20);

        button.setBounds(750,50,100, 40);

        final JTextField textfield = new JTextField();

        textfield.setBounds(50, 50, 650, 40);

        textarea.add(new JScrollPane());

        jframe.add(button);
        jframe.add(textfield);
        jframe.add(subline);
        jframe.add(result);
        jframe.add(autoSuggestion);
        jframe.add(scrollPane);
        jframe.add(searching);
        jframe.setIconImage(icon);

        searching.setVisible(false);
        scrollPane.setVisible(false);

        jframe.setSize(1000,500);//400 width and 500 height
        jframe.setLayout(new BorderLayout());//using no layout managers
        jframe.setVisible(true);

        button.addActionListener(new ActionListener() {                                  //Search Button

            public void actionPerformed(ActionEvent e){
            	Thread searchingThread = new Thread() {
            		public void run() {
            			String word = textfield.getText();
            			String[] al = word.split(" ");
            			if(al.length>1) {
            				searching.setText("You cannot search more than one word at once.");
            				searching.setVisible(true);
                            textarea.setText(null);
                            result.setVisible(false);
                            scrollPane.resetKeyboardActions();
                            autoSuggestion.setVisible(false);;
                            scrollPane.setVisible(false);
            			}else {
            				searchingBox();
            				if(scrollPane.isVisible()) {
                                //searching.setVisible(true);
                                textarea.setText(null);
                                scrollPane.resetKeyboardActions();
                                autoSuggestion.setVisible(false);;
                                scrollPane.setVisible(false);
                            }
                            if(searching.isVisible()) {
                                searching.setVisible(false);
                            }
                            try {
                                if(word.length()==0) {
                                    result.setText("Please Enter a word");
                                    result.setVisible(true);
                                    searchBox.dispose();
                                }else {
                                    ArrayList<String> lis = AutoSuggestion.startSuggestion(word);         //Auto Suggestion
                                    if(lis.contains(word)) {
                                    	lis.remove(word);
                                    }
                                    HashMap<String, Integer> hm = sortingHashMap(getFrequency(word));               //Indexing
                                    List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());
                                    if(list.size() > 0)
                                    {
                                        result.setText("Total Results Found: "+list.size());
                                        result.setVisible(true);
                                        if(lis.isEmpty()) {
                                            autoSuggestion.setText("No suggestion Found");
                                            autoSuggestion.setVisible(true);
                                        }else {
                                            autoSuggestion.setText("You can also search for: "+ lis );
                                            autoSuggestion.setVisible(true);
                                        }
                                        scrollPane.setVisible(true);
                                        for (int i = 0; i < list.size(); i++) {
                                            textarea.append((i+1)+". "+list.get(i) + System.lineSeparator());
                                        }
                                        searching.setVisible(false);
                                    }
                                    else {
                                        String correction = "";
                                        boolean wordExist;
                                        ArrayList<String> spelllist = SpellCheck.correction(word);            //Spell check
                                        for(int i=0; i<spelllist.size();i++) {
                                            if(i==spelllist.size()-1) {
                                                correction += spelllist.get(i);
                                            }else {
                                                correction += spelllist.get(i)+ " or ";
                                            }
                                        }
                                        wordExist = SpellCheck.check(word);
                                        if(spelllist.isEmpty()&&wordExist==false) {
                                        	searching.setText("Ummmmm, This doesn't seem like a word!");
                                        	result.setVisible(false);
                                            searching.setVisible(true);
                                        }else if(spelllist.isEmpty()&&wordExist==true) {
                                        	searching.setVisible(false);
                                        }
                                        else {
                                            searching.setText("Did you mean: "+ correction +" ?");
                                            searching.setVisible(true);
                                        }
                                        result.setText("Results not Found for : " + word);
                                        result.setVisible(true);
                                        if(lis.isEmpty()&&!spelllist.isEmpty()) {
                                        	autoSuggestion.setVisible(false);
                                        }
                                        else if(lis.isEmpty()&&spelllist.isEmpty()) {
                                            autoSuggestion.setText("No suggestion Found");
                                            autoSuggestion.setVisible(true);
                                        }else {
                                            autoSuggestion.setText("You can also search for: "+ lis );
                                            autoSuggestion.setVisible(true);
                                        }
                                    }
                                    searchBox.dispose();
                                }
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
            			}
            		}
            	};
            	searchingThread.start();
            	
            }
        });

    }
    
    private static void searchingMethod() {
    	
    }
    
    private static void searchingBox() {
    	searchBox = new JDialog();
    	
    	JPanel contentPanel = new JPanel();
    	searchBox.setIconImage(icon);
		searchBox.setBounds(100, 100, 339, 136);
		searchBox.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		searchBox.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Searching...");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 17));
		lblNewLabel.setBounds(10, 11, 173, 23);
		contentPanel.add(lblNewLabel);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setBounds(75, 46, 181, 23);
		contentPanel.add(progressBar);
		
		searchBox.setVisible(true);
    }

}
