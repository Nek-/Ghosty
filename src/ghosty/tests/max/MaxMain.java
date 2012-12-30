package ghosty.tests.max;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import ghosty.config.ConfigFactory;
import ghosty.config.loader.LoadException;
import ghosty.config.loader.XmlConfigLoader;
import ghosty.config.saver.SaveException;
import ghosty.config.saver.XmlConfigSaver;
import ghosty.config.utils.ConfigMapList;
import ghosty.files.Directory;
import ghosty.md5.CheckException;
import ghosty.md5.Checker;

public class MaxMain {

	private static String file = "C:\\Users\\Nek\\Documents\\GitHub\\Ghosty\\TestFolder\\lol.xml";


	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    //MaxMain.testLoader();
		//MaxMain.testSaver();
		MaxMain.testMd5();
	}
	

	private static void testSaver() {
		ConfigMapList cml = new ConfigMapList();
		cml.put("Hello", "The world");
		cml.put("key", "fesrgdhtyjut rehge znrfg egeb erg nt23146 o�''\"-�_�_��");
		
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
	
	private static void testMd5() {
		// Test the md5 hash
		String str = Checker.hash((new String("Coucou")).getBytes());
		System.out.println("Hash de coucou : " + str); // Must return "41060d3ddfdf63e68fc2bf196f652ee9"
		
		// Test the md5 on a file list

		try {
			ArrayList<Path> a = Directory.getFilesFromPath("/home/nek/test/");
			
			String[] str2 = Checker.check(
					Arrays.copyOf(a.toArray(), a.size(), Path[].class),
					new ConfigFactory("/home/nek/test")
				);
			for(String s : str2) {
				System.out.println(s);
			}
		} catch (LoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CheckException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
