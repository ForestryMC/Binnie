package binnie.extratrees.block.decor;

import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;

public class FenceDescription {
    FenceType fenceType;
    IPlankType plankType;
    IPlankType secondaryPlankType;

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
