package ghosty.config.loader;

import ghosty.config.utils.ConfigMapList;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Load a configuration file who use XML
 * 
 * Example of possible file:
 * <configuration>
 * 		<option>
 * 			<key></key>
 * 			<value></value>
 * 		</option>
 * </configuration>
 *
 */
public class XmlConfigLoader implements ConfigLoaderInterface {

	@Override
	public ConfigMapList load(String path) throws LoadException {
		ConfigMapList res = new ConfigMapList();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			InputStream fXmlFile = null;
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			NodeList options = doc.getDocumentElement().getElementsByTagName("option");
			Node currentOption;
			Element currentOptionElement;
			
			for (int i = 0; i < options.getLength(); i++) {
				currentOption = options.item(i);
				
				if (currentOption.getNodeType() == Node.ELEMENT_NODE) {
					currentOptionElement = (Element) currentOption;
					res.put(
						this.getTextFromTagNode(currentOptionElement, "key"),
						this.getTextFromTagNode(currentOptionElement, "value")
					);
				}
			}
			
		} catch (Exception e) {
			throw new LoadException(path);
		}
		
		
		return null;
	}
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	private String getTextFromTagNode(Element e, String tag) {

		NodeList nl = e.getElementsByTagName(tag).item(0).getChildNodes();
		
		Node n = (Node) nl.item(0);
		
		return n.getNodeValue();
	}

}
