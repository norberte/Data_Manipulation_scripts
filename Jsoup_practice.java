package New;
import java.io.IOException;
import java.net.URL;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;

public class Jsoup_practice {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {

		CleanerProperties props = new CleanerProperties();
		String link = "http://www.airlinequality.com/airline-reviews/air-canada/";
		 
		// set some properties to non-default values
		props.setTranslateSpecialEntities(true);
		props.setTransResCharsToNCR(true);
		props.setOmitComments(true);
		 
		// do parsing
		TagNode tagNode = new HtmlCleaner(props).clean( new URL(link) );
		 
		// serialize to xml file
		new PrettyXmlSerializer(props).writeXmlToFile(tagNode, "C:/Users/Norbert/Desktop/air_c.xml", "utf-8");
	}

}
