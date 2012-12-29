package ghosty.tests.max;

import ghosty.config.loader.LoadException;
import ghosty.config.loader.XmlConfigLoader;
import ghosty.config.saver.SaveException;
import ghosty.config.saver.XmlConfigSaver;
import ghosty.config.utils.ConfigMapList;

public class MaxMain {

	private static String file = "C:\\Users\\Nek\\Documents\\GitHub\\Ghosty\\TestFolder\\lol.xml";


	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    //MaxMain.testLoader();
		MaxMain.testSaver();
	}
	

	private static void testSaver() {
		ConfigMapList cml = new ConfigMapList();
		cml.put("Hello", "The world");
		cml.put("key", "fesrgdhtyjut rehge znrfg egeb erg nt23146 oй''\"-и_з_ай");
		
		XmlConfigSaver saver = new XmlConfigSaver();
		
		try {
			saver.save(MaxMain.file, cml);
		} catch (SaveException e) {
			System.out.println("Save failed");
			e.printStackTrace();
		}
	}


	private static void testLoader() {
		XmlConfigLoader loader = new XmlConfigLoader();
		ConfigMapList cml;
		try {
			//cml = loader.load("/home/nek/lol.xml");
			cml = loader.load(MaxMain.file);
			System.out.println(cml);
		} catch (LoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
