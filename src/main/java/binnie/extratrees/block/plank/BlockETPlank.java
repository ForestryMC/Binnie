package binnie.extratrees.block.plank;

import binnie.Constants;
import binnie.extratrees.block.EnumExtraTreeLog;
import binnie.extratrees.block.property.PropertyETWoodType;
import forestry.arboriculture.blocks.planks.BlockForestryPlanks;
import net.minecraft.util.ResourceLocation;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

public abstract class BlockETPlank extends BlockForestryPlanks<EnumExtraTreeLog> {
	public static final String BLOCK_NAME = "plank";
	
	public static List<BlockETPlank> create(boolean fireproof) {
		List<BlockETPlank> blocks = new ArrayList<>();
		PropertyETWoodType[] variants = PropertyETWoodType.create("variant", VARIANTS_PER_BLOCK, false);
		for (int i = 0; i < variants.length; i++) {
			PropertyETWoodType variant = variants[i];
			BlockETPlank block = new BlockETPlank(fireproof, i) {
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

	private BlockETPlank(boolean fireproof, int blockNumber) {
		super(fireproof, blockNumber);
		String name = "planks.";
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
