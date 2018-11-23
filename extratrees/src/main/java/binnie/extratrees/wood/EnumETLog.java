package binnie.extratrees.wood;

import java.util.Locale;

import forestry.api.arboriculture.IWoodType;

import binnie.core.Constants;
import binnie.extratrees.wood.planks.ExtraTreePlanks;
import binnie.extratrees.wood.planks.ForestryPlanks;
import binnie.extratrees.wood.planks.IPlankType;
import binnie.extratrees.wood.planks.VanillaPlanks;

public enum EnumETLog implements IWoodType {
	Apple("Apple", ExtraTreePlanks.Apple),
	Fig("Fig", ExtraTreePlanks.Fig),
	Butternut("Butternut", ExtraTreePlanks.Butternut),
	Whitebeam("Whitebeam", ExtraTreePlanks.Whitebeam),
	Rowan("Rowan", ExtraTreePlanks.Rowan),
	Hemlock("Hemlock", ExtraTreePlanks.Hemlock),
	Ash("Ash", ExtraTreePlanks.Ash),
	Alder("Alder", ExtraTreePlanks.Alder),
	Beech("Beech", ExtraTreePlanks.Beech),
	Hawthorn("Hawthorn", ExtraTreePlanks.Hawthorn),
	Banana("Banana", ExtraTreePlanks.Banana),
	Yew("Yew", ExtraTreePlanks.Yew),
	Cypress("Cypress", ExtraTreePlanks.Cypress),
	Fir("Fir", ExtraTreePlanks.Fir),
	Hazel("Hazel", ExtraTreePlanks.Hazel),
	Hickory("Hickory", ExtraTreePlanks.Hickory),
	Elm("Elm", ExtraTreePlanks.Elm),
	Elder("Elder", ExtraTreePlanks.Elder),
	Holly("Holly", ExtraTreePlanks.Holly),
	Hornbeam("Hornbeam", ExtraTreePlanks.Hornbeam),
	Cedar("Cedar", ExtraTreePlanks.Cedar),
	Olive("Olive", ExtraTreePlanks.Olive),
	Sweetgum("Sweetgum", ExtraTreePlanks.Sweetgum),
	Locust("Locust", ExtraTreePlanks.Locust),
	Pear("Pear", ExtraTreePlanks.Pear),
	Maclura("Maclura", ExtraTreePlanks.Maclura),
	Brazilwood("Brazilwood", ExtraTreePlanks.Brazilwood),
	Logwood("Logwood", ExtraTreePlanks.Logwood),
	Rosewood("Rosewood", ExtraTreePlanks.Rosewood),
	Purpleheart("Purpleheart", ExtraTreePlanks.Purpleheart),
	Iroko("Iroko", ExtraTreePlanks.Iroko),
	Gingko("Gingko", ExtraTreePlanks.Gingko),
	Eucalyptus("Eucalyptus", ExtraTreePlanks.Eucalyptus),
	Box("Box", ExtraTreePlanks.Box),
	Syzgium("Syzgium", ExtraTreePlanks.Syzgium),
	PinkIvory("PinkIvory", ExtraTreePlanks.PinkIvory),
	Eucalyptus2("Eucalyptus", ExtraTreePlanks.Eucalyptus, false),
	Eucalyptus3("Eucalyptus", ExtraTreePlanks.Eucalyptus, false),
	Cherry("Cherry", ForestryPlanks.CHERRY, false),
	Cinnamon("Cinnamon", VanillaPlanks.JUNGLE, false);

	public static final EnumETLog[] VALUES = values();

	private final String name;
	private final String uid;
	private final IPlankType plank;
	private final boolean hasProducts;

	EnumETLog(String name, IPlankType plank) {
		this(name, plank, true);
	}

	EnumETLog(String name, IPlankType plank, boolean hasProducts) {
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

