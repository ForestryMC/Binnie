package binnie.core.machines.storage;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

enum Compartment implements IMachineType {
    Compartment(StandardCompartment.PackageCompartment.class),
    CompartmentCopper(StandardCompartment.PackageCompartmentCopper.class),
    CompartmentBronze(StandardCompartment.PackageCompartmentBronze.class),
    CompartmentIron(StandardCompartment.PackageCompartmentIron.class),
    CompartmentGold(StandardCompartment.PackageCompartmentGold.class),
    CompartmentDiamond(StandardCompartment.PackageCompartmentDiamond.class);

    Class<? extends MachinePackage> clss;

    Compartment(Class<? extends MachinePackage> clss) {
        this.clss = clss;
    }

    @Override
    public Class<? extends MachinePackage> getPackageClass() {
        return clss;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public ItemStack get(int i) {
        return new ItemStack(BinnieCore.packageCompartment.getBlock(), i, ordinal());
    }

    public abstract static class PackageCompartment extends MachinePackage {
        private BinnieResource renderTexture;

        protected PackageCompartment(String uid, IBinnieTexture renderTexture) {
            super(uid, false);
            this.renderTexture = renderTexture.getTexture();
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        @Override
        public void renderMachine(
                Machine machine, double x, double y, double z, float partialTick, RenderBlocks renderer) {
            MachineRendererCompartment.instance.renderMachine(machine, 0xffffff, renderTexture, x, y, z, partialTick);
        }
    }
}
