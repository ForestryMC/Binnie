package binnie.extrabees.utils;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialBeehive extends Material {

	public MaterialBeehive() {
		super(MapColor.STONE);
		this.setRequiresTool();
		this.setImmovableMobility();
	}

	@Override
	public boolean isOpaque() {
		return true;
	}

}
