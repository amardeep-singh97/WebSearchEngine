package searchEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

//import searchEngine.Indexer.Tuple;

//import searchEngine.Indexer.Tuple;

public class Searcher <E> {
	List<String> stopwords = Arrays.asList("a", "able", "about",
			"across", "after", "all", "almost", "also", "am", "among", "an",
			"and", "any", "are", "as", "at", "be", "because", "been", "but",
			"by", "can", "cannot", "could", "dear", "did", "do", "does",
			"either", "else", "ever", "every", "for", "from", "get", "got",
			"had", "has", "have", "he", "her", "hers", "him", "his", "how",
			"however", "i", "if", "in", "into", "is", "it", "its", "just",
			"least", "let", "like", "likely", "may", "me", "might", "most",
			"must", "my", "neither", "no", "nor", "not", "of", "off", "often",
			"on", "only", "or", "other", "our", "own", "rather", "said", "say",
			"says", "she", "should", "since", "so", "some", "than", "that",
			"the", "their", "them", "then", "there", "these", "they", "this",
			"tis", "to", "too", "twas", "us", "wants", "was", "we", "were",
			"what", "when", "where", "which", "while", "who", "whom", "why",
			"will", "with", "would", "yet", "you", "your");

	Map<String, List<Tuple>> index = new HashMap<String, List<Tuple>>();
	List<String> files = new ArrayList<String>();
	
	public void indexFile(File file) throws IOException {
		int fileno = files.indexOf(file.getPath());
		if (fileno == -1) {
			files.add(file.getPath());
			fileno = files.size() - 1;
		}
 
		int pos = 0;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		for (String line = reader.readLine(); line != null; line = reader.readLine()) {
			for (String _word : line.split("\\W+")) {
				String word = _word.toLowerCase();
				pos++;
				if (stopwords.contains(word))
					continue;
				List<Tuple> idx = index.get(word);
				if (idx == null) {	
					idx = new LinkedList<Tuple>();
					index.put(word, idx);
				}
				idx.add(new Tuple(fileno, pos));
			}
		}
		reader.close();
//		System.out.println("indexed " + file.getPath() + " " + pos + " words");
	}
	   public static HashMap<Integer, Integer> sortByValue(HashMap<Integer, Integer> hm) 
	    { 
	        // Create a list from elements of HashMap 
	        List<Map.Entry<Integer, Integer> > list = 
	               new LinkedList<Map.Entry<Integer, Integer> >(hm.entrySet()); 
	  
	        // Sort the list 
	        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer> >() { 
	            public int compare(Map.Entry<Integer, Integer> o1,  
	                               Map.Entry<Integer, Integer> o2) 
	            { 
	                return (o1.getValue()).compareTo(o2.getValue()); 
	            } 
	        }); 
	          
	        // put data from sorted list to hashmap  
	        HashMap<Integer, Integer> temp = new LinkedHashMap<Integer, Integer>(); 
	        for (Map.Entry<Integer, Integer> aa : list) { 
	            temp.put(aa.getKey(), aa.getValue()); 
	        } 
	        return temp; 
	        
	    } 
	public Set<String> search(List<String> words) {
		int count=0;
		Set<String> answer = new HashSet<String>();
		int prev=0;
		int a[]= new int[50];
		int b[] = new int[50];
		for (int j=0;j<a.length;j++)
		{
			a[j]=0;
			b[j]=0;
		}

		for (String _word : words) {
			String word = _word.toLowerCase();
			List<Tuple> idx = index.get(word);
			HashMap<Integer, Integer> hmap = new HashMap<Integer, Integer>();

				for (int k = 0;k<a.length;k++)
				{
					if(a[k]!=0)
					{
						hmap.put(k, a[k]);
					}
				}
				
//				Map<Integer, Integer> hm1 = sortByValue(hmap); 
			
			if (idx != null) {
				for (Tuple t : idx) {
					count++;
					answer.add(files.get(t.fileno));	
				}
			}
	
			System.out.println("");
		}

		return answer;
	}
	
	public static void start() throws IOException {
		Searcher<?> srch = new Searcher();
		
		File wholeFolder = new File("ConvertedText");
	    File[] List_Of_Files = wholeFolder.listFiles();
	    Scanner s = new Scanner(System.in);

	    for (int i = 0; i < List_Of_Files.length; i++) {
			srch.indexFile(List_Of_Files[i]);
	    }
	    int check =1;
	    while(check==1) {
	    	System.out.println("Enter the word you want to search");
	    	String str = s.nextLine();
	    
	    	String[] al = str.split(" ");
	    	Set<String> answer = new HashSet<String>();
	    	answer = srch.search(Arrays.asList(al));	    	
	    	if(answer.size() > 0)
	    	{
	    		for (String last : answer) {
					System.out.print(last.substring(0,last.length())+"\n" );
	    		}
	    	}
	    	else
	    		System.out.println("Results not Found for : " + str);

	    	try {
				AutoSuggestion.startSuggestion(str);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	
	    	System.out.println("\nType 1 to search for another word or Type 0 to exit");
	    	
	    	while(!s.hasNextInt())
	    	{
	    		System.out.println("Try again!! Type 1 to search for another word or Type 0 to exit");
	    		s.next();
	    	}	    	
	    	check = s.nextInt();
	    	if (check == 0) {
	    		System.out.println("Hope to see you soon. Good Bye!!");
	    		break;
	    	}
	       	else {
	    		str = s.nextLine();	
	    	}
	    }
    	s.close();
	}
	
	public class Tuple {
		private int fileno;
		private int position;
 
		public Tuple(int fileno, int position) {
			this.fileno = fileno;
			this.position = position;
		}
	}
}