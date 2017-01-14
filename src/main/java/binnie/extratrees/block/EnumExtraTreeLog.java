package binnie.extratrees.block;

import binnie.Constants;
import forestry.api.arboriculture.IWoodType;

public enum EnumExtraTreeLog implements IWoodType {
	Apple("Apple", PlankType.ExtraTreePlanks.Apple),
	Fig("Fig", PlankType.ExtraTreePlanks.Fig),
	Butternut("Butternut", PlankType.ExtraTreePlanks.Butternut),
	Cherry("Cherry", PlankType.ForestryPlanks.CHERRY),
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
	Cinnamon("Cinnamon", PlankType.VanillaPlanks.JUNGLE),
	PinkIvory("Pink Ivory", PlankType.ExtraTreePlanks.PinkIvory),
	EMPTY("EMPTY", PlankType.VanillaPlanks.OAK);//TODO change name/create wood for SHRUBS

	String name;
	IPlankType plank;

	EnumExtraTreeLog(final String name, final IPlankType plank) {
		this.name = name;
		this.plank = plank;
	}

	@Override
	public String getName() {
		return name().toLowerCase();
	}

	public String getDisplayName() {
		return this.name;
	}


//    public void addRecipe() {
//        if (this.plank == null) {
//            return;
//        }
//        final ItemStack log = this.getItemStack();
//        final ItemStack result = this.plank.getStack();
//        result.stackSize = 4;
//        GameRegistry.addShapelessRecipe(result, new Object[]{log});
//    }

	@Override
	public float getHardness() {
		return 5;
	}

	@Override
	public int getCarbonization() {
		return 0; //TODO return valid value
	}

	@Override
	public float getCharcoalChance(int numberOfCharcoal) {
		return 0;//TODO return valid value
	}

	@Override
	public String getHeartTexture() {
		return Constants.EXTRA_TREES_MOD_ID + ":blocks/logs/" + name().toLowerCase() + "Trunk";
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
		return Constants.EXTRA_TREES_MOD_ID + ":blocks/logs/" + name().toLowerCase() + "Bark";
	}

	@Override
	public String getPlankTexture() {
		return plank.getPlankTextureName();
	}

	@Override
	public int getMetadata() {
		return ordinal();
	}
}

