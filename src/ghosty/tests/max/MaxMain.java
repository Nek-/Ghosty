package ghosty.tests.max;

import ghosty.config.loader.LoadException;
import ghosty.config.loader.XmlConfigLoader;
import ghosty.config.utils.ConfigMapList;

public class MaxMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    MaxMain.testLoader();
	}
	

	private static void testLoader() {
		XmlConfigLoader loader = new XmlConfigLoader();
		ConfigMapList cml;
		try {
			cml = loader.load("/home/nek/lol.xml");
			System.out.println(cml);
		} catch (LoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
