package sentimentAnalysis;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;


public class Vivekn_API {
	private Scanner sc, in;
	private File file;
	private final String path = "C:/Users/Norbert/Desktop/Reports.txt";
	private String line, jsonText;
	private String[] sentences;
	private int dataLength, counter;
	private HttpClient httpClient;
	private HttpPost request;
	private StringEntity params;
	private HttpResponse response = null;
	private HttpEntity entity;
	
	public String[] getSentences(){
		dataLength = 26; counter = 0;  // adjust dataLength to the number of lines in the file
		sentences = new String[dataLength];
		
		file = new File(path);
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFound Exception when reading the input file.");
			System.exit(0);
		}

		while (sc.hasNextLine()) {
			line = sc.nextLine();
			sentences[counter] = line;
			counter++;
		}
		
		return sentences;
	}
	
	public String makePostRequest(String targetURL, String urlParameter){
		httpClient = HttpClientBuilder.create().build(); //Use this instead 
		request= new HttpPost(targetURL);
		try{
			params = new StringEntity("txt="+urlParameter);
			request.addHeader("content-type", "application/x-www-form-urlencoded");
			request.setEntity(params);
			response = httpClient.execute(request);
		
			// handle response here...
			entity = response.getEntity();
            in = new Scanner(entity.getContent());
			jsonText = "";
			while(in.hasNext()){
				jsonText = jsonText + in.next();
			}
			return jsonText;
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "O";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "R";
		} 
	}
}
	

