package binnie.botany.ceramic;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.util.I18N;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.carpentry.BlockDesign;
import binnie.extratrees.carpentry.DesignBlock;
import binnie.extratrees.carpentry.ModuleCarpentry;

public class BlockCeramicPatterned extends BlockDesign {
	public BlockCeramicPatterned() {
		super(CeramicDesignSystem.instance, Material.ROCK);
		this.setHardness(1.0f);
		this.setResistance(5.0f);
		this.setCreativeTab(CreativeTabBotany.instance);
		this.setRegistryName("ceramicPattern");
	}

	@Override
	public ItemStack getCreativeStack(final IDesign design) {
		return ModuleCarpentry.getItemStack(this, CeramicColor.get(EnumFlowerColor.White), CeramicColor.get(EnumFlowerColor.Black), design);
	}

	@Override
	public String getBlockName(final DesignBlock design) {
		return I18N.localise("botany.ceramic.block.name", design.getDesign().getName());
	}
}
