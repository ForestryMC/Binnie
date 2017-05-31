package binnie.extratrees.block.wood;

import binnie.Constants;
import binnie.extratrees.block.EnumETLog;
import binnie.extratrees.block.property.PropertyETWoodType;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IStateMapperRegister;
import forestry.arboriculture.IWoodTyped;
import forestry.arboriculture.blocks.BlockForestryFence;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockETFence extends BlockForestryFence<EnumETLog> implements IWoodTyped, IItemModelRegister, IStateMapperRegister {
	private BlockETFence(boolean fireproof, int blockNumber) {
		super(fireproof, blockNumber);
		String name = "fences.";
		if (fireproof) {
			name += "fireproof.";
		}
		name += blockNumber;
		setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, name));
		setUnlocalizedName(name);
	}

	public static List<BlockETFence> create(boolean fireproof) {
		List<BlockETFence> blocks = new ArrayList<>();

		PropertyETWoodType[] variants = PropertyETWoodType.create("variant", VARIANTS_PER_BLOCK);
		for (int i = 0; i < variants.length; i++) {
			PropertyETWoodType variant = variants[i];
			BlockETFence block = new BlockETFence(fireproof, i) {
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
