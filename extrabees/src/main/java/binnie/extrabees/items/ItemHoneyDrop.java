package binnie.extrabees.items;

import net.minecraft.item.ItemStack;

import forestry.api.core.Tabs;
import forestry.core.items.IColoredItem;
import forestry.api.apiculture.BeeManager;
import forestry.api.arboriculture.TreeManager;
import forestry.api.lepidopterology.ButterflyManager;

import binnie.extrabees.items.types.EnumHoneyDrop;
import net.minecraftforge.oredict.OreDictionary;

public class ItemHoneyDrop extends ItemProduct<EnumHoneyDrop> implements IColoredItem {

	public ItemHoneyDrop() {
		super(EnumHoneyDrop.values());
		this.setCreativeTab(Tabs.tabApiculture);
		this.setUnlocalizedName("honey_drop");
		setRegistryName("honey_drop");
		BeeManager.beeRoot.setResearchSuitability(new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), 0.5f);
		TreeManager.treeRoot.setResearchSuitability(new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), 0.5f);
		ButterflyManager.butterflyRoot.setResearchSuitability(new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), 0.5f);
	}

	@Override
	public int getColorFromItemstack(ItemStack itemStack, int tintIndex) {
		EnumHoneyDrop drop = get(itemStack);
		return drop.getSpriteColour(tintIndex);
	}
}
