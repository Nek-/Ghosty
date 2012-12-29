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
	
	private boolean more;
	
	public ConfigMapListIterator(ConfigMapList list) {
		this.list 			= list;
		this.currentIndex 	= -1;
	}

	@Override
	public boolean hasNext() {
		this.more = true;
		return this.list.size() > (this.currentIndex + 1);
	}

	@Override
	public String next() {
		this.goToNext();
		return this.list.getValue(this.currentIndex);
	}
	
	private void goToNext() {
		if(this.more) {
			this.currentIndex++;
			this.more = false;
		}
	}

	public String nextKey() {
		this.goToNext();
		return this.list.getKey(this.currentIndex);
	}

	@Override
	public void remove() {
		this.list.remove(this.currentIndex);

	}

}
