package binnie.extratrees.block.log;

import binnie.Constants;
import binnie.extratrees.block.EnumExtraTreeLog;
import binnie.extratrees.block.property.PropertyETWoodType;
import forestry.arboriculture.blocks.log.BlockForestryLog;
import forestry.arboriculture.blocks.property.PropertyWoodType;
import net.minecraft.util.ResourceLocation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public abstract class BlockETLog extends BlockForestryLog<EnumExtraTreeLog> {
	public static List<BlockETLog> create(boolean fireproof) {
		List<BlockETLog> blocks = new ArrayList<>();
		PropertyETWoodType[] variants = PropertyETWoodType.create("variant", VARIANTS_PER_BLOCK, true);
		for (int i = 0; i < variants.length; i++) {
			PropertyETWoodType variant = variants[i];
			BlockETLog block = new BlockETLog(fireproof, i) {
				@Nonnull
				@Override
				public PropertyWoodType<EnumExtraTreeLog> getVariant() {
					return variant;
				}
			};
			blocks.add(block);
		}
		return blocks;
	}

	private BlockETLog(boolean fireproof, int blockNumber) {
		super(fireproof, blockNumber);
		String name = "logs.";
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
