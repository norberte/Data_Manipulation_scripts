package New;
//  CYK algorithm in R
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class URL_Stream {
	public static void main(String[] args) throws IOException {
		//ArrayList<String> URLs = New.Get_URLs.getAllURLs();
		
		//Iterator<String> myIterator = URLs.iterator();
		//while(myIterator.hasNext()){
			//createFile(myIterator.next());
			//createFile("http://www.airlinequality.com/Forum/1time.htm");
		//}
		getHTMLFiles();
		System.out.println("Done");
	}
	
	public static void getHTMLFiles() throws IOException{
		String path = "C:/Users/Norbert/Desktop/";
		String link = "http://www.airlinequality.com/airline-reviews/air-canada/";
		String content = "";
		URL url = new URL(link);
		Scanner input = new Scanner(url.openStream());
		
		BufferedWriter out = new BufferedWriter(new FileWriter(path + "AIR_Canada.txt"));
		while(input.hasNextLine()){
			content = content + input.nextLine() + System.lineSeparator();
		}
		out.write(content);
		out.close();
		input.close();
	}
	
	public static void createFile(String link) throws IOException{
		String path = "C:/Users/Norbert/Desktop/research/Important files/Airline Reviews/";
		URL url = new URL(link);
		
		if(link.equals("http://www.airlinequality.com/Forum/air_2000.htm")){
			return;
		} else if (link.equals("http://www.airlinequality.com/Forum/jmc.htm")){
			return;
		}
		
		Scanner input = new Scanner(url.openStream());

		String content = "";
		String rating = "";
		String html_Code_line = "";
		
		String lineCheck = "<tr><td width=\"22\">";
		String beforeReview1 = "<p style=\"margin:0 22px; \" class=\"text2\">"; // line
		String beforeReview = "<p class=\"text2\" style=\"margin-top: 6px; margin-bottom: 0\">"; // line
		String afterReview = "</td></tr>"; // line
		String afterReview1 = "<br><br>"; // line

		Scanner token = new Scanner(html_Code_line);
		System.out.println(link);
		while (input.hasNextLine()) {
			if (html_Code_line.startsWith(lineCheck)) {
				token = new Scanner(html_Code_line);
				String oneToken = "";
				while (token.hasNext()) {
					oneToken = token.next();
					if (oneToken.equals(":")) {
						oneToken = token.next();
						int index = oneToken.indexOf("/");
						rating = rating + oneToken.substring(0, index) + ",";
					}
				}
				html_Code_line = input.nextLine();
			} else if (html_Code_line.equals(beforeReview)) {
				html_Code_line = input.nextLine();
				while (!html_Code_line.equals(afterReview) && input.hasNextLine()) {
					content = content + html_Code_line + System.lineSeparator();
					html_Code_line = input.nextLine();
				}
				content = content + System.lineSeparator();
			} else if (html_Code_line.equals(beforeReview1)) {
				html_Code_line = input.nextLine();
				while (!html_Code_line.equals(afterReview1) && input.hasNextLine()) {
					content = content + html_Code_line + System.lineSeparator();
					html_Code_line = input.nextLine();
				}
				content = content + System.lineSeparator();
			} else {
				html_Code_line = input.nextLine();
			}
		}
		//content = content.substring(0, content.length() - 1);
		input.close();
		token.close();
		
		int index1 = link.lastIndexOf("/");
		int index2 = link.lastIndexOf(".");
		
		String fileNameForReview = path + "Reviews/" + link.substring(index1 + 1, index2)+ "_Review.txt";
		String fileNameForRating = path + "Ratings/" + link.substring(index1 + 1, index2)+ "_Rating.txt";
		
		BufferedWriter outReview = new BufferedWriter(new FileWriter(fileNameForReview));
		BufferedWriter outRating = new BufferedWriter(new FileWriter(fileNameForRating));
		outReview.write(content);
		outReview.close();
		
		outRating.write(rating);
		outRating.close();
		
	}
	
}
