package searchEngine;

import textprocessing.TST;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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
}
