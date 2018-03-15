package binnie.extrabees.items;

import net.minecraft.item.ItemStack;

import forestry.api.core.Tabs;
import forestry.core.items.IColoredItem;
import forestry.api.apiculture.BeeManager;
import forestry.api.arboriculture.TreeManager;
import forestry.api.lepidopterology.ButterflyManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;

import binnie.extrabees.items.types.EnumHoneyDrop;
import net.minecraftforge.oredict.OreDictionary;

public class ItemHoneyDrop extends ItemProduct<EnumHoneyDrop> implements IColoredItem {

	static final float RESEARCH_SUITABILITY = 0.5f;
	static final String BOTANY_UID = "rootFlowers";

	public ItemHoneyDrop() {
		super(EnumHoneyDrop.values());
		this.setCreativeTab(Tabs.tabApiculture);
		this.setUnlocalizedName("honey_drop");
		setRegistryName("honey_drop");
		BeeManager.beeRoot.setResearchSuitability(new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), RESEARCH_SUITABILITY);
		TreeManager.treeRoot.setResearchSuitability(new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), RESEARCH_SUITABILITY);
		ButterflyManager.butterflyRoot.setResearchSuitability(new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), RESEARCH_SUITABILITY);

		ISpeciesRoot botanyRoot = AlleleManager.alleleRegistry.getSpeciesRoot(BOTANY_UID);
		if (botanyRoot != null) {
			botanyRoot.setResearchSuitability(new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), RESEARCH_SUITABILITY);
		}
	}

	@Override
	public int getColorFromItemstack(ItemStack itemStack, int tintIndex) {
		EnumHoneyDrop drop = get(itemStack);
		return drop.getSpriteColour(tintIndex);
	}
}
