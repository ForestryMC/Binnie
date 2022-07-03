package binnie.extratrees.block.decor;

import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;

public class FenceDescription {
    protected FenceType fenceType;
    protected IPlankType plankType;
    protected IPlankType secondaryPlankType;

    public FenceDescription(FenceType fenceType, IPlankType plankType, IPlankType secondaryPlankType) {
        this.fenceType = fenceType;
        this.plankType = plankType;
        this.secondaryPlankType = secondaryPlankType;
    }

    public FenceDescription(int meta) {
        fenceType = new FenceType(meta >> 8 & 0xFF);
        plankType = WoodManager.getPlankType(meta & 0xFF);
        secondaryPlankType = WoodManager.getPlankType(meta >> 16 & 0xFF);
    }

    public FenceType getFenceType() {
        return fenceType;
    }

    public IPlankType getPlankType() {
        return plankType;
    }

    public IPlankType getSecondaryPlankType() {
        return secondaryPlankType;
    }
}
