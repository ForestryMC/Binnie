package binnie.extratrees.block;

import binnie.extratrees.ExtraTrees;
import net.minecraft.util.IIcon;

public enum DoorType {
	Standard("standard"),
	Solid("solid"),
	Double("double"),
	Full("full");

	protected String id;
	protected IIcon iconDoorLower;
	protected IIcon iconDoorUpper;
	protected IIcon iconDoorLowerFlip;
	protected IIcon iconDoorUpperFlip;
	protected IIcon iconItem;

	DoorType(String iconName) {
		id = iconName;
	}

	public String getName() {
		return ExtraTrees.proxy.localise("block.door.type." + id);
	}
}
