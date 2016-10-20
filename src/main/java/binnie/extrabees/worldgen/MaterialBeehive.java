// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.worldgen;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialBeehive extends Material
{
	public MaterialBeehive() {
		super(MapColor.stoneColor);
		this.setRequiresTool();
		this.setImmovableMobility();
	}

	@Override
	public boolean isOpaque() {
		return true;
	}
}
