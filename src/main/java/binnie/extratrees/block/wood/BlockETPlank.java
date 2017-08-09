package binnie.extratrees.block.wood;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;

import forestry.arboriculture.blocks.BlockForestryPlanks;

import binnie.Constants;
import binnie.extratrees.block.EnumETLog;
import binnie.extratrees.block.property.PropertyETWoodType;

public abstract class BlockETPlank extends BlockForestryPlanks<EnumETLog> {
	private BlockETPlank(boolean fireproof, int blockNumber) {
		super(fireproof, blockNumber);
		String name = "planks.";
		if (fireproof) {
			name += "fireproof.";
		}
		name += blockNumber;
		setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, name));
		setUnlocalizedName(name);
	}

	public static List<BlockETPlank> create(boolean fireproof) {
		List<BlockETPlank> blocks = new ArrayList<>();
		PropertyETWoodType[] variants = PropertyETWoodType.create("variant", VARIANTS_PER_BLOCK, false);
		for (int i = 0; i < variants.length; i++) {
			PropertyETWoodType variant = variants[i];
			BlockETPlank block = new BlockETPlank(fireproof, i) {
				@Override
				public PropertyETWoodType getVariant() {
					return variant;
				}
			};
			blocks.add(block);
		}
		return blocks;
	}

	@Override
	public EnumETLog getWoodType(int meta) {
		int variantCount = getVariant().getAllowedValues().size();
		int variantMeta = (meta % variantCount) + getBlockNumber() * VARIANTS_PER_BLOCK;
		return EnumETLog.byMetadata(variantMeta);
	}
}
