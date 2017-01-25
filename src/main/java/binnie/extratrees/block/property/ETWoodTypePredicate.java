package binnie.extratrees.block.property;

import com.google.common.base.Predicate;

import binnie.extratrees.block.EnumETLog;
import binnie.extratrees.block.PlankType;

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
	public boolean apply(EnumETLog woodType) {
		if(!isLog && !(woodType.getPlank() instanceof PlankType.ExtraTreePlanks)){
			return false;
		}
		return woodType.getMetadata() >= minWoodTypeMeta && woodType.getMetadata() <= maxWoodTypeMeta;
	}
}
