// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.ceramic;

import binnie.extratrees.carpentry.DesignBlock;
import binnie.extratrees.carpentry.ModuleCarpentry;
import binnie.botany.genetics.EnumFlowerColor;
import net.minecraft.item.ItemStack;
import binnie.extratrees.api.IDesign;
import binnie.botany.CreativeTabBotany;
import net.minecraft.block.material.Material;
import binnie.extratrees.carpentry.BlockDesign;

public class BlockCeramicPatterned extends BlockDesign
{
	public BlockCeramicPatterned() {
		super(CeramicDesignSystem.instance, Material.rock);
		this.setHardness(1.0f);
		this.setResistance(5.0f);
		this.setCreativeTab(CreativeTabBotany.instance);
		this.setBlockName("ceramicPattern");
	}

	@Override
	public ItemStack getCreativeStack(final IDesign design) {
		return ModuleCarpentry.getItemStack(this, CeramicColor.get(EnumFlowerColor.White), CeramicColor.get(EnumFlowerColor.Black), design);
	}

	@Override
	public String getBlockName(final DesignBlock design) {
		return design.getDesign().getName() + " Ceramic Block";
	}
}
