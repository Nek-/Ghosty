package ghosty.config.saver;

import ghosty.config.utils.ConfigMapList;

public interface ConfigSaverInterface {

	void save(String path, ConfigMapList config) throws SaveException;
}
