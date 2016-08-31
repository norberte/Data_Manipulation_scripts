package New;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.htmlcleaner.*;

public class Html_Cleaner_Practice {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		tryMe();
		
		/*
		String link = "http://www.airlinequality.com/airline-reviews/air-canada/";
		 
		// set some properties to non-default values
		props.setTranslateSpecialEntities(true);
		props.setTransResCharsToNCR(true);
		props.setOmitComments(true);
		 
		// do parsing
		TagNode tagNode = new HtmlCleaner(props).clean( new URL(link) );
		 
		// serialize to xml file
		new PrettyXmlSerializer(props).writeXmlToFile(node, "C:/Users/Norbert/Desktop/air_c.xml", "utf-8");
		*/
		
		
		// ArrayList<Object> NODES = new ArrayList<Object>(10);
				// for (int i = 1; i <= 10; i++) {
				// try {
				// NODES.add( node.evaluateXPath(xPath_base + i + "]") );
				// } catch (XPatherException e) {
				// e.printStackTrace();
				// }
				// }	
		
		
		//*[@id="main"]/section[3]/div[1]/article/article[1]
	}
	
	public static void tryMe() throws IOException{
		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();

		TagNode node = cleaner.clean(new URL("http://www.airlinequality.com/airline-reviews/air-canada/"));
		
		TagNode[] myNodes = node.getElementsByName("article", true);
		
		String xPath_sample = "//*[@id=\"main\"]/section[3]/div[1]/article/article[1]";
		Object[] Nodes = null;

		try {
			//Nodes = node.evaluateXPath("//*[@id=\"main\"]/section[3]/div[1]/article/article[1]/div[2]/div[1]/text()");
			Nodes = node.evaluateXPath(xPath_sample);
		} catch (XPatherException e) {
			e.printStackTrace();
		}

	
		System.out.println("XPATH: ");
		for (int i = 0; i < Nodes.length; i++) {
			System.out.println(Nodes[i].toString());
		}
	}

}
