package binnie.core.machines;

import net.minecraft.tileentity.TileEntity;

import binnie.core.util.I18N;

public abstract class MachinePackage {
	boolean powered;
	private String uid;
	private boolean active;
	private int metadata;
	private MachineGroup group;

	protected MachinePackage(final String uid, final boolean powered) {
		this.active = true;
		this.metadata = -1;
		this.uid = uid;
		this.powered = powered;
	}

	public String getUID() {
		return this.uid;
	}

	public abstract void createMachine(final Machine p0);

	public abstract TileEntity createTileEntity();

	public abstract void register();

	public final String getDisplayName() {
		return I18N.localise(this.group.getMod().getModId() + '.' + "machine." + this.group.getShortUID() + "." + this.getUID());
	}

	public final Integer getMetadata() {
		return this.metadata;
	}

	public void assignMetadata(final int meta) {
		this.metadata = meta;
	}

	public MachineGroup getGroup() {
		return this.group;
	}

	public void setGroup(final MachineGroup group) {
		this.group = group;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	public final String getInformation() {
		return I18N.localise(this.group.getMod().getModId() + '.' + "machine." + this.group.getShortUID() + "." + this.getUID() + ".info");
	}
}
