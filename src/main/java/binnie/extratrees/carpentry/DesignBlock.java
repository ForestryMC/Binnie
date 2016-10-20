// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.carpentry;

import binnie.extratrees.api.IToolHammer;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import binnie.extratrees.api.ILayout;
import binnie.extratrees.api.IDesignSystem;
import net.minecraftforge.common.util.ForgeDirection;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesign;

public class DesignBlock
{
	IDesign design;
	IDesignMaterial primaryMaterial;
	IDesignMaterial secondaryMaterial;
	int rotation;
	ForgeDirection facing;
	boolean panel;

	@Override
	public String toString() {
		return super.toString() + " { design:" + this.design + " }, { primary:" + this.primaryMaterial + " }, { secondary:" + this.secondaryMaterial + " }, { rotation:" + this.rotation + " }, { facing:" + this.facing + " }";
	}

	public IDesign getDesign() {
		return this.design;
	}

	public IDesignMaterial getPrimaryMaterial() {
		return this.primaryMaterial;
	}

	public IDesignMaterial getSecondaryMaterial() {
		return this.secondaryMaterial;
	}

	DesignBlock(final IDesignSystem system, final IDesignMaterial primaryWood, final IDesignMaterial secondaryWood, final IDesign design, final int rotation, final ForgeDirection dir) {
		this.rotation = 0;
		this.facing = ForgeDirection.UP;
		this.panel = false;
		this.design = design;
		this.primaryMaterial = primaryWood;
		this.secondaryMaterial = secondaryWood;
		this.rotation = rotation;
		this.facing = dir;
		if (design == null) {
			this.design = EnumDesign.Blank;
		}
		if (primaryWood == null) {
			this.primaryMaterial = system.getDefaultMaterial();
		}
		if (secondaryWood == null) {
			this.secondaryMaterial = system.getDefaultMaterial();
		}
		if (this.rotation > 3 || this.rotation < 0) {
			this.rotation = 0;
		}
		if (this.facing == null || this.facing == ForgeDirection.UNKNOWN) {
			this.facing = ForgeDirection.UP;
		}
	}

	public int getPrimaryColour() {
		return this.getPrimaryMaterial().getColour();
	}

	public int getSecondaryColour() {
		return this.getSecondaryMaterial().getColour();
	}

	ForgeDirection getRotation(final ForgeDirection dir, final ModuleCarpentry.Axis axis) {
		if (axis == ModuleCarpentry.Axis.Y) {
			switch (dir) {
			case EAST: {
				return ForgeDirection.NORTH;
			}
			case NORTH: {
				return ForgeDirection.WEST;
			}
			case SOUTH: {
				return ForgeDirection.EAST;
			}
			case WEST: {
				return ForgeDirection.SOUTH;
			}
			default: {
				return dir;
			}
			}
		}
		else if (axis == ModuleCarpentry.Axis.X) {
			switch (dir) {
			case EAST: {
				return ForgeDirection.UP;
			}
			case UP: {
				return ForgeDirection.WEST;
			}
			case WEST: {
				return ForgeDirection.DOWN;
			}
			case DOWN: {
				return ForgeDirection.EAST;
			}
			default: {
				return dir;
			}
			}
		}
		else {
			switch (dir) {
			case SOUTH: {
				return ForgeDirection.UP;
			}
			case UP: {
				return ForgeDirection.NORTH;
			}
			case NORTH: {
				return ForgeDirection.DOWN;
			}
			case DOWN: {
				return ForgeDirection.SOUTH;
			}
			default: {
				return dir;
			}
			}
		}
	}

	public ILayout getLayout(ForgeDirection dir) {
		ForgeDirection adjustedDir;
		dir = (adjustedDir = dir.getRotation(ForgeDirection.DOWN));
		switch (this.getFacing()) {
		case DOWN: {
			adjustedDir = adjustedDir.getRotation(ForgeDirection.EAST);
			adjustedDir = adjustedDir.getRotation(ForgeDirection.EAST);
			break;
		}
		case EAST: {
			adjustedDir = adjustedDir.getRotation(ForgeDirection.EAST);
			adjustedDir = adjustedDir.getRotation(ForgeDirection.NORTH);
			break;
		}
		case NORTH: {
			adjustedDir = adjustedDir.getRotation(ForgeDirection.EAST);
			adjustedDir = adjustedDir.getRotation(ForgeDirection.SOUTH);
			adjustedDir = adjustedDir.getRotation(ForgeDirection.SOUTH);
			break;
		}
		case SOUTH: {
			adjustedDir = adjustedDir.getRotation(ForgeDirection.EAST);
			break;
		}
		case WEST: {
			adjustedDir = adjustedDir.getRotation(ForgeDirection.EAST);
			adjustedDir = adjustedDir.getRotation(ForgeDirection.SOUTH);
			break;
		}
		}
		for (int i = 0; i < this.rotation; ++i) {
			adjustedDir = adjustedDir.getRotation(ForgeDirection.DOWN);
		}
		ILayout layout = null;
		switch (adjustedDir) {
		case EAST: {
			layout = this.getDesign().getEastPattern();
			break;
		}
		case NORTH: {
			layout = this.getDesign().getNorthPattern();
			break;
		}
		case SOUTH: {
			layout = this.getDesign().getSouthPattern();
			break;
		}
		case WEST: {
			layout = this.getDesign().getWestPattern();
			break;
		}
		case DOWN: {
			layout = this.getDesign().getBottomPattern();
			break;
		}
		default: {
			layout = this.getDesign().getTopPattern();
			break;
		}
		}
		Label_1107: {
			switch (this.getFacing()) {
			case UP: {
				if (dir == ForgeDirection.DOWN || dir == ForgeDirection.UP) {
					for (int j = 0; j < this.rotation; ++j) {
						layout = layout.rotateRight();
					}
					break;
				}
				break;
			}
			case DOWN: {
				switch (dir) {
				case UP:
				case DOWN: {
					layout = layout.flipVertical();
					break;
				}
				case EAST:
				case NORTH:
				case SOUTH:
				case WEST: {
					layout = layout.rotateRight().rotateRight();
					break;
				}
				}
				if (dir == ForgeDirection.DOWN || dir == ForgeDirection.UP) {
					for (int j = 0; j < this.rotation; ++j) {
						layout = layout.rotateLeft();
					}
					break;
				}
				break;
			}
			case EAST: {
				switch (dir) {
				case SOUTH:
				case UP: {
					layout = layout.rotateRight();
					break;
				}
				case NORTH: {
					layout = layout.rotateLeft();
					break;
				}
				case DOWN: {
					layout = layout.rotateLeft().flipHorizontal();
					break;
				}
				case WEST: {
					layout = layout.flipHorizontal();
					break;
				}
				}
				if (dir == ForgeDirection.EAST) {
					for (int j = 0; j < this.rotation; ++j) {
						layout = layout.rotateRight();
					}
				}
				if (dir == ForgeDirection.WEST) {
					for (int j = 0; j < this.rotation; ++j) {
						layout = layout.rotateLeft();
					}
					break;
				}
				break;
			}
			case WEST: {
				switch (dir) {
				case NORTH: {
					layout = layout.rotateRight();
					break;
				}
				case SOUTH:
				case UP: {
					layout = layout.rotateLeft();
					break;
				}
				case DOWN: {
					layout = layout.rotateLeft().flipVertical();
					break;
				}
				case EAST: {
					layout = layout.flipHorizontal();
					for (int j = 0; j < this.rotation; ++j) {
						layout = layout.rotateLeft();
					}
					break;
				}
				case WEST: {
					for (int j = 0; j < this.rotation; ++j) {
						layout = layout.rotateRight();
					}
					break;
				}
				}
				break;
			}
			case NORTH: {
				switch (dir) {
				case WEST: {
					layout = layout.rotateLeft();
					break Label_1107;
				}
				case EAST: {
					layout = layout.rotateRight();
					break Label_1107;
				}
				case DOWN: {
					layout = layout.flipHorizontal();
					break Label_1107;
				}
				case SOUTH: {
					layout = layout.flipHorizontal();
					for (int j = 0; j < this.rotation; ++j) {
						layout = layout.rotateLeft();
					}
					break Label_1107;
				}
				case NORTH: {
					for (int j = 0; j < this.rotation; ++j) {
						layout = layout.rotateRight();
					}
					break Label_1107;
				}
				default: {
					break Label_1107;
				}
				}

			}
			case SOUTH: {
				switch (dir) {
				case EAST: {
					layout = layout.rotateLeft();
					break Label_1107;
				}
				case WEST: {
					layout = layout.rotateRight();
					break Label_1107;
				}
				case UP: {
					layout = layout.rotateRight().rotateRight();
					break Label_1107;
				}
				case DOWN: {
					layout = layout.flipVertical();
					break Label_1107;
				}
				case NORTH: {
					layout = layout.flipHorizontal();
					for (int j = 0; j < this.rotation; ++j) {
						layout = layout.rotateLeft();
					}
					break Label_1107;
				}
				case SOUTH: {
					for (int j = 0; j < this.rotation; ++j) {
						layout = layout.rotateRight();
					}
					break Label_1107;
				}
				default: {
					break Label_1107;
				}
				}

			}
			}
		}
		return layout;
	}

	public IIcon getPrimaryIcon(final IDesignSystem system, final ForgeDirection dir) {
		final ILayout l = this.getLayout(dir);
		return (l == null) ? null : l.getPrimaryIcon(system);
	}

	public IIcon getSecondaryIcon(final IDesignSystem system, final ForgeDirection dir) {
		final ILayout l = this.getLayout(dir);
		return (l == null) ? null : l.getSecondaryIcon(system);
	}

	public IIcon getIcon(final IDesignSystem system, final boolean secondary, final ForgeDirection dir) {
		return secondary ? this.getSecondaryIcon(system, dir) : this.getPrimaryIcon(system, dir);
	}

	public ForgeDirection getFacing() {
		return this.facing;
	}

	public int getRotation() {
		return this.rotation;
	}

	public void rotate(final int face, final ItemStack hammer, final EntityPlayer player, final World world, final int x, final int y, final int z) {
		final ForgeDirection dir = ForgeDirection.getOrientation(face);
		final IToolHammer hammerI = (IToolHammer) hammer.getItem();
		if (player.isSneaking()) {
			if (this.panel) {
				ForgeDirection newFacing = this.getFacing();
				do {
					newFacing = ForgeDirection.getOrientation(newFacing.ordinal() + 1);
					if (newFacing == ForgeDirection.UNKNOWN) {
						newFacing = ForgeDirection.DOWN;
					}
				} while (newFacing != this.getFacing() && !BlockCarpentryPanel.isValidPanelPlacement(world, x, y, z, newFacing));
				if (newFacing != this.getFacing()) {
					hammerI.onHammerUsed(hammer, player);
				}
				this.setFacing(newFacing);
			}
			else {
				if (dir != this.getFacing()) {
					hammerI.onHammerUsed(hammer, player);
				}
				this.setFacing(dir);
			}
		}
		else {
			++this.rotation;
			hammerI.onHammerUsed(hammer, player);
		}
		if (this.rotation > 3) {
			this.rotation = 0;
		}
		if (this.rotation < 0) {
			this.rotation = 3;
		}
	}

	public void setFacing(final ForgeDirection facing) {
		this.facing = facing;
	}

	public int getBlockMetadata(final IDesignSystem system) {
		return ModuleCarpentry.getBlockMetadata(system, this);
	}

	public int getItemMetadata(final IDesignSystem system) {
		return ModuleCarpentry.getItemMetadata(system, this);
	}

	public void setPanel() {
		this.panel = true;
	}

	public String getString() {
		String type = "";
		if (this.getPrimaryMaterial() != this.getSecondaryMaterial()) {
			type = this.getPrimaryMaterial().getName() + " and " + this.getSecondaryMaterial().getName();
		}
		else {
			type = this.getPrimaryMaterial().getName();
		}
		return super.toString() + " " + "{" + type + " " + this.getDesign().getName() + " " + (this.panel ? "Panel" : "Tile") + ", Facing:" + this.getFacing().toString() + ", Rotation:" + this.getRotation() + "}";
	}
}
