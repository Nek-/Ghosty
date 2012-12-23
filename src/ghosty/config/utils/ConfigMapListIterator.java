package ghosty.config.utils;

import java.util.Iterator;

public class ConfigMapListIterator implements Iterator {
	
	/**
	 * Store the current list
	 */
	private ConfigMapList list;
	
	/**
	 * We can iterate with index since the ConfigMapList use ArrayLists
	 */
	private int currentIndex;
	
	public ConfigMapListIterator(ConfigMapList list) {
		this.list 			= list;
		this.currentIndex 	= -1;
	}

	@Override
	public boolean hasNext() {
		
		return this.list.size() > (this.currentIndex + 1);
	}

	@Override
	public String next() {
		this.currentIndex++;
		return this.list.getValue(this.currentIndex);
	}

	public String nextKey() {
		this.currentIndex++;
		return this.list.getKey(this.currentIndex);
	}

	@Override
	public void remove() {
		this.list.remove(this.currentIndex);

	}

}
