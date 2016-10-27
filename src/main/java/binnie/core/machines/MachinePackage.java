package binnie.core.machines;

import binnie.Binnie;
import net.minecraft.tileentity.TileEntity;

public abstract class MachinePackage {
	private String uid;
	private boolean active;
	boolean powered;
	private int metadata;
	private MachineGroup group;

	public String getUID() {
		return this.uid;
	}

	protected MachinePackage(final String uid, final boolean powered) {
		this.active = true;
		this.powered = false;
		this.metadata = -1;
		this.uid = uid;
		this.powered = powered;
	}

	public abstract void createMachine(final Machine p0);

	public abstract TileEntity createTileEntity();

	public abstract void register();

	public final String getDisplayName() {
		return Binnie.Language.localise(this.group.getMod(), "machine." + this.group.getShortUID() + "." + this.getUID());
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

	public abstract void renderMachine(final Machine p0, final double p1, final double p2, final double p3, final float p4);//, final RenderBlocks p5);

	public boolean isActive() {
		return this.active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	public final String getInformation() {
		return Binnie.Language.localise(this.group.getMod(), "machine." + this.group.getShortUID() + "." + this.getUID() + ".info");
	}
}
