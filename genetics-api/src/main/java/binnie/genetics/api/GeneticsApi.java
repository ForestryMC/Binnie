package binnie.genetics.api;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import forestry.api.genetics.IIndividual;

public class GeneticsApi {
	private static Collection<IAnalystPagePlugin> analystPagePlugins = new ArrayList<>();
	private static Collection<IProducePlugin> producePlugins = new ArrayList<>();

	public static void registerAnalystPagePlugin(IAnalystPagePlugin<?> analystPageFactory) {
		analystPagePlugins.add(analystPageFactory);
	}

	@Nullable
	public static <T extends IIndividual> IAnalystPagePlugin<T> getAnalystPagePlugin(T individual) {
		for (IAnalystPagePlugin plugin : analystPagePlugins) {
			if (plugin.handles(individual)) {
				//noinspection unchecked
				return plugin;
			}
		}
		return null;
	}

	public static void registerProducePlugin(IProducePlugin producePlugin) {
		producePlugins.add(producePlugin);
	}

	public static Collection<IProducePlugin> getProducePlugins() {
		return Collections.unmodifiableCollection(producePlugins);
	}
}
