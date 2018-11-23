package binnie.core.machines;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import binnie.core.Constants;
import binnie.core.util.I18N;

public abstract class MachinePackage {
	private final String uid;
	private boolean active;
	private int metadata;
	private MachineGroup group;

	protected MachinePackage(String uid) {
		this.active = true;
		this.metadata = -1;
		this.uid = uid;
	}

	public String getUID() {
		return this.uid;
	}

	public abstract void createMachine(Machine p0);

	public abstract TileEntity createTileEntity();

	public final String getDisplayName() {
		return I18N.localise(this.group.getMod().getModId() + '.' + this.group.getShortUID() + '.' + this.getUID());
	}

	public final Integer getMetadata() {
		return this.metadata;
	}

	public void assignMetadata(int meta) {
		this.metadata = meta;
	}

	public MachineGroup getGroup() {
		return this.group;
	}

	public void setGroup(MachineGroup group) {
		this.group = group;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public final String getInformation() {
		return I18N.localise(this.group.getMod().getModId() + '.' + this.group.getShortUID() + '.' + this.getUID() + ".info");
	}

	protected ResourceLocation getSlotRL(String slotName) {
		return new ResourceLocation(Constants.CORE_MOD_ID, "gui.slots." + slotName);
	}
}
