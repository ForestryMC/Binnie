package binnie.extratrees.blocks.property;

import com.google.common.base.Predicate;

import javax.annotation.Nullable;

import binnie.extratrees.wood.EnumETLog;

public class ETWoodTypePredicate implements Predicate<EnumETLog> {
	private final int minWoodTypeMeta;
	private final int maxWoodTypeMeta;
	private final boolean isLog;

	public ETWoodTypePredicate(int blockNumber, int variantsPerBlock, boolean isLog) {
		this.minWoodTypeMeta = blockNumber * variantsPerBlock;
		this.maxWoodTypeMeta = minWoodTypeMeta + variantsPerBlock - 1;
		this.isLog = isLog;
	}

	@Override
	public boolean apply(@Nullable EnumETLog woodType) {
		if (woodType != null) {
			if (isLog || woodType.hasProducts()) {
				return woodType.getMetadata() >= minWoodTypeMeta && woodType.getMetadata() <= maxWoodTypeMeta;
			}
		}
		return false;
	}
}
