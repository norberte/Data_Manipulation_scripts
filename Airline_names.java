package New;

// 250
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Airline_names {
	public static void main(String[] args) throws IOException {

		ArrayList<String> URLs = New.Get_URLs.getAllURLs();

		Iterator<String> myIterator = URLs.iterator();
		while (myIterator.hasNext()) {
			String link = myIterator.next();
			try {
				getTableData(link);
			} catch (Exception ex) {
				System.out.println("Missing: " + link);
			}
		}
	}

	public static void getTableData(String link) throws IOException {
		// deleted pages
		if (link.equals("http://www.airlinequality.com/Forum/air_2000.htm")) {
			return;
		} else if (link.equals("http://www.airlinequality.com/Forum/jmc.htm")) {
			return;
		}
		URL url;
		Scanner input = new Scanner("");
		boolean writeToFile;
		Scanner token = new Scanner("");
		String path = "C:/Users/Norbert/Desktop/airlineNames.txt";
		BufferedWriter writeToCSV = new BufferedWriter(new FileWriter(path,
				true));

		url = new URL(link);
		input = new Scanner(url.openStream());

		writeToFile = false;
		String oneToken = "";
		String html_line = "";
		String airline_name = "";

		String airlineName_id_c1 = "<td colspan=\"2\" height=\"49\" valign=\"top\">";

		token = new Scanner(html_line);
		while (input.hasNextLine()) {
			html_line = input.nextLine();
			if (html_line.startsWith(airlineName_id_c1)) {
				while (!html_line.startsWith("<h1>")) {
					html_line = input.nextLine();
				}
				if (html_line.startsWith("<h1>") || html_line.startsWith("<b>")) {
					int startIndex = html_line.indexOf("b");
					int endIndex = html_line.indexOf("<", startIndex + 2);
					airline_name = html_line
							.substring(startIndex + 2, endIndex);
					writeToFile = true;
				}

				// write to file
				if (writeToFile) {
					airline_name.trim();

					if (Character.isWhitespace(airline_name.charAt(airline_name
							.length() - 1))) {
						airline_name = airline_name.substring(0,
								airline_name.length() - 1);
					}

					if (airline_name.length() == 0) {
						System.out.println("Error, data does not exist.");
					}

					writeToCSV.write(airline_name + ",");
					System.out.println("Name: " + airline_name);

					writeToFile = false;
				}
			}

		}
		input.close();
		token.close();
		writeToCSV.close();
	}	
}
