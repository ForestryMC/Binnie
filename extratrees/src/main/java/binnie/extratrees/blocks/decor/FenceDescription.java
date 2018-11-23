package binnie.extratrees.blocks.decor;

import binnie.extratrees.wood.WoodManager;
import binnie.extratrees.wood.planks.IPlankType;

public class FenceDescription {
	private final FenceType fenceType;
	private final IPlankType plankType;
	private final IPlankType secondaryPlankType;

	public FenceDescription(FenceType fenceType, IPlankType plankType, IPlankType secondaryPlankType) {
		this.fenceType = fenceType;
		this.plankType = plankType;
		this.secondaryPlankType = secondaryPlankType;
	}

	public FenceDescription(int meta) {
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
