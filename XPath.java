package New;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
//import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class XPath {
	public static void main(String[] args) throws Exception, IOException, ParserConfigurationException {
		myMethod();
	}

	public static void myMethod() throws Exception, IOException, ParserConfigurationException{
		String xPath_sample = "//*[@id=\"main\"]/section[3]/div[1]/article/article[1]/div[2]/div[2]/table/tbody";
		String fileName = "C:/Users/Norbert/Desktop/XML Files/air-canada_1.xml";
		try{
			Document dc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fileName);
			javax.xml.xpath.XPath newXPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) newXPath.evaluate(xPath_sample, dc, XPathConstants.NODESET );
			
		//	System.out.println(nodes.item(0).getTextContent()); all the text from an object
			
			for (int i = 0; i < nodes.getLength(); i++) {
				//NamedNodeMap attributes = nodes.item(i).getAttributes();
				NodeList childNodes = nodes.item(i).getChildNodes();
				
				/*
				for (int j = 0; j < childNodes.getLength(); j++) {
					System.out.println("j :" + j);
					System.out.println(childNodes.item(j).getTextContent());
					System.out.println(childNodes.item(j).getNodeName());
					System.out.println(childNodes.item(j).getNamespaceURI());
					//System.out.println(childNodes.item(j).getNextSibling().toString());
					System.out.println(childNodes.item(j).getNodeType());
					//System.out.println(childNodes.item(j).getFirstChild().toString());
					//System.out.println(childNodes.item(j).getParentNode().toString());
					System.out.println(childNodes.item(j).getBaseURI());
					System.out.println(childNodes.item(j).getLocalName());
					System.out.println(childNodes.item(j).getNodeValue());
					System.out.println(childNodes.item(j).getPrefix());
					//System.out.println(childNodes.item(j).getAttributes().toString());
					System.out.println(childNodes.item(j).getChildNodes().toString());
					//System.out.println(childNodes.item(j).getLastChild().toString());
					//System.out.println(childNodes.item(j).getPreviousSibling().toString());
					System.out.println(childNodes.item(j));
				}
				*/
				
				for (int j = 0; j < childNodes.getLength(); j++) {
					System.out.println("J = " + j);
					System.out.println(childNodes.item(j).getTextContent().replaceAll("\\s","").replaceAll("\\s",""));
					System.out.println(org.joox.JOOX.$(childNodes.item(j)).xpath());;
				}
			}
			
			
		} catch (Exception ex) {
			Logger.getLogger(XPath.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
