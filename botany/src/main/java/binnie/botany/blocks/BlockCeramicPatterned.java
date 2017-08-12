package binnie.botany.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

import binnie.botany.ceramic.CeramicColor;
import binnie.botany.ceramic.CeramicDesignSystem;
import binnie.botany.api.genetics.EnumFlowerColor;
import binnie.core.util.I18N;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.carpentry.BlockDesign;
import binnie.extratrees.carpentry.DesignBlock;
import binnie.extratrees.modules.ModuleCarpentry;

public class BlockCeramicPatterned extends BlockDesign {
	public BlockCeramicPatterned() {
		super(CeramicDesignSystem.instance, Material.ROCK);
		setHardness(1.0f);
		setResistance(5.0f);
		setRegistryName("ceramicPattern");
	}

	@Override
	public ItemStack getCreativeStack(IDesign design) {
		return ModuleCarpentry.getItemStack(this, CeramicColor.get(EnumFlowerColor.White), CeramicColor.get(EnumFlowerColor.Black), design);
	}

	@Override
	public String getBlockName(DesignBlock design) {
		return I18N.localise("botany.ceramic.block.name", design.getDesign().getName());
	}
}
