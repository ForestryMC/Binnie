package binnie.extrabees.utils.config;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.Set;

public class ConfigHandler {

	private final Configuration config;
	private final Set<IConfigurable> configurables;

	public ConfigHandler(File location) {
		config = new Configuration(location);
		configurables = Sets.newHashSet();
	}

	public boolean addConfigurable(IConfigurable configurable) {
		return configurables.add(Preconditions.checkNotNull(configurable));
	}

	public void reload(boolean load) {
		if (load) {
			config.load();
		}
		for (IConfigurable configurable : configurables) {
			configurable.configure(config);
		}
		if (config.hasChanged()) {
			config.save();
		}
	}
}
