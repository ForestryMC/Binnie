package binnie.extratrees.block;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import binnie.extratrees.genetics.ETTreeDefinition;

public class TreeTypePredicate implements Predicate<ETTreeDefinition> {
	private final int minMeta;
	private final int maxMeta;

	public TreeTypePredicate(int blockNumber, int variantsPerBlock) {
		this.minMeta = blockNumber * variantsPerBlock;
		this.maxMeta = minMeta + variantsPerBlock - 1;
	}

	@Override
	public boolean apply(@Nullable ETTreeDefinition treeDefinition) {
		return treeDefinition.getMetadata() >= minMeta && treeDefinition.getMetadata() <= maxMeta;
	}
}
