package binnie.extratrees.machines;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IInteraction;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.machines.designer.GlassworkerPackage;
import binnie.extratrees.machines.designer.PanelworkerPackage;
import binnie.extratrees.machines.designer.TileworkerPackage;
import binnie.extratrees.machines.designer.WoodworkerPackage;
import binnie.extratrees.machines.lumbermill.LumbermillPackage;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public enum ExtraTreeMachine implements IMachineType {
    Lumbermill(LumbermillPackage.class),
    Woodworker(WoodworkerPackage.class),
    Panelworker(PanelworkerPackage.class),
    Glassworker(GlassworkerPackage.class),
    Tileworker(TileworkerPackage.class);

    protected Class<? extends MachinePackage> cls;

    ExtraTreeMachine(Class<? extends MachinePackage> cls) {
        this.cls = cls;
    }

    @Override
    public Class<? extends MachinePackage> getPackageClass() {
        return cls;
    }

    @Override
    public boolean isActive() {
        if (this == ExtraTreeMachine.Tileworker) {
            return BinnieCore.isBotanyActive();
        }
        return true;
    }

    public ItemStack get(int i) {
        return new ItemStack(ExtraTrees.blockMachine, i, ordinal());
    }

    public static class ComponentExtraTreeGUI extends MachineComponent implements IInteraction.RightClick {
        protected ExtraTreesGUID id;

        public ComponentExtraTreeGUI(Machine machine, ExtraTreesGUID id) {
            super(machine);
            this.id = id;
        }

        @Override
        public void onRightClick(World world, EntityPlayer player, int x, int y, int z) {
            ExtraTrees.proxy.openGui(id, player, x, y, z);
        }
    }

    public abstract static class PackageExtraTreeMachine extends MachinePackage {
        protected BinnieResource textureName;

        protected PackageExtraTreeMachine(String uid, String textureName, boolean powered) {
            super(uid, powered);
            this.textureName = Binnie.Resource.getFile(ExtraTrees.instance, ResourceType.Tile, textureName);
        }

        protected PackageExtraTreeMachine(String uid, BinnieResource textureName, boolean powered) {
            super(uid, powered);
            this.textureName = textureName;
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        @Override
        public void renderMachine(
                Machine machine, double x, double y, double z, float partialTick, RenderBlocks renderer) {
            MachineRendererForestry.renderMachine(textureName.getShortPath(), x, y, z, partialTick);
        }
    }
}
