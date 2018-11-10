package binnie.extrabees.items;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import forestry.api.apiculture.BeeManager;
import forestry.api.arboriculture.TreeManager;
import forestry.api.core.Tabs;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.lepidopterology.ButterflyManager;
import forestry.core.items.IColoredItem;

import binnie.extrabees.items.types.EnumHoneyDrop;

public class ItemHoneyDrop extends ItemProduct<EnumHoneyDrop> implements IColoredItem {

	private static final float RESEARCH_SUITABILITY = 0.5f;
	private static final String BOTANY_UID = "rootFlowers";

	private void setResearchSuitability(@Nullable ISpeciesRoot speciesRoot) {
		if (speciesRoot != null) {
			speciesRoot.setResearchSuitability(new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), RESEARCH_SUITABILITY);
		}
	}

	public ItemHoneyDrop() {
		super(EnumHoneyDrop.values());
		this.setCreativeTab(Tabs.tabApiculture);
		this.setUnlocalizedName("honey_drop");
		setRegistryName("honey_drop");
		BeeManager.beeRoot.setResearchSuitability(new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), RESEARCH_SUITABILITY);
		setResearchSuitability(TreeManager.treeRoot);
		setResearchSuitability(ButterflyManager.butterflyRoot);

		setResearchSuitability(AlleleManager.alleleRegistry.getSpeciesRoot(BOTANY_UID));
	}

	@Override
	public int getColorFromItemstack(ItemStack itemStack, int tintIndex) {
		EnumHoneyDrop drop = get(itemStack);
		return drop.getSpriteColour(tintIndex);
	}
}
