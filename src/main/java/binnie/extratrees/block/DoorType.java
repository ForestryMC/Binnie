package binnie.extratrees.block;

import binnie.extratrees.ExtraTrees;
import net.minecraft.util.IIcon;

public enum DoorType {
	STANDARD("standard"),
	SOLID("solid"),
	DOUBLE("double"),
	FULL("full");

	protected String id;
	protected IIcon iconDoorLower;
	protected IIcon iconDoorUpper;
	protected IIcon iconDoorLowerFlip;
	protected IIcon iconDoorUpperFlip;
	protected IIcon iconItem;

	DoorType(String id) {
		this.id = id;
	}

	public String getName() {
		return ExtraTrees.proxy.localise("block.door.type." + id);
	}
}
