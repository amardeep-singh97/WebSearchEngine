package searchEngine;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import textprocessing.StdOut;
import textprocessing.TST;
import textprocessing.TrieST;

public class AutoSuggestion {    
    
  public static String readFileAsString(String fileName)throws Exception
  {
    String strData = "";
    String strFileName = "ConvertedText\\" + fileName;
    strData = new String(Files.readAllBytes(Paths.get(strFileName)));
    return strData;
  }
	
  public static void startSuggestion(String word)throws Exception
  {
	  File wholeFolder = new File("ConvertedText");
	  File[] List_Of_Files = wholeFolder.listFiles();
      List<String> listStrings = new ArrayList<>();
      TST<Integer> objTST = new TST<Integer>();
	  for (File file : List_Of_Files) 
	  {
		  if (file.isFile()) 
		  {
			  String strFileText = readFileAsString(file.getName());
			  StringTokenizer strTokenizer = new StringTokenizer(strFileText); 
			  while (strTokenizer.hasMoreTokens()) 
			  {  
				  String strToken = strTokenizer.nextToken();
				  listStrings.add(strToken);
			  }
		  }
	  }
	  
	  for(int i = 0; i < listStrings.size(); i++)
	  {
		  String strToken = listStrings.get(i);
		  objTST.put(strToken, i);
	  }
	  
	  System.out.print("You can also search for : ");
      for (String str : objTST.prefixMatch(word))
    	  System.out.print(str + ", ");  	  	
  	 }
}
