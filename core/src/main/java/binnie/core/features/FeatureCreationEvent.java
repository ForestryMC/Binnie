package binnie.core.features;

import net.minecraftforge.fml.common.eventhandler.Event;

public class FeatureCreationEvent extends Event {
	public final String containerID;
	public final FeatureType type;

	public FeatureCreationEvent(String containerID, FeatureType type) {
		this.containerID = containerID;
		this.type = type;
	}
}
