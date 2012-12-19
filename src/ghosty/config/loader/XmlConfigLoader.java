package ghosty.config.loader;

import ghosty.config.utils.ConfigMapList;

import java.io.IOException;
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
		DocumentBuilder docBuilder;
		
		try {
			docBuilder = dbFactory.newDocumentBuilder();

			Document doc = docBuilder.parse(path);
			
			
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
			
		} catch (IOException e) {
			// Nothing to do, the file doesn't exists but it's not a problem
			System.out.println("The configuration file doesn't exists.");
		} catch (Exception e) {
			throw new LoadException(path);
		}
		
		
		return res;
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
