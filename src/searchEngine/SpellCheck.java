package searchEngine;

import textprocessing.Sequences;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class SpellCheck {
    public void check(String word){
        try{
            ArrayList<String> arr = new ArrayList<String>();
            int i=0;
            int d=0;;
            BufferedReader br =  new BufferedReader(new FileReader("src\\Dictionary.txt"));
            String line = br.readLine();
            while (line != null) {
                arr.add(line);
                line = br.readLine();
            }

            if (arr.contains(word)) {

            }
            else {
                for (i=0;i<arr.size();i++) {
                    d= Sequences.editDistance(word, arr.get(i));
//					System.out.println(d);
                    if(d==1) {
                        System.out.println("Did you mean \""+arr.get(i)+"\"?");
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
