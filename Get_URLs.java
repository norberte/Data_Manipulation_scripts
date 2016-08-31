package New;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Get_URLs {

	public static void main(String[] args) throws IOException {
		ArrayList<String> URLs = getAllURLs();
		Iterator<String> myIterator = URLs.iterator();
		while (myIterator.hasNext()) {
			System.out.println(myIterator.next());
		}
	}

	public static ArrayList<String> getAllURLs() throws IOException {
		ArrayList<String> arrayList = new ArrayList<String>();

		String line = "";
		String token = "";
		String baseURL = "http://www.airlinequality.com/Forum/";
		String URL = "";

		File myFile = new File("C:/Users/Norbert/Desktop/HTML.txt");
		Scanner input = new Scanner(myFile);

		Scanner tokenReader = new Scanner(line);

		while (input.hasNextLine()) {
			line = input.nextLine();
			tokenReader = new Scanner(line);
			while (tokenReader.hasNext()) {
				token = tokenReader.next();
				if (token.startsWith("href=")) {
					int index = token.indexOf("=");
					int lastIndex = token.lastIndexOf('"');
					URL = baseURL + token.substring(index + 2, lastIndex);
					arrayList.add(URL);
				}
			}
		}

		tokenReader.close();
		input.close();
		return arrayList;
	}

}
