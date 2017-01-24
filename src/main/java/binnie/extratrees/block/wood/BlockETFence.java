package binnie.extratrees.block.wood;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import binnie.Constants;
import binnie.extratrees.block.EnumExtraTreeLog;
import binnie.extratrees.block.property.PropertyETWoodType;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IStateMapperRegister;
import forestry.arboriculture.IWoodTyped;
import forestry.arboriculture.blocks.fence.BlockForestryFence;
import net.minecraft.util.ResourceLocation;

public abstract class BlockETFence extends BlockForestryFence<EnumExtraTreeLog> implements IWoodTyped, IItemModelRegister, IStateMapperRegister {
	public static List<BlockETFence> create(boolean fireproof) {
		List<BlockETFence> blocks = new ArrayList<>();

		PropertyETWoodType[] variants = PropertyETWoodType.create("variant", VARIANTS_PER_BLOCK);
		for (int i = 0; i < variants.length; i++) {
			PropertyETWoodType variant = variants[i];
			BlockETFence block = new BlockETFence(fireproof, i) {
				@Nonnull
				@Override
				public PropertyETWoodType getVariant() {
					return variant;
				}
			};
			blocks.add(block);
		}
		return blocks;
	}

	private BlockETFence(boolean fireproof, int blockNumber) {
		super(fireproof, blockNumber);
		String name = "fences.";
		if(fireproof){
			name+="fireproof.";
		}
		name+=blockNumber;
		setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, name));
		setUnlocalizedName(name);
	}

	@Nonnull
	@Override
	public EnumExtraTreeLog getWoodType(int meta) {
		int variantCount = getVariant().getAllowedValues().size();
		int variantMeta = (meta % variantCount) + getBlockNumber() * VARIANTS_PER_BLOCK;
		return EnumExtraTreeLog.byMetadata(variantMeta);
	}
}
