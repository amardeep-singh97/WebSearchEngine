package searchEngine;

import textprocessing.TST;

import javax.swing.*;
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

    public static void start (){
        Indexer t = new Indexer();
        searchEngine.SpellCheck spellCheck = new searchEngine.SpellCheck();
        Scanner scanner = new Scanner(System.in);
        int check =1;
        int s;

        while(check==1) {
            System.out.println("Enter the word to search");
            String word = scanner.nextLine().toLowerCase();
            HashMap<String, Integer> hm = sortingHashMap(getFrequency(word));
            List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

            if (list.size() == 0) {
                System.out.println();
                System.out.println("Not found");
                spellCheck.check(word);
            }
            if (list.size() > 0) {
                for (int i = 0; i < 7; i++) {
                    System.out.println(list.get(i));
                }
                try {
                    AutoSuggestion.startSuggestion(word);
                } catch (Exception e) {
                    System.out.println("What?");
                }
            }

            System.out.println("\nType 1 to search for another word or Type 0 to exit");

            while(!scanner.hasNextInt())
            {
                System.out.println();
                System.out.println("Try again!! Type 1 to search for another word or Type 0 to exit");
                scanner.next();
            }
            check = scanner.nextInt();
            if (check == 0) {
                System.out.println();
                System.out.println("Hope to see you soon. Good Bye!!");
                break;
            }
            else if(check > 1){
                System.out.println();
                System.out.println("Please enter either 1 to search another word or 0 to exit");
                check = scanner.nextInt();
            }
            else {
                word = scanner.nextLine().toLowerCase();
            }
        }
    }

    public static void GUI() throws IOException {
        final Searcher<?> srch = new Searcher();
        File wholeFolder = new File("ConvertedText");
        File[] List_Of_Files = wholeFolder.listFiles();

        for (int i = 0; i < List_Of_Files.length; i++) {
            srch.indexFile(List_Of_Files[i]);
        }

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

        Image icon = Toolkit.getDefaultToolkit().getImage("src\\browser.png");

        scrollPane.setBounds(50,155, 750, 250);

        autoSuggestion.setBounds(50,135,500,15);

        result.setBounds(50,115,500,15);

        searching.setBounds(50,100,500,15);

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

        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e){
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
                String wordToSearch = textfield.getText();
                try {
                    if(wordToSearch.length()==0) {
                        result.setText("Please Enter a word");
                        result.setVisible(true);
                    }else {
                        ArrayList<String> lis = AutoSuggestion.startSuggestion(wordToSearch);
                        String[] al = wordToSearch.split(" ");
                        HashMap<String, Integer> hm = sortingHashMap(getFrequency(wordToSearch));
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
                            ArrayList<String> spelllist = SpellCheck.correction(wordToSearch);
                            for(int i=0; i<spelllist.size();i++) {
                                if(i==spelllist.size()-1) {
                                    correction += spelllist.get(i);
                                }else {
                                    correction += spelllist.get(i)+ " or ";
                                }
                            }
                            wordExist = SpellCheck.check(wordToSearch);
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
                            result.setText("Results not Found for : " + wordToSearch);
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

                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

}
