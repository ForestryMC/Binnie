package binnie.extrabees.apiary.machine;

import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.MachineRendererBlock;
import binnie.core.resource.BinnieResource;
import binnie.extrabees.apiary.ModuleApiary;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import binnie.extrabees.apiary.machine.frame.FrameAlvearyPackage;
import binnie.extrabees.apiary.machine.hatchery.HatcheryAlvearyPackage;
import binnie.extrabees.apiary.machine.lighting.LightingAlvearyPackage;
import binnie.extrabees.apiary.machine.mutator.MutatorAlvearyPackage;
import binnie.extrabees.apiary.machine.rainShield.RainShieldAlvearyPackage;
import binnie.extrabees.apiary.machine.stimulator.StimulatorAlvearyPackage;
import binnie.extrabees.apiary.machine.transmission.TransmissionAlvearyPackage;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public enum AlvearyMachine implements IMachineType {
    Mutator(MutatorAlvearyPackage.class),
    Frame(FrameAlvearyPackage.class),
    RainShield(RainShieldAlvearyPackage.class),
    Lighting(LightingAlvearyPackage.class),
    Stimulator(StimulatorAlvearyPackage.class),
    Hatchery(HatcheryAlvearyPackage.class),
    Transmission(TransmissionAlvearyPackage.class);

    protected Class<? extends MachinePackage> cls;

    AlvearyMachine(Class<? extends MachinePackage> cls) {
        this.cls = cls;
    }

    @Override
    public Class<? extends MachinePackage> getPackageClass() {
        return cls;
    }

    public ItemStack get(int size) {
        return new ItemStack(ModuleApiary.blockComponent, size, ordinal());
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public abstract static class AlvearyPackage extends MachinePackage {
        protected BinnieResource machineTexture;

        public AlvearyPackage(String id, BinnieResource machineTexture, boolean powered) {
            super(id, powered);
            this.machineTexture = machineTexture;
        }

        @Override
        public void createMachine(Machine machine) {
            // ignored
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileExtraBeeAlveary(this);
        }

        @Override
        public void renderMachine(
                Machine machine, double x, double y, double z, float partialTick, RenderBlocks renderer) {
            MachineRendererBlock.instance.renderMachine(machineTexture, x, y, z, partialTick);
        }
    }
}
