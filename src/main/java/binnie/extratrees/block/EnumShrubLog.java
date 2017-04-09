package binnie.extratrees.block;

import java.util.Locale;

import binnie.Constants;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		return Constants.EXTRA_TREES_MOD_ID + ":null";
	}

	@Override
	public String getHeartTexture() {
		return Constants.EXTRA_TREES_MOD_ID + ":null";
	}

	@Override
	public String getDoorLowerTexture() {
		return Constants.EXTRA_TREES_MOD_ID + ":null";
	}

	@Override
	public String getDoorUpperTexture() {
		return Constants.EXTRA_TREES_MOD_ID + ":null";
	}

	@Override
	public String getBarkTexture() {
		return Constants.EXTRA_TREES_MOD_ID + ":null";
	}

	@Override
	public int getMetadata() {
		return 0;
	}
}
