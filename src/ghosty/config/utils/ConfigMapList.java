package ghosty.config.utils;

import java.util.ArrayList;

import java.util.Iterator;

/**
 * Simulate a HashMap<String, String> who support iteration and removing features
 *
 */
public class ConfigMapList implements Iterable {
	/**
	 * ArrayList who contains keys
	 */
	private ArrayList<String> keys;
	
	/**
	 * ArrayList who contains values
	 */
	private ArrayList<String> values;
	
	/**
	 * Create array lists
	 */
	public ConfigMapList() {
		this.keys 	= new ArrayList<String>();
		this.values = new ArrayList<String>();
	}
	
	/**
	 * Put a new key
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, String value) {
		this.keys.add(key);
		this.values.add(value);
	}

	/**
	 * Get the value from the key specified
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return this.values.get(this.keys.indexOf(key));
	}
	
	/**
	 * Get the value from the index specified
	 * 
	 * @param index
	 * @return
	 */
	public String getValue(int index) {
		return this.values.get(index);
	}
	
	/**
	 * Get the key from the index specified
	 * 
	 * @param index
	 * @return
	 */
	public String getKey(int index) {
		return this.keys.get(index);
	}
	
	
	public void remove(int index) {
		
		if((this.keys.size()-1) > index) {

			this.keys.remove(index);
			this.values.remove(index);
		}
		
	}
	
	/**
	 * Check if the specified key exists in the map
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key) {
		return this.keys.contains(key);
	}
	
	public boolean isEmpty() {
		return this.keys.size() == 0;
	}

	@Override
	public Iterator iterator() {
		return new ConfigMapListIterator(this);
	}
	
	/**
	 * 
	 * @return
	 */
	public int size() {
		return this.keys.size();
	}
}
