package httpParse;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import nasa.neoAstroid.news.newsEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import android.util.Log;
import android.graphics.drawable.Drawable;

public class XmlParser {

	private DocumentBuilderFactory domFactory;
	private DocumentBuilder builder;
	private Document doc;
	private XPath xpath;
	public String tempImageURL_DOC;
	
	public XmlParser(String XmlFile) {
		try {
			tempImageURL_DOC = XmlFile;
//			Log.i("xpath", "XmlParser_XmlFile :" + XmlFile);
			domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(true);
			domFactory.setCoalescing(true);
			builder = domFactory.newDocumentBuilder();
			doc = stringToDom(XmlFile);
			xpath = XPathFactory.newInstance().newXPath();
		} catch (Exception ex) {
			Log.e("xpath", "Error Setting upXpath");
			Log.e("xpath", ex.getMessage());
		}
	}

	public XmlParser(){}
	
	public static Document stringToDom(String xmlSource) throws SAXException,
		ParserConfigurationException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new InputSource(new StringReader(xmlSource)));
	}

	public String getXpath_single(String Xpath) {
		String XPath_Results = "";
		try {
			// ArrayList nbList = new ArrayList();
			XPathExpression expr = xpath.compile(Xpath);
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			XPath_Results = nodes.item(0).getNodeValue();
			for (int i = 0; i < nodes.getLength(); i++) {
//				Log.i("xpath", "got data");
//				Log.i("xpath", nodes.item(i).getNodeValue());
				XPath_Results = nodes.item(i).getNodeValue();
			}
		} catch (Exception e) {
//			Log.i("xpath", "xpath Error");
		}
		return XPath_Results;
	}

	public ArrayList<String> getXpath(String Xpath) {
//		Log.i("xpath", "XpathString: " + Xpath);
		ArrayList<String> arList = new ArrayList<String>();
		try {
			NodeList nodes = (NodeList) xpath.evaluate(Xpath, doc, XPathConstants.NODESET);
//			Log.i("xpath", "NodeLength: " + nodes.getLength());
			for (int i = 0; i < nodes.getLength(); i++) {
				Log.i("xpath", "Nodes: " + nodes.item(i).getNodeValue());
				arList.add(nodes.item(i).getNodeValue());
			}
		} catch (Exception e) {
			Log.e("xpath", "Exception!!! Somthing went wrong");
			e.printStackTrace();
		}
		return arList;
	}

	public ArrayList<String> getXpath_old(String Xpath) {
//		Log.i("xpath", "getXpath_XpathString: " + Xpath);
		ArrayList<String> arList = new ArrayList<String>();
		try {
			XPathExpression expr = xpath.compile(Xpath);
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
//			Log.i("xpath", "getting multi-data");
//			Log.i("xpath", "NodeLength: " + nodes.getLength());
			for (int i = 0; i < nodes.getLength(); i++) {
//				Log.i("xpath", "Nodes: " + nodes.item(i).getNodeValue());
				arList.add(nodes.item(i).getNodeValue());
			}
		} catch (Exception e) {
			Log.i("xpath", "xpath Error multi");
		}
		return arList;
	}

	public String getLastUpdated() {
		String entityCount = "";
		try {
			// ArrayList nbList = new ArrayList();
			XPathExpression expr = xpath.compile("feed/updated/text()");
			Object result = expr.evaluate(doc, XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			entityCount = nodes.item(0).getNodeValue();
			for (int i = 0; i < nodes.getLength(); i++) {
//				Log.i("xpath", "got data");
//				Log.i("xpath", nodes.item(i).getNodeValue());
				entityCount = nodes.item(0).getNodeValue();
			}
		} catch (Exception e) {
			Log.i("xpath", "xpath Error");
		}
		return entityCount;
	}

    public ArrayList<newsEntity> getXpath_getNewsItem() {
    		Log.i("news", "Getting xpath stuff");
    		ArrayList<newsEntity> newsList = new ArrayList<newsEntity>();
    		try {
    		    NodeList nodes = doc.getElementsByTagName("item");
    		    Log.i("news", "Getting xpath getLength "+nodes.getLength());
    			  for (int i = 0; i < nodes.getLength(); i++) {
    				  newsEntity JPLEntity = new newsEntity();
    			      Element element = (Element) nodes.item(i);
    			      NodeList title = element.getElementsByTagName("title");
    			      NodeList description = element.getElementsByTagName("description");
    			      NodeList pubDate = element.getElementsByTagName("pubDate");
    			      NodeList link = element.getElementsByTagName("link");
    				  String descriptionSTR = description.item(0).getTextContent().toString().trim();
    				  int bIdx = descriptionSTR.indexOf("<br /><br />");
    				  int eIdx = descriptionSTR.indexOf("</p>");
    				  int imgIDX = descriptionSTR.indexOf("img src=\"");
    				  int imgEDX = descriptionSTR.indexOf(".jpg\"");
    				  String strDesc = descriptionSTR.substring(bIdx+12,eIdx);
    				  String imgURL = descriptionSTR.substring(imgIDX+9,imgEDX+4);
    				  Log.i("news", "Getting xpath title: "+title.item(0).getTextContent().toString().trim());
    				  JPLEntity.title = title.item(0).getTextContent().toString().trim();
//    				  JPLEntity.pubDate = pubDate.item(0).getTextContent().toString().trim();
    				  JPLEntity.setPubDate(pubDate.item(0).getTextContent().toString().trim());
    				  JPLEntity.artcileUrl =  link.item(0).getTextContent().toString().trim();
    				  JPLEntity.description =  strDesc.trim();
    				  JPLEntity.imgURL = ImageOperations(imgURL.trim());	
    				  newsList.add(JPLEntity);
    				  if(i==10){
    				  break;
    			  }  
    			  }
    		} catch (Exception e) {
    		    Log.e("news", "xpath Exception!!! Somthing went wrong");
    		    Log.e("news", e.getMessage());
//    		    Log.e("news", e.getCause());
    			Log.e("xpath", "Exception!!! Somthing went wrong");
//    			e.printStackTrace();
    		}
    		return newsList;
    	}

	private Drawable ImageOperations(String url) {
		try {
			InputStream is = (InputStream) this.fetch(url);
			Drawable d = Drawable.createFromStream(is, "src");
			return d;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object fetch(String address) throws MalformedURLException,IOException {
		URL url = new URL(address);
		Object content = url.getContent();
		return content;
	}
	

}
