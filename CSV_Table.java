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

public class CSV_Table {
	public static void main(String[] args) throws IOException {
		
		 ArrayList<String> URLs = New.Get_URLs.getAllURLs();
		
		 Iterator<String> myIterator = URLs.iterator();
		 while(myIterator.hasNext()){
			 getTableData(myIterator.next());
		 } 
		 // do not run the script with the iterator
		 // run it with 10-20 web sites at the time
	}

	public static void createHeader() throws IOException {
		BufferedWriter header = new BufferedWriter(
				new FileWriter("C:/Users/Norbert/Desktop/research/Important Files_1/CSV table data/csvData.txt"));
		header.write("Airline Company,Date of Review,Passanger Name,Originality of Passanger,Rating");
		header.newLine();
		header.close();
	}
	
	public static ArrayList<String> findMorePages(String link) throws IOException {
		ArrayList<String> allPages = new ArrayList<String>();
		
		int pageCounter = 2;
		int index = link.lastIndexOf(".");
		String modifiedLink = link.substring(0, index) + "_" + pageCounter + ".htm";
		
		URL url = new URL(modifiedLink);
		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		huc.setRequestMethod("HEAD");
		
		while(huc.getResponseCode() == HttpURLConnection.HTTP_OK){
			allPages.add(modifiedLink);
			pageCounter++;
			modifiedLink = link.substring(0, index) + "_" + pageCounter + ".htm"; // "_"
			url = new URL(modifiedLink);
			huc = (HttpURLConnection) url.openConnection();
			huc.setRequestMethod("HEAD");
		}
		
		pageCounter = 2;
		index = link.lastIndexOf(".");
		modifiedLink = link.substring(0, index) + "-" + pageCounter + ".htm";
		
		url = new URL(modifiedLink);
		huc = (HttpURLConnection) url.openConnection();
		huc.setRequestMethod("HEAD");
		
		while(huc.getResponseCode() == HttpURLConnection.HTTP_OK){
			allPages.add(modifiedLink);
			pageCounter++;
			modifiedLink = link.substring(0, index) + "-" + pageCounter + ".htm"; // "-"
			url = new URL(modifiedLink);
			huc = (HttpURLConnection) url.openConnection();
			huc.setRequestMethod("HEAD");
		}
		
		allPages.trimToSize();
		return allPages;
	}

	public static void getTableData(String link) throws IOException {
		// deleted pages
		if (link.equals("http://www.airlinequality.com/Forum/air_2000.htm")) {
			return;
		} else if (link.equals("http://www.airlinequality.com/Forum/jmc.htm")) {
			return;
		}
		URL url; Scanner input = new Scanner(""); boolean writeToFile; Scanner token = new Scanner(""); 
		String path = "C:/Users/Norbert/Desktop/research/Important Files_1/CSV table data/csvData.txt";
		//BufferedWriter writeToCSV = new BufferedWriter(new FileWriter(path, true));
		
		ArrayList<String> morePages = findMorePages(link);
		Iterator<String> myIterator = morePages.iterator();
		
		for (int i = 0; i < morePages.size()+1; i++) {
			if(i == 0){
				url = new URL(link);
			} else {
				url = new URL(myIterator.next());
			}
		
			input = new Scanner(url.openStream());

			writeToFile = false;
			String oneToken = "";
			String html_line = "";
			String airline_name, date, person_name, country, rating;
			airline_name = date = person_name = country = rating = "";

			// Case 1 identifiers(e.g. Air Canada)
			String date_passangerName_Country_id_c1 = "<td class=\"airport\" width=\"729\" valign=\"top\" colspan=\"2\" height=\"24\">";
			String airlineName_id_c1 = "<td colspan=\"2\" height=\"49\" valign=\"top\">";
			String rating_id_c1 = "<tr><td width=\"22\"></td><td width=\"209\" valign=\"top\">";
			
			// Case 2 identifiers(e.g. 1Time Airline)
			// Airline name identifier is the same as case 1
			String date_passangerName_Country_id_c2 = "<h3>";
			String rating_id_c2 = "<td width=\"128\" valign=\"bottom\"><p class=\"text1\" style=\"margin-top: 0; margin-bottom: 8px\">";
			String id_c2 = "<p style=\"margin:0 22px; \" class=\"text2\">";
			//String rating_id_c3 = "<td width=\"22\"></td><td width=\"129\"><p class=\"text1\">";

			token = new Scanner(html_line);
			while (input.hasNextLine()) {
				html_line = input.nextLine();
				// case 1 (e.g. Air Canada)
				if (html_line.startsWith(airlineName_id_c1)) {
					while(!html_line.startsWith("<h1>")) {
						html_line = input.nextLine();
					}
					if(html_line.startsWith("<h1>") || html_line.startsWith("<b>")){
						int startIndex = html_line.indexOf("b");
						int endIndex = html_line.indexOf("<", startIndex + 2);
						airline_name = html_line.substring(startIndex + 2, endIndex);
					}
				} else if (html_line.startsWith(date_passangerName_Country_id_c1)) {
					token = new Scanner(html_line);
					oneToken = "";
					while (token.hasNext()) {
						oneToken = token.next();
						if(html_line.contains("<h9>")){ // duplicated code to avoid case combination errors
							if(oneToken.endsWith("&nbsp;")){
								for (int j = 0; j < 3; j++) {
									oneToken = token.next();
									if (j == 2) date = date + oneToken;
									else date = date + oneToken + " ";
								}
							} else if(oneToken.equals("by")){
								oneToken = token.next();
								
								while (!oneToken.startsWith("(")) {
									person_name = person_name + oneToken + " ";
									oneToken = token.next();
								}
								char endCharacter = person_name.charAt(person_name.length() - 1);
								if(Character.isWhitespace(endCharacter)){
									person_name = person_name.substring(0, person_name.length() - 1);
								}
								
								if (oneToken.startsWith("(") && oneToken.endsWith("<h3>")) {
									int startIndex = oneToken.indexOf("(");
									int endIndex = oneToken.indexOf(")");
									country = oneToken.substring(startIndex + 1, endIndex);
								} else if(oneToken.startsWith("(") && !oneToken.endsWith(")")) {
									int end = oneToken.indexOf(")");
									if(!token.hasNext()){
										country = country + oneToken.substring(1, end);
									} else {
										country = country + oneToken.substring(1) + " ";
										oneToken = token.next();
										int endIndex = oneToken.indexOf(")");
										if(oneToken.contains(")")){
											country = country + oneToken.substring(0, endIndex);
										}else{
											country = country + oneToken + " ";
										}
									}
								} 
							}
						}
						else if (oneToken.equals(":&nbsp;")) {
							for (int j = 0; j < 3; j++) {
								oneToken = token.next();
								if (j == 2) date = date + oneToken;
								else date = date + oneToken + " ";
							}
						} else if (oneToken.equals("by")) {
							oneToken = token.next();
							while (!oneToken.endsWith("&nbsp;&nbsp;")) {
								person_name = person_name + oneToken + " ";
								oneToken = token.next();
								if(oneToken.endsWith("&nbsp;&nbsp;")){
									int end = oneToken.indexOf("&");
									person_name = person_name + oneToken.substring(0,end);
								}
							}
						} else if (oneToken.startsWith("(") && oneToken.endsWith(")")) {
							int startIndex = oneToken.indexOf("(");
							int endIndex = oneToken.indexOf(")");
							country = oneToken.substring(startIndex + 1, endIndex);
						} else if(oneToken.startsWith("(") && !oneToken.endsWith(")")) {
							int end = oneToken.indexOf(")");
							if(!token.hasNext()){
								country = country + oneToken.substring(1, end) + " ";
							} else {
								country = country + oneToken.substring(1) + " ";
								oneToken = token.next();
								int endIndex = oneToken.indexOf(")");
								country = country + oneToken.substring(0, endIndex);
							}
						} 
					}
				} else if (html_line.startsWith(rating_id_c1)) {
					token = new Scanner(html_line);
					oneToken = "";
					while (token.hasNext()) {
						oneToken = token.next();
						if (oneToken.equals(":")) {
							oneToken = token.next();
							int index = oneToken.indexOf("/");
							rating = oneToken.substring(0, index);
							writeToFile = true;
						}
					}
				} // case 2 (E.g. 1Time Airline)
				else if (html_line.startsWith(date_passangerName_Country_id_c2) || html_line.startsWith(id_c2)){ // ?? check if next token is the name of the Airline
					if(!html_line.contains("(")){
						continue;
					}
					token = new Scanner(html_line);
					oneToken = "";
					while(token.hasNext()){
						oneToken = token.next();
						if(oneToken.endsWith("&nbsp;")){
							for (int j = 0; j < 3; j++) {
								oneToken = token.next();
								if (j == 2) date = date + oneToken;
								else date = date + oneToken + " ";
							}
						} else if(oneToken.equals("by")){
							oneToken = token.next();
							while (!oneToken.startsWith("(")) {
								person_name = person_name + oneToken + " ";
								oneToken = token.next();
							}
							char endCharacter = person_name.charAt(person_name.length() - 1);
							if(Character.isWhitespace(endCharacter)){
								person_name = person_name.substring(0, person_name.length() - 1);
							}
							if (oneToken.startsWith("(") && oneToken.endsWith("<h3>")) {
								int startIndex = oneToken.indexOf("(");
								int endIndex = oneToken.indexOf(")");
								country = oneToken.substring(startIndex + 1, endIndex);
							} else if(oneToken.startsWith("(") && !oneToken.endsWith(")")) {
								int end = oneToken.indexOf(")");
								if(!token.hasNext()){
									country = country + oneToken.substring(1, end);
								} else {
									country = country + oneToken.substring(1) + " ";
									oneToken = token.next();
									int endIndex = oneToken.indexOf(")");
									if(oneToken.contains(")")){
										country = country + oneToken.substring(0, endIndex);
									}else{
										country = country + oneToken + " ";
									}
								}
							} 
						}
					}
				} else if(html_line.startsWith(rating_id_c2)){
					token = new Scanner(html_line);
					oneToken = "";
					while(token.hasNext()){
						oneToken = token.next();
						if(oneToken.equals(":&nbsp;")){
							oneToken = token.next();
							int index = oneToken.indexOf("/");
							rating = oneToken.substring(0,index);
							writeToFile = true;
						}
					}
				}
				/*
				// case 3:
				else if(html_line.startsWith("<div align=\"center\"><table border=\"0\" width=\"736\" cellspacing=\"0\" cellpadding=\"0\">")){
					html_line = input.nextLine();
					if(html_line.startsWith("<h3>")){
						token = new Scanner(html_line);
						oneToken = "";
						country = "unknown";
						while(token.hasNext()){
							oneToken = token.next();
							if(oneToken.equals(":&nbsp;")){
								for (int j = 0; j < 3; j++) {
									oneToken = token.next();
									if (j == 2) date = date + oneToken;
									else date = date + oneToken + " ";
								}
							} else if (oneToken.equals("by")) {
								while (token.hasNext()) {
									oneToken = token.next();
									if (oneToken.endsWith("</h3>")) {
										int index = oneToken.indexOf("<");
										person_name = person_name + oneToken.substring(0,index);
									} else {
										person_name = person_name + oneToken + " ";
									}
								}
							}
							char endCharacter = airline_name.charAt(airline_name.length() - 1);
							if (Character.isWhitespace(endCharacter)) {
								airline_name = airline_name.substring(0,airline_name.length() - 1);
							}
						}
					}
					
				} else if(html_line.startsWith(rating_id_c3)){
					token = new Scanner(html_line);
					oneToken = "";
					while(token.hasNext()){
						oneToken = token.next();
						if(oneToken.equals(":")){
							oneToken = token.next();
							int index = oneToken.indexOf("/");
							rating = oneToken.substring(0,index);
							writeToFile = true;
						}
					}
				}
				*/
				// write to file
				if (writeToFile) {
					airline_name.trim(); date.trim(); person_name.trim();country.trim(); rating.trim();
					if(person_name.contains(" &nbsp;&nbsp") || person_name.contains("&nbsp;")){
						int index = person_name.indexOf("&");
						person_name = person_name.substring(0, index);
					}

					if (Character.isWhitespace(airline_name.charAt(airline_name.length() - 1))) {
						airline_name = airline_name.substring(0,airline_name.length() - 1);
					}
					if(person_name.length() != 0){
						if (Character.isWhitespace(person_name.charAt(person_name.length() - 1))) {
							person_name = person_name.substring(0,person_name.length() - 1);
						}
					}
					
					if (airline_name.length() == 0 || date.length() == 0
							|| person_name.length() == 0 || country.length() == 0
							|| rating.length() == 0) {
						System.out.println("Error, data does not exist.");
					}

					//writeToCSV.write(airline_name + "," + date + "," + person_name + "," + country + "," + rating);
					//writeToCSV.newLine();
					System.out.println(airline_name + "," + date + "," + person_name + "," + country + "," + rating);

					writeToFile = false;
					date = person_name = country = rating = "";
				}
			}
		}

		input.close();
		token.close();
		//writeToCSV.close();

	}
}
