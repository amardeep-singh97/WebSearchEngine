package searchEngine;

import textprocessing.Sequences;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class SpellCheck {
	
	private static ArrayList<String> createDict(){
		ArrayList<String> arr = new ArrayList<String>();
		try {
	            BufferedReader br =  new BufferedReader(new FileReader("src\\Dictionary.txt"));
	            String line = br.readLine();
	            while (line != null) {
	                arr.add(line);
	                line = br.readLine();
	            }
		}catch(Exception e) {
			System.out.println("Error while accessing Dictionary "+ e);
		}
		return arr;
	}
	
	
    public static ArrayList<String> correction(String word){
        ArrayList<String> wordCorrection = new ArrayList<String>();
        try{
            ArrayList<String> ar = new ArrayList<String>();
            int i=0;
            int d=0;
            ar = createDict();

            if (!ar.contains(word)) {
            	
                for (i=0;i<ar.size();i++) {
                    d= Sequences.editDistance(word, ar.get(i));
                    if(d==1) {
                        wordCorrection.add(ar.get(i));
                    }
                }

            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return wordCorrection;
    }
    
    public static boolean check(String letter) {
    	ArrayList<String> ar = new ArrayList<String>();
    	ar = createDict();
    	if(ar.contains(letter)) {
    		return true;
    	}else {
    		return false;
    	}
    	
    }
}
