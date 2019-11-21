package binnie.extratrees.blocks;

import binnie.core.util.I18N;
import binnie.design.DesignHelper;
import binnie.design.api.IDesign;
import binnie.design.blocks.BlockDesign;
import binnie.design.blocks.DesignBlock;
import binnie.extratrees.CreativeTabCarpentry;
import binnie.extratrees.carpentry.DesignSystem;
import binnie.extratrees.wood.planks.ExtraTreePlanks;
import binnie.extratrees.wood.planks.VanillaPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class BlockCarpentry extends BlockDesign {
	public BlockCarpentry(String name) {
		super(DesignSystem.Wood, Material.WOOD);
		this.setCreativeTab(CreativeTabCarpentry.INSTANCE);
		this.setRegistryName(name);
		this.setResistance(5.0f);
		this.setHardness(2.0f);
		this.setSoundType(SoundType.WOOD);
	}

	@Override
	public ItemStack getCreativeStack(final IDesign design) {
		return DesignHelper.getItemStack(this, ExtraTreePlanks.Apple, VanillaPlanks.BIRCH, design);
	}

	@Override
	public String getBlockName(final DesignBlock design) {
		return I18N.localise("extratrees.block.woodentile.name", design.getDesign().getName());
	}
}
