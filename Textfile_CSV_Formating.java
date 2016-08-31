package New;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Textfile_CSV_Formating {

	public static void main(String[] args) throws IOException {
		createCSVfile( makeSentences() );
	}
	
	public static void createCSVfile(ArrayList<String> list) throws IOException{
		String path = "C:/Users/Norbert/Desktop/sentiment152.csv";
		BufferedWriter writeToCSV = new BufferedWriter(new FileWriter(path,true));
		String content, value;
		content = value = "";
		
		Iterator<String> myIterator = list.iterator();
		
		while(myIterator.hasNext()){
			 value = myIterator.next();
			 if(!value.equals("##")){
				 content = content + value + ",";
			 }else{
				 writeToCSV.write(content);
				 System.out.println(content);
				 writeToCSV.newLine();
				 content = value = ""; 
			 }
		} 
		writeToCSV.close();
	}
	
	public static ArrayList<String> makeSentences() throws IOException{
		File myFile = new File("C:/Users/Norbert/Desktop/myFile2.txt");
		Scanner text_in = new Scanner(myFile);
		String sentences = "";
		String line = "";
		ArrayList<String> list = new ArrayList<String>();

		while(text_in.hasNext()){
			try{
				while(!(line = text_in.nextLine()).trim().isEmpty()) {
					sentences = sentences + line + " ";
				}
			}
			catch(NoSuchElementException ex){
				
			}
			//String[] sentences = sentence.split("(?i)(?<=[.?!])\\S+(?=[a-z])");
		
			//Pattern re = Pattern.compile("(?:[\u00A0\u2007\u202F\p{javaWhitespace}&&[^\n\r]])*(\n\r|\r\n|\n|\r)(?:(?:[\u00A0\u2007\u202F\p{javaWhitespace}&&[^\n\r]])*\1)+", Pattern.MULTILINE | Pattern.COMMENTS);
			Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
			Matcher reMatcher = re.matcher(sentences);
			sentences = "";
			while (reMatcher.find()) {
				list.add(appendDQ(reMatcher.group()));
				System.out.println(appendDQ(reMatcher.group()));
			}
			//for (int i = 0; i < sentences.length; i++) {
			//	list.add(appendDQ(sentences[i]));
			//	System.out.println(appendDQ(sentences[i]));
			//}
			list.add("##");
		}

		text_in.close();
		return list;
	}
	private static String appendDQ(String str) {
	    return "\"" + str + "\"";
	}

}
