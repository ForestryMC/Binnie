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
		return "tetxures/planks_oak";
	}

	@Override
	public String getHeartTexture() {
		return "tetxures/log_oak_top";
	}

	@Override
	public String getDoorLowerTexture() {
		return "tetxures/door_wood_lower";
	}

	@Override
	public String getDoorUpperTexture() {
		return "tetxures/door_wood_upper";
	}

	@Override
	public String getBarkTexture() {
		return "tetxures/log_oak";
	}

	@Override
	public int getMetadata() {
		return 0;
	}
}
