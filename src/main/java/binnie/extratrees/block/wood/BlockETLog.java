package binnie.extratrees.block.wood;

import binnie.Constants;
import binnie.extratrees.block.EnumETLog;
import binnie.extratrees.block.property.PropertyETWoodType;
import forestry.arboriculture.blocks.BlockForestryLog;
import forestry.arboriculture.blocks.PropertyWoodType;
import net.minecraft.util.ResourceLocation;
import java.util.ArrayList;
import java.util.List;

public abstract class BlockETLog extends BlockForestryLog<EnumETLog> {
	public static List<BlockETLog> create(boolean fireproof) {
		List<BlockETLog> blocks = new ArrayList<>();
		PropertyETWoodType[] variants = PropertyETWoodType.create("variant", VARIANTS_PER_BLOCK, true);
		for (int i = 0; i < variants.length; i++) {
			PropertyETWoodType variant = variants[i];
			BlockETLog block = new BlockETLog(fireproof, i) {
				@Override
				public PropertyWoodType<EnumETLog> getVariant() {
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

	@Override
	public EnumETLog getWoodType(int meta) {
		int variantCount = getVariant().getAllowedValues().size();
		int variantMeta = (meta % variantCount) + getBlockNumber() * VARIANTS_PER_BLOCK;
		return EnumETLog.byMetadata(variantMeta);
	}
	
}
