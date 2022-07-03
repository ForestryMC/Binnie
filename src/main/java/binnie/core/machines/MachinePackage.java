package binnie.core.machines;

import binnie.core.util.I18N;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;

public abstract class MachinePackage {
    private String uid;
    private boolean active;
    boolean powered;
    private int metadata;
    private MachineGroup group;

    public String getUID() {
        return uid;
    }

    protected MachinePackage(String uid, boolean powered) {
        active = true;
        metadata = -1;
        this.uid = uid;
        this.powered = powered;
    }

    public abstract void createMachine(Machine machine);

    public abstract TileEntity createTileEntity();

    public String getDisplayName() {
        return I18N.localise(group.getMod().getModID() + ".machine." + group.getShortUID() + "." + getUID());
    }

    public Integer getMetadata() {
        return metadata;
    }

    public void assignMetadata(int meta) {
        metadata = meta;
    }

    public MachineGroup getGroup() {
        return group;
    }

    public void setGroup(MachineGroup group) {
        this.group = group;
    }

    public abstract void renderMachine(
            Machine machine, double x, double y, double z, float partialTick, RenderBlocks renderer);

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getInformation() {
        return I18N.localise(group.getMod().getModID() + ".machine." + group.getShortUID() + "." + getUID() + ".info");
    }
}
