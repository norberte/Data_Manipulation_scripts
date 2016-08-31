package New;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class getFunctionCalls {

	public static void main(String[] args) throws IOException {
		createFunctionCalls();
	}

	public static void createFunctionCalls() throws IOException{
		String content = "";
		ArrayList<String> URLs = New.Get_URLs.getAllURLs();
		Iterator<String> myIterator = URLs.iterator();
		while (myIterator.hasNext()) {
			String link = myIterator.next();
			if (link.equals("http://www.airlinequality.com/Forum/air_2000.htm")) {
				continue;
			} else if (link.equals("http://www.airlinequality.com/Forum/jmc.htm")) {
				continue;
			}
			String functionCall = "getTableData(\"" + link + "\");";
			
			content = content + functionCall + System.lineSeparator();
		}
		
		BufferedWriter header = new BufferedWriter(new FileWriter("C:/Users/Norbert/Desktop/functionCalls.txt"));
		header.write(content);
		header.close();
	}
}
