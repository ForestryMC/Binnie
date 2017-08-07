package binnie.extratrees.block;

import java.util.Locale;

import forestry.api.arboriculture.IWoodType;

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
	Box("Box", PlankType.ExtraTreePlanks.Box),
	Syzgium("Syzgium", PlankType.ExtraTreePlanks.Syzgium),
	PinkIvory("PinkIvory", PlankType.ExtraTreePlanks.PinkIvory),
	Eucalyptus2("Eucalyptus", PlankType.ExtraTreePlanks.Eucalyptus, false),
	Eucalyptus3("Eucalyptus", PlankType.ExtraTreePlanks.Eucalyptus, false),
	Cherry("Cherry", PlankType.ForestryPlanks.CHERRY, false),
	Cinnamon("Cinnamon", PlankType.VanillaPlanks.JUNGLE, false);

	public static final EnumETLog[] VALUES = values();

	private final String name;
	private final String uid;
	private final IPlankType plank;
	private final boolean hasProducts;
	
	EnumETLog(final String name, final IPlankType plank) {
		this(name, plank, true);
	}

	EnumETLog(final String name, final IPlankType plank, boolean hasProducts) {
		this.name = name;
		this.uid = name.toLowerCase(Locale.ENGLISH).replace(" ", "_");
		this.plank = plank;
		this.hasProducts = hasProducts;
	}

	public static EnumETLog byMetadata(int meta) {
		if (meta < 0 || meta >= VALUES.length) {
			meta = 0;
		}
		return VALUES[meta];
	}
	
	public boolean hasProducts() {
		return hasProducts;
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

	@Override
	public float getHardness() {
		return 2.0F;
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
		return Constants.EXTRA_TREES_MOD_ID + ":blocks/logs/" + uid + "_trunk";
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
		return Constants.EXTRA_TREES_MOD_ID + ":blocks/logs/" + uid + "_bark";
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

	public String getUid() {
		return uid;
	}
}

