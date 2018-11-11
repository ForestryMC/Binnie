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

import binnie.core.item.ItemMisc;
import binnie.extrabees.items.types.EnumHoneyDrop;

public class ItemHoneyDrop extends ItemMisc<EnumHoneyDrop> implements IColoredItem {

	private static final float RESEARCH_SUITABILITY = 0.5f;
	private static final String BOTANY_UID = "rootFlowers";

	public ItemHoneyDrop() {
		super(Tabs.tabApiculture, EnumHoneyDrop.values(), "honey_drop");
		setResearchSuitability(BeeManager.beeRoot);
		setResearchSuitability(TreeManager.treeRoot);
		setResearchSuitability(ButterflyManager.butterflyRoot);

		setResearchSuitability(AlleleManager.alleleRegistry.getSpeciesRoot(BOTANY_UID));
	}

	private void setResearchSuitability(@Nullable ISpeciesRoot speciesRoot) {
		if (speciesRoot != null) {
			speciesRoot.setResearchSuitability(new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), RESEARCH_SUITABILITY);
		}
	}

	@Override
	public int getColorFromItemstack(ItemStack itemStack, int tintIndex) {
		EnumHoneyDrop drop = getProvider(itemStack);
		return drop.getSpriteColour(tintIndex);
	}
}
