package binnie.extrabees.items;

import jline.internal.Nullable;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.Tabs;
import forestry.core.items.IColoredItem;
import forestry.api.apiculture.BeeManager;
import forestry.api.arboriculture.TreeManager;
import forestry.api.lepidopterology.ButterflyManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.Mods;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.items.types.EnumHoneyComb;
import net.minecraftforge.oredict.OreDictionary;

public class ItemHoneyComb extends ItemProduct<EnumHoneyComb> implements IColoredItem {

	private static final float RESEARCH_SUITABILITY = 0.4f;
	private static final String BOTANY_UID = "rootFlowers";

	private void setResearchSuitability(@Nullable ISpeciesRoot speciesRoot) {
		if (speciesRoot != null) {
			speciesRoot.setResearchSuitability(new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), RESEARCH_SUITABILITY);
		}
	}

	public ItemHoneyComb() {
		super(EnumHoneyComb.values());
		this.setCreativeTab(Tabs.tabApiculture);
		this.setUnlocalizedName("honey_comb");
		setRegistryName("honey_comb");
		BeeManager.beeRoot.setResearchSuitability(new ItemStack(this, 1, OreDictionary.WILDCARD_VALUE), RESEARCH_SUITABILITY);
		setResearchSuitability(TreeManager.treeRoot);
		setResearchSuitability(ButterflyManager.butterflyRoot);

		setResearchSuitability(AlleleManager.alleleRegistry.getSpeciesRoot(BOTANY_UID));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		EnumHoneyComb honeyComb = get(stack);
		return honeyComb.getSpriteColour(tintIndex);
	}

	public static boolean isInvalidComb(ItemStack stack){
		if(stack.isEmpty()){
			return true;
		}
		if(stack.getItem() != ExtraBees.comb){
			return false;
		}
		EnumHoneyComb honeyComb = EnumHoneyComb.get(stack);
		return !honeyComb.isActive();
	}

	public enum VanillaComb {
		HONEY,
		COCOA,
		SIMMERING,
		STRINGY,
		FROZEN,
		DRIPPING,
		SILKY,
		PARCHED,
		MYSTERIOUS,
		IRRADIATED,
		POWDERY,
		REDDENED,
		DARKENED,
		OMEGA,
		WHEATEN,
		MOSSY,
		QUARTZ;

		public ItemStack get() {
			return new ItemStack(Mods.Forestry.item("bee_combs"), 1, this.ordinal());
		}

	}
}
