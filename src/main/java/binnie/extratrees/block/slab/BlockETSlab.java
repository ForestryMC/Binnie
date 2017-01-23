package binnie.extratrees.block.slab;

import binnie.Constants;
import binnie.extratrees.block.EnumExtraTreeLog;
import binnie.extratrees.block.property.PropertyETWoodType;
import forestry.arboriculture.blocks.slab.BlockForestrySlab;
import net.minecraft.util.ResourceLocation;
import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockETSlab extends BlockForestrySlab<EnumExtraTreeLog> {
	public static List<BlockETSlab> create(boolean fireproof, final boolean doubleSlab) {
		List<BlockETSlab> blocks = new ArrayList<>();
		PropertyETWoodType[] variants = PropertyETWoodType.create("variant", VARIANTS_PER_BLOCK, false);
		for (int i = 0; i < variants.length; i++) {
			PropertyETWoodType variant = variants[i];
			BlockETSlab block = new BlockETSlab(fireproof, i) {
				@Nonnull
				@Override
				public PropertyETWoodType getVariant() {
					return variant;
				}

				@Override
				public boolean isDouble() {
					return doubleSlab;
				}
			};
			blocks.add(block);
		}
		return blocks;
	}

	private BlockETSlab(boolean fireproof, int blockNumber) {
		super(fireproof, blockNumber);
		String name = "slabs.";
		if(fireproof){
			name+="fireproof.";
		}
		if(isDouble()){
			name+="double.";
		}
		name+=blockNumber;
		setRegistryName(new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, name));
		setUnlocalizedName(name);
	}

	@Nonnull
	@Override
	public EnumExtraTreeLog getWoodType(int meta) {
		meta &= ~8; // unset the top/bottom slab bit
		int variantCount = getVariant().getAllowedValues().size();
		int variantMeta = (meta % variantCount) + getBlockNumber() * VARIANTS_PER_BLOCK;
		return EnumExtraTreeLog.byMetadata(variantMeta);
	}
}
