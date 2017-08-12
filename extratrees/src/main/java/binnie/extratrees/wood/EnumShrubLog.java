package binnie.extratrees.wood;

import forestry.api.arboriculture.IWoodType;

public enum EnumShrubLog implements IWoodType {
	INSTANCE;

	public static final EnumShrubLog[] VALUES = values();

	@Override
	public String getName() {
		return "shrub_log";
	}

	@Override
	public String toString() {
		return getName();
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
	public String getPlankTexture() {
		return "blocks/planks_oak";
	}

	@Override
	public String getHeartTexture() {
		return "blocks/log_oak_top";
	}

	@Override
	public String getDoorLowerTexture() {
		return "blocks/door_wood_lower";
	}

	@Override
	public String getDoorUpperTexture() {
		return "blocks/door_wood_upper";
	}

	@Override
	public String getBarkTexture() {
		return "blocks/log_oak";
	}

	@Override
	public int getMetadata() {
		return 0;
	}
}
