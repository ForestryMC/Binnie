package binnie.extratrees.blocks.decor;

import binnie.extratrees.wood.planks.IPlankType;
import binnie.extratrees.wood.WoodManager;

public class FenceDescription {
	private final FenceType fenceType;
	private final IPlankType plankType;
	private final IPlankType secondaryPlankType;

	public FenceDescription(final FenceType fenceType, final IPlankType plankType, final IPlankType secondaryPlankType) {
		this.fenceType = fenceType;
		this.plankType = plankType;
		this.secondaryPlankType = secondaryPlankType;
	}

	public FenceDescription(final int meta) {
		this.fenceType = new FenceType(meta >> 8 & 0xFF);
		this.plankType = WoodManager.getPlankType(meta & 0xFF);
		this.secondaryPlankType = WoodManager.getPlankType(meta >> 16 & 0xFF);
	}

	public FenceType getFenceType() {
		return this.fenceType;
	}

	public IPlankType getPlankType() {
		return this.plankType;
	}

	public IPlankType getSecondaryPlankType() {
		return this.secondaryPlankType;
	}
}
