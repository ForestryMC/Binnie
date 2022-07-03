package binnie.genetics.machine;

import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;

public abstract class PackageGeneticBase extends MachinePackage {
    protected BinnieResource renderTexture;
    protected int color;

    protected PackageGeneticBase(String uid, IBinnieTexture renderTexture, int flashColor, boolean powered) {
        super(uid, powered);
        this.renderTexture = renderTexture.getTexture();
        color = flashColor;
    }

    @Override
    public TileEntity createTileEntity() {
        return new TileEntityMachine(this);
    }

    @Override
    public void renderMachine(Machine machine, double x, double y, double z, float partialTick, RenderBlocks renderer) {
        MachineRendererGenetics.instance.renderMachine(machine, color, renderTexture, x, y, z, partialTick);
    }
}
