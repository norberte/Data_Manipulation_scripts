package sentimentAnalysis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Vivekn_Main {
	static Vivekn_API API = new Vivekn_API();
	static final String targetURL = "http://sentiment.vivekn.com/api/text/";
	static String urlParameter, result;
	static String[] data, sentiment, confidence;
	
	public static void main(String[] args) throws IOException {
		data = API.getSentences();
		confidence = new String[data.length];
		sentiment = new String[data.length];
		
		for (int i = 0; i < data.length; i++) {
			try {
				urlParameter = "txt=" + URLEncoder.encode(data[i], "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("Encoding not supported Exception !");
				e.printStackTrace();
			}
			result = API.makePostRequest(targetURL, urlParameter );
			
			confidence[i] = result.substring(25, 32);
			sentiment[i] = result.substring(47, result.length() - 3);
		}
		writeToCSV(data, confidence, sentiment, data.length);
	}
	
	public static void writeToCSV(String[] data, String[] confidence, String[] sentiment,int length) throws IOException{
		BufferedWriter out = new BufferedWriter( new FileWriter("C:/Users/Norbert/Desktop/teaching_evaluations.csv") );
		out.write("reviewText,sentiment,confidenceScore");
		out.newLine();
		for (int i = 0; i < length; i++) {
			out.write(appendDQ(data[i]) + "," + appendDQ(sentiment[i]) + "," + appendDQ(confidence[i]));
			out.newLine();
		}
		out.close();
	}
	
	private static String appendDQ(String str) {
	    return "\"" + str + "\"";
	}

}
