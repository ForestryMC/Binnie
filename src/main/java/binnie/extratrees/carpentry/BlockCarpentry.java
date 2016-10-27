package binnie.extratrees.carpentry;

import binnie.Binnie;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.block.PlankType;
import forestry.api.core.Tabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class BlockCarpentry extends BlockDesign {
	public BlockCarpentry(String name) {
		super(DesignSystem.Wood, Material.WOOD);
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setRegistryName(name);
		this.setResistance(5.0f);
		this.setHardness(2.0f);
		this.setSoundType(SoundType.WOOD);
	}

	@Override
	public ItemStack getCreativeStack(final IDesign design) {
		return ModuleCarpentry.getItemStack(this, PlankType.ExtraTreePlanks.Apple, PlankType.VanillaPlanks.BIRCH, design);
	}

	@Override
	public String getBlockName(final DesignBlock design) {
		return Binnie.Language.localise(ExtraTrees.instance, "block.woodentile.name", design.getDesign().getName());
	}
}
