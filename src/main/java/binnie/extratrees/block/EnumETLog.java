package binnie.extratrees.block;

import java.util.Locale;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.registry.GameRegistry;

import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;

import binnie.Constants;

public enum EnumETLog implements IWoodType {
	Apple("Apple", PlankType.ExtraTreePlanks.Apple),
	Fig("Fig", PlankType.ExtraTreePlanks.Fig),
	Butternut("Butternut", PlankType.ExtraTreePlanks.Butternut),
	Whitebeam("Whitebeam", PlankType.ExtraTreePlanks.Whitebeam),
	Rowan("Rowan", PlankType.ExtraTreePlanks.Rowan),
	Hemlock("Hemlock", PlankType.ExtraTreePlanks.Hemlock),
	Ash("Ash", PlankType.ExtraTreePlanks.Ash),
	Alder("Alder", PlankType.ExtraTreePlanks.Alder),
	Beech("Beech", PlankType.ExtraTreePlanks.Beech),
	Hawthorn("Hawthorn", PlankType.ExtraTreePlanks.Hawthorn),
	Banana("Banana", PlankType.ExtraTreePlanks.Banana),
	Yew("Yew", PlankType.ExtraTreePlanks.Yew),
	Cypress("Cypress", PlankType.ExtraTreePlanks.Cypress),
	Fir("Fir", PlankType.ExtraTreePlanks.Fir),
	Hazel("Hazel", PlankType.ExtraTreePlanks.Hazel),
	Hickory("Hickory", PlankType.ExtraTreePlanks.Hickory),
	Elm("Elm", PlankType.ExtraTreePlanks.Elm),
	Elder("Elder", PlankType.ExtraTreePlanks.Elder),
	Holly("Holly", PlankType.ExtraTreePlanks.Holly),
	Hornbeam("Hornbeam", PlankType.ExtraTreePlanks.Hornbeam),
	Cedar("Cedar", PlankType.ExtraTreePlanks.Cedar),
	Olive("Olive", PlankType.ExtraTreePlanks.Olive),
	Sweetgum("Sweetgum", PlankType.ExtraTreePlanks.Sweetgum),
	Locust("Locust", PlankType.ExtraTreePlanks.Locust),
	Pear("Pear", PlankType.ExtraTreePlanks.Pear),
	Maclura("Maclura", PlankType.ExtraTreePlanks.Maclura),
	Brazilwood("Brazilwood", PlankType.ExtraTreePlanks.Brazilwood),
	Logwood("Logwood", PlankType.ExtraTreePlanks.Logwood),
	Rosewood("Rosewood", PlankType.ExtraTreePlanks.Rosewood),
	Purpleheart("Purpleheart", PlankType.ExtraTreePlanks.Purpleheart),
	Iroko("Iroko", PlankType.ExtraTreePlanks.Iroko),
	Gingko("Gingko", PlankType.ExtraTreePlanks.Gingko),
	Eucalyptus("Eucalyptus", PlankType.ExtraTreePlanks.Eucalyptus),
	Eucalyptus2("Eucalyptus", PlankType.ExtraTreePlanks.Eucalyptus),
	Box("Box", PlankType.ExtraTreePlanks.Box),
	Syzgium("Syzgium", PlankType.ExtraTreePlanks.Syzgium),
	Eucalyptus3("Eucalyptus", PlankType.ExtraTreePlanks.Eucalyptus),
	PinkIvory("Pink Ivory", PlankType.ExtraTreePlanks.PinkIvory),
	Cherry("Cherry", PlankType.ForestryPlanks.CHERRY),
	Cinnamon("Cinnamon", PlankType.VanillaPlanks.JUNGLE);

	public static final EnumETLog[] VALUES = values();

	private final String name;
	private final IPlankType plank;

	EnumETLog(final String name, final IPlankType plank) {
		this.name = name;
		this.plank = plank;
	}

	public static EnumETLog byMetadata(int meta) {
		if (meta < 0 || meta >= VALUES.length) {
			meta = 0;
		}
		return VALUES[meta];
	}

	@Override
	public String getName() {
		return name().toLowerCase();
	}

	@Override
	public String toString() {
		return getName();
	}

	public String getDisplayName() {
		return this.name;
	}

	public void addRecipe() {
		if (this.plank == null) {
			return;
		}
		ItemStack log = TreeManager.woodAccess.getStack(this, WoodBlockKind.LOG, false);
		ItemStack result = plank.getStack(false);
		result.setCount(4);
		GameRegistry.addShapelessRecipe(result, log);

		ItemStack logFireproof = TreeManager.woodAccess.getStack(this, WoodBlockKind.LOG, true);
		ItemStack resultFireproof = plank.getStack(true);
		resultFireproof.setCount(4);
		GameRegistry.addShapelessRecipe(resultFireproof, logFireproof);
	}

	@Override
	public float getHardness() {
		return 5;
	}

	@Override
	public int getCarbonization() {
		return 4;
	}

	@Override
	public float getCharcoalChance(int numberOfCharcoal) {
		if (numberOfCharcoal == 3) {
			return 0.75F;
		} else if (numberOfCharcoal == 4) {
			return 0.5F;
		} else if (numberOfCharcoal == 5) {
			return 0.25F;
		}
		return 0.15F;
	}

	@Override
	public String getHeartTexture() {
		String name = name().toLowerCase(Locale.ENGLISH).replace(" ", "_");
		return Constants.EXTRA_TREES_MOD_ID + ":blocks/logs/" + name + "_trunk";
	}

	@Override
	public String getDoorLowerTexture() {
		return Constants.EXTRA_TREES_MOD_ID + ":blocks/door.standard.lower";
	}

	@Override
	public String getDoorUpperTexture() {
		return Constants.EXTRA_TREES_MOD_ID + ":blocks/door.standard.upper";
	}

	@Override
	public String getBarkTexture() {
		String name = name().toLowerCase(Locale.ENGLISH).replace(" ", "_");
		return Constants.EXTRA_TREES_MOD_ID + ":blocks/logs/" + name + "_bark";
	}

	@Override
	public String getPlankTexture() {
		return plank.getPlankTextureName();
	}

	public IPlankType getPlank() {
		return plank;
	}

	@Override
	public int getMetadata() {
		return ordinal();
	}
}

