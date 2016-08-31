package New;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import dataCollector.WebScraper;


public class XPath_XML {

	public static void main(String[] args) throws IOException, ParserConfigurationException, Exception {
		long start = System.currentTimeMillis(); //*[@id="main"]/section[3]/div[1]/article/article[1]/div[2]/div[2]/table/tbody/tr[9]/td[2]/span[2]
		String data = getDataByAttributeValue("//*[@id=\"main\"]/section[3]/div[1]/article/article[1]/div[2]/div[2]/table/tbody/tr[10]/td[2]/span[1]", "C:/Users/Norbert/Desktop/XML Files/air-canada_1.xml");
		//String data = getData("//*[@id=\"main\"]/section[3]/div[1]/article/article[1]/div[2]/div[2]/table/tbody/tr[4]/td[2]/span[1]", "C:/Users/Norbert/Desktop/XML Files/air-canada_1.xml", false, "");
		long end = System.currentTimeMillis();
		System.out.println("Data = " + data + " & Time = " + ((end-start)/ 1000.0) + " seconds");
		//mainMethod();
		
		//*[@id="main"]/section[3]/div[1]/article/article[1]/div[2]/div[2]/table/tbody/tr[4]/td[2]/span[1] // seat confort
		//*[@id="main"]/section[3]/div[1]/article/article[1]/div[2]/div[2]/table/tbody/tr[5]/td[2]/span[1] // cabin flown
		//*[@id="main"]/section[3]/div[1]/article/article[1]/div[2]/div[2]/table/tbody/tr[6]/td[2]/span[1] // food and beverages
		//*[@id="main"]/section[3]/div[1]/article/article[1]/div[2]/div[2]/table/tbody/tr[7]/td[2]/span[1] // inflight
		//*[@id="main"]/section[3]/div[1]/article/article[1]/div[2]/div[2]/table/tbody/tr[8]/td[2]/span[1] // ground service
		//*[@id="main"]/section[3]/div[1]/article/article[1]/div[2]/div[2]/table/tbody/tr[9]/td[2]/span[1] // value for money
		
		
		//*[@id="main"]/section[3]/div[1]/article/article[2]/div[2]/div[2]/table/tbody/tr[4]/td[2]/span[1] // seat
		
		//*[@id="main"]/section[3]/div[1]/article/article[5]/div[2]/div[2]/table/tbody/tr[5]/td[2]/span[1] // seat 
		//*[@id="main"]/section[3]/div[1]/article/article[5]/div[2]/div[2]/table/tbody/tr[10]/td[2]/span[1]
		//*[@id="main"]/section[3]/div[1]/article/article[5]/div[2]/div[2]/table/tbody/tr[11]/td[2]/span[1]
	}
	
	public static String getIncrementedXPath(int counter, String xPath){
		int startIndex = xPath.lastIndexOf("[");
		return xPath.substring(0, startIndex+1) + counter + "]";
	}
	
	public static String[] getXPathArray(int counter){
		String[] data = new String[17];
		
		data[0] = "//*[@id=\"main\"]/section[1]/div/section/div[2]/div[1]/div[1]/h1"; // airline
		if(counter == 1){ 
			data[1] = "//*[@id=\"main\"]/section[3]/div[1]/article/article[1]"; // review
		} else {
			data[1] = "//*[@id=\"main\"]/section[3]/div[1]/article/article[1]"; // default review xPath
			data[1] = getIncrementedXPath(counter, data[1]); 
		}
		data[2] = data[1] + "/div[1]/span[1]"; //rating
		data[3] = data[1] + "/div[2]/h3/span"; // author
		data[4] = data[1] + "/div[2]/h3/text()"; // extraction
		data[5] = data[1] + "/div[2]/div[1]/text()"; // text
		data[6] = data[1] + "/div[2]/div[2]/table/tbody/tr[1]/td[2]"; // aircraft
		data[7] = data[1] + "/div[2]/div[2]/table/tbody/tr[2]/td[2]"; // travellerType
		data[8] = data[1] + "/div[2]/div[2]/table/tbody/tr[3]/td[2]"; // cabinFlown
		data[9] = data[1] + "/div[2]/div[2]/table/tbody/tr[4]/td[2]"; // route
		data[10] = data[1] + "/div[2]/div[2]/table/tbody/tr[5]/td[2]/span[1]"; // stars --> Seat Comfort
		data[11] = data[1] + "/div[2]/div[2]/table/tbody/tr[6]/td[2]/span[1]"; // stars --> Cabin Staff Service
		data[12] = data[1] + "/div[2]/div[2]/table/tbody/tr[7]/td[2]/span[1]"; // stars --> Food & Beverages
		data[13] = data[1] + "/div[2]/div[2]/table/tbody/tr[8]/td[2]/span[1]"; // stars --> Inflight Entertainment
		data[14] = data[1] + "/div[2]/div[2]/table/tbody/tr[9]/td[2]/span[1]"; // stars --> Ground Service
		data[15] = data[1] + "/div[2]/div[2]/table/tbody/tr[10]/td[2]/span[1]"; // stars --> Value For Money
		data[16] = data[1] + "/div[2]/div[2]/table/tbody/tr[11]/td[2]"; // recommendation
		return data;
	}
	
	public static int getNumberOfPagesPerLink(String link) throws IOException{
		String xPath = "//*[@id=\"main\"]/section[1]/div/section/div[2]/div[2]/div[2]/div[1]/span";
		File xmlFile; org.jsoup.nodes.Document jsoupDoc = null;
		int index, endIndex;  int numberOfReviews = 0; String xml_path, fileName;
		HtmlCleaner cleaner; CleanerProperties props; PrettyXmlSerializer xml; TagNode tagNode;
		TransformerFactory tFactory; Transformer transformer; DOMSource source; StreamResult result; 
		W3CDom w3cDom; org.w3c.dom.Document w3cDoc; NodeList childNodes;
		org.w3c.dom.Document dc; NodeList nodes; javax.xml.xpath.XPath newXPath; 
		
		index = link.indexOf("/", 40);
		endIndex = link.indexOf("/", index + 1);
		fileName = link.substring(index + 1, endIndex) + "_" + 1;
		
		try {
			jsoupDoc = Jsoup.connect(link).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		w3cDom = new W3CDom();
		w3cDoc = w3cDom.fromJsoup(jsoupDoc);
			
		xmlFile = new File("C:/Users/Norbert/Desktop/XML Files/" + fileName + ".xml");
		try {
			tFactory = TransformerFactory.newInstance();
			transformer = tFactory.newTransformer();

			source = new DOMSource(w3cDoc);
			result = new StreamResult(xmlFile);
			transformer.transform(source, result);
		} catch (TransformerConfigurationException tce) {
			System.out.println("* Transformer Factory error @Number of Reviews");
		} catch (TransformerException te) {
			System.out.println("* Transformation error @Number of Reviews");
		}

		cleaner = new HtmlCleaner();
		props = cleaner.getProperties();
		props.setTranslateSpecialEntities(true); // set some properties to non-default values
		props.setTransResCharsToNCR(true);
		props.setOmitComments(true);

		xml = new PrettyXmlSerializer(props);
		tagNode = new HtmlCleaner(props).clean(xmlFile); // do parsing
		
		xml_path = "C:/Users/Norbert/Desktop/XML Files/" + fileName + ".xml"; // serialize to xml file
		xml.writeToFile(tagNode, xml_path, "utf-8");

		try {
			dc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xml_path);
			newXPath = XPathFactory.newInstance().newXPath();
			nodes = (NodeList) newXPath.evaluate(xPath, dc, XPathConstants.NODESET);
			childNodes = nodes.item(0).getChildNodes();

			try{
				numberOfReviews = Integer.parseInt(childNodes.item(0).getTextContent());
				System.out.println("Number of reviews = " + numberOfReviews);
			}catch(NumberFormatException ex){
				System.out.println("ERROR ! String Not A Number");
			}
		} catch (Exception ex) {
			Logger.getLogger(XPath.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		double pages = numberOfReviews / 100.0;
		if(pages > 0.0){
			System.out.println(pages + " Pages = " + (int) Math.ceil(pages));
			return (int) Math.ceil(pages);
		} else {
			System.out.println("ERROR! Something went really wrong with the number of reviews !");
			System.exit(0);
			return 0;
		}
	}
	
	public static void mainMethod() throws ParserConfigurationException, Exception{
		File xmlFile; org.jsoup.nodes.Document jsoupDoc = null; Throwable x;
		int index, endIndex, numberOfPages; String xml_path, fileName; String[] xPathCalls; String[] data;
		HtmlCleaner cleaner; CleanerProperties props; PrettyXmlSerializer xml; TagNode tagNode;
		TransformerFactory tFactory; Transformer transformer; DOMSource source; StreamResult result;
		W3CDom w3cDom; org.w3c.dom.Document w3cDoc; String[] airlineReview_links = new String[5];
		
		airlineReview_links[0] = "http://www.airlinequality.com/airline-reviews/air-canada/?sortby=post_date%3ADesc&pagesize=100";
		airlineReview_links[1] = "http://www.airlinequality.com/airline-reviews/us-airways/?sortby=post_date%3ADesc&pagesize=100";
		airlineReview_links[2] = "http://www.airlinequality.com/airline-reviews/british-airways/?sortby=post_date%3ADesc&pagesize=100";
		airlineReview_links[3] = "http://www.airlinequality.com/airline-reviews/air-india/?sortby=post_date%3ADesc&pagesize=100";
		airlineReview_links[4] = "http://www.airlinequality.com/airline-reviews/egyptair/?sortby=post_date%3ADesc&pagesize=100";
		
		for (int i = 0; i < airlineReview_links.length; i++){
			numberOfPages = getNumberOfPagesPerLink(airlineReview_links[i]);
			for (int j = 1; j <= numberOfPages; j++) {
				index = airlineReview_links[i].indexOf("/", 40);
				endIndex = airlineReview_links[i].indexOf("/", index + 1);
				fileName = airlineReview_links[i].substring(index + 1, endIndex) + "_" + j;
				if(j != 1){
					try {
				        jsoupDoc = Jsoup.connect(airlineReview_links[i]).get();
				    } catch (IOException e) {
				        e.printStackTrace();
				    }
				    w3cDom = new W3CDom();
				    w3cDoc = w3cDom.fromJsoup(jsoupDoc);
					
					xmlFile = new File("C:/Users/Norbert/Desktop/XML Files/" + fileName + ".xml");
					try {
						tFactory = TransformerFactory.newInstance();
						transformer = tFactory.newTransformer();

						source = new DOMSource(w3cDoc);
						result = new StreamResult(xmlFile);
						transformer.transform(source, result);
					} catch (TransformerConfigurationException tce) {
						System.out.println("* Transformer Factory error");
						System.out.println(" " + tce.getMessage());
						x = tce;
						if (tce.getException() != null)
							x = tce.getException();
						x.printStackTrace();
					} catch (TransformerException te) {
						System.out.println("* Transformation error");
						System.out.println(" " + te.getMessage());
						x = te;
						if (te.getException() != null)
							x = te.getException();
						x.printStackTrace();
					}
					
					cleaner = new HtmlCleaner();
					props = cleaner.getProperties();
					props.setTranslateSpecialEntities(true); // set some properties to non-default values
					props.setTransResCharsToNCR(true);
					props.setOmitComments(true);
					
					xml = new PrettyXmlSerializer(props);
					tagNode = new HtmlCleaner(props).clean(xmlFile); // do parsing
					
					// serialize to xml file
					xml_path = "C:/Users/Norbert/Desktop/XML Files/" + fileName + ".xml";
					xml.writeToFile(tagNode, xml_path, "utf-8");
				} else {
					xml_path = "C:/Users/Norbert/Desktop/XML Files/" + fileName + ".xml";
				}
				
				for (int reviewCounter = 1; reviewCounter <= 100; reviewCounter++) {
					xPathCalls = getXPathArray(reviewCounter);
					data = new String[xPathCalls.length];
					for (int k = 0; k < xPathCalls.length; k++) {
						switch(k){
							case 10: case 11: case 12: case 13: case 14: case 15: data[k] = getData(xPathCalls[k], xml_path, false, "");
							case 0: case 2: case 3: case 5: case 6: case 7: 
							case 8: case 9: case 16: data[k] = getData(xPathCalls[k], xml_path, true, "");
							case 4: data[k] = getData(xPathCalls[k], xml_path, true, "extraction"); /// special case
						}
						// careful with special case... data[4] contains country and data separated by "#"
						// take data and write it to .csv file
						// also create .csv header
					}
					
				}
				
			}
		}
	}

	public static String getData(String xPath, String filePath, boolean attribute, String specialCase) throws Exception, IOException,ParserConfigurationException {
		try {
			Document dc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filePath);
			javax.xml.xpath.XPath newXPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) newXPath.evaluate(xPath, dc, XPathConstants.NODESET);
			int number = 0;
			if (attribute) {
				NodeList childNodes = nodes.item(0).getChildNodes();
				if(specialCase.equals("extraction")){
					// (Canada) 21st June 2015
					String extraction = childNodes.item(0).getTextContent();
					 int startIndex = extraction.indexOf("(") ;
					 int endIndex = extraction.indexOf(")");
					 return extraction.substring(startIndex + 1, endIndex) + "#" + extraction.substring(endIndex + 2);
				} else{
					return childNodes.item(0).getTextContent();
				}
				
			} else {
				NamedNodeMap attributes = nodes.item(0).getAttributes();
				String data = attributes.item(0).getTextContent();
				while (data.equals("star fill")) {
					int index = xPath.lastIndexOf("[");
					try {
						number = Integer.parseInt(xPath.substring(index + 1,xPath.length() - 1));
					} catch (NumberFormatException ex) {
						System.out.println("ERROR !!! Can not convert span number to an integer!");
					}
					xPath = xPath.substring(0, xPath.length() - 2) + ++number + "]";
					if(number == 6){
						break;
					}
					nodes = (NodeList) newXPath.evaluate(xPath, dc,XPathConstants.NODESET);
					attributes = nodes.item(0).getAttributes();
					data = attributes.item(0).getTextContent();
				}
				return (number - 1) + "";
			}

		} catch (Exception ex) {
			Logger.getLogger(XPath.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public static String getDataByAttributeValue(String xPath, String filePath){
		try {
			Document dc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filePath);
			javax.xml.xpath.XPath newXPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) newXPath.evaluate(xPath, dc, XPathConstants.NODESET);
			NamedNodeMap attributes = nodes.item(0).getAttributes();
			String data = attributes.item(0).getTextContent();
			
			System.out.println(data);
			return data;
		} catch (Exception ex) {
			Logger.getLogger(WebScraper.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "#ERROR";
	}
}
