package binnie.extratrees.blocks.property;

import binnie.extratrees.wood.EnumETLog;
import com.google.common.base.Predicate;

import javax.annotation.Nullable;

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
				if (woodType.getMetadata() >= minWoodTypeMeta && woodType.getMetadata() <= maxWoodTypeMeta) {
					return true;
				}
			}
		}
		return false;
	}
}
