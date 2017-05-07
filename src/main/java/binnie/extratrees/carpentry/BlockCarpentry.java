package binnie.extratrees.carpentry;

import binnie.Binnie;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.block.PlankType;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class BlockCarpentry extends BlockDesign {
	public BlockCarpentry() {
		super(DesignSystem.Wood, Material.wood);
		setCreativeTab(Tabs.tabArboriculture);
		setBlockName("carpentry");
		setResistance(5.0f);
		setHardness(2.0f);
		setStepSound(Block.soundTypeWood);
	}

	@Override
	public ItemStack getCreativeStack(IDesign design) {
		return ModuleCarpentry.getItemStack(this, PlankType.ExtraTreePlanks.Apple, PlankType.VanillaPlanks.BIRCH, design);
	}

	@Override
	public String getBlockName(DesignBlock design) {
		return Binnie.I18N.localise(ExtraTrees.instance, "block.woodentile.name", design.getDesign().getName());
	}

}
