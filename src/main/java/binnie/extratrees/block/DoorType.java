// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.block;

import binnie.extratrees.ExtraTrees;
import net.minecraft.util.IIcon;

public enum DoorType
{
	Standard("standard"),
	Solid("solid"),
	Double("double"),
	Full("full");

	String id;
	IIcon iconDoorLower;
	IIcon iconDoorUpper;
	IIcon iconDoorLowerFlip;
	IIcon iconDoorUpperFlip;
	IIcon iconItem;

	private DoorType(final String iconName) {
		this.id = iconName;
	}

	public String getName() {
		return ExtraTrees.proxy.localise("block.door.type." + this.id);
	}
}
