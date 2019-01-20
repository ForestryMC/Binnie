package binnie.core.features;

import net.minecraftforge.fml.common.eventhandler.Event;

public class RegisterFeatureEvent extends Event {
	public void register(Class<? extends IModFeature> featureClass) {
		for (IModFeature feature : featureClass.getEnumConstants()) {
			ModFeatureRegistry.get(feature.getModId()).register(feature);
		}
	}

}
