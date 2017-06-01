package binnie.extratrees.carpentry;

import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.ILayout;
import binnie.extratrees.api.IToolHammer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class DesignBlock {
	private static final int[][] ROTATION_MATRIX = {
		{0, 1, 4, 5, 3, 2, 6},
		{0, 1, 5, 4, 2, 3, 6},
		{5, 4, 2, 3, 0, 1, 6},
		{4, 5, 2, 3, 1, 0, 6},
		{2, 3, 1, 0, 4, 5, 6},
		{3, 2, 0, 1, 4, 5, 6},
		{0, 1, 2, 3, 4, 5, 6},
	};
	IDesign design;
	IDesignMaterial primaryMaterial;
	IDesignMaterial secondaryMaterial;
	int rotation;
	EnumFacing facing;
	boolean panel;

	DesignBlock(final IDesignSystem system, @Nullable final IDesignMaterial primaryWood, @Nullable final IDesignMaterial secondaryWood, @Nullable final IDesign design, final int rotation, @Nullable final EnumFacing dir) {
		this.panel = false;
		this.rotation = rotation;

		if (design == null) {
			this.design = EnumDesign.Blank;
		} else {
			this.design = design;
		}
		if (primaryWood == null) {
			this.primaryMaterial = system.getDefaultMaterial();
		} else {
			this.primaryMaterial = primaryWood;
		}
		if (secondaryWood == null) {
			this.secondaryMaterial = system.getDefaultMaterial();
		} else {
			this.secondaryMaterial = secondaryWood;
		}
		if (this.rotation > 3 || this.rotation < 0) {
			this.rotation = 0;
		}
		if (dir == null) {
			this.facing = EnumFacing.UP;
		} else {
			this.facing = dir;
		}
	}

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

	public int getPrimaryColour() {
		return this.getPrimaryMaterial().getColour();
	}

	public int getSecondaryColour() {
		return this.getSecondaryMaterial().getColour();
	}

	EnumFacing getRotation(final EnumFacing dir, final ModuleCarpentry.Axis axis) {
		if (axis == ModuleCarpentry.Axis.Y) {
			switch (dir) {
				case EAST: {
					return EnumFacing.NORTH;
				}
				case NORTH: {
					return EnumFacing.WEST;
				}
				case SOUTH: {
					return EnumFacing.EAST;
				}
				case WEST: {
					return EnumFacing.SOUTH;
				}
				default: {
					return dir;
				}
			}
		} else if (axis == ModuleCarpentry.Axis.X) {
			switch (dir) {
				case EAST: {
					return EnumFacing.UP;
				}
				case UP: {
					return EnumFacing.WEST;
				}
				case WEST: {
					return EnumFacing.DOWN;
				}
				case DOWN: {
					return EnumFacing.EAST;
				}
				default: {
					return dir;
				}
			}
		} else {
			switch (dir) {
				case SOUTH: {
					return EnumFacing.UP;
				}
				case UP: {
					return EnumFacing.NORTH;
				}
				case NORTH: {
					return EnumFacing.DOWN;
				}
				case DOWN: {
					return EnumFacing.SOUTH;
				}
				default: {
					return dir;
				}
			}
		}
	}

	public EnumFacing getRotation(EnumFacing facing, EnumFacing axis) {
		return getNewFacing(ROTATION_MATRIX[axis.ordinal()][facing.ordinal()]);
	}

	public ILayout getLayout(EnumFacing facing) {
		EnumFacing adjustedFacing;
		facing = (adjustedFacing = getRotation(facing, EnumFacing.DOWN));
		switch (this.getFacing()) {
			case DOWN: {
				adjustedFacing = getRotation(adjustedFacing, EnumFacing.EAST);
				adjustedFacing = getRotation(adjustedFacing, EnumFacing.EAST);
				break;
			}
			case EAST: {
				adjustedFacing = getRotation(adjustedFacing, EnumFacing.EAST);
				adjustedFacing = getRotation(adjustedFacing, EnumFacing.NORTH);
				break;
			}
			case NORTH: {
				adjustedFacing = getRotation(adjustedFacing, EnumFacing.EAST);
				adjustedFacing = getRotation(adjustedFacing, EnumFacing.SOUTH);
				adjustedFacing = getRotation(adjustedFacing, EnumFacing.SOUTH);
				break;
			}
			case SOUTH: {
				adjustedFacing = getRotation(adjustedFacing, EnumFacing.EAST);
				break;
			}
			case WEST: {
				adjustedFacing = getRotation(adjustedFacing, EnumFacing.EAST);
				adjustedFacing = getRotation(adjustedFacing, EnumFacing.SOUTH);
				break;
			}
			default: {
				break;
			}
		}
		for (int i = 0; i < this.rotation; ++i) {
			adjustedFacing = getRotation(adjustedFacing, EnumFacing.DOWN);
		}
		ILayout layout = null;
		switch (adjustedFacing) {
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
		LAYOUTS:
		{
			switch (this.getFacing()) {
				case UP: {
					if (facing == EnumFacing.DOWN || facing == EnumFacing.UP) {
						for (int j = 0; j < this.rotation; ++j) {
							layout = layout.rotateRight();
						}
						break;
					}
					break;
				}
				case DOWN: {
					switch (facing) {
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
					if (facing == EnumFacing.DOWN || facing == EnumFacing.UP) {
						for (int j = 0; j < this.rotation; ++j) {
							layout = layout.rotateLeft();
						}
						break;
					}
					break;
				}
				case EAST: {
					switch (facing) {
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
						default: {
							break;
						}
					}
					if (facing == EnumFacing.EAST) {
						for (int j = 0; j < this.rotation; ++j) {
							layout = layout.rotateRight();
						}
					}
					if (facing == EnumFacing.WEST) {
						for (int j = 0; j < this.rotation; ++j) {
							layout = layout.rotateLeft();
						}
						break;
					}
					break;
				}
				case WEST: {
					switch (facing) {
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
					switch (facing) {
						case WEST: {
							layout = layout.rotateLeft();
							break LAYOUTS;
						}
						case EAST: {
							layout = layout.rotateRight();
							break LAYOUTS;
						}
						case DOWN: {
							layout = layout.flipHorizontal();
							break LAYOUTS;
						}
						case SOUTH: {
							layout = layout.flipHorizontal();
							for (int j = 0; j < this.rotation; ++j) {
								layout = layout.rotateLeft();
							}
							break LAYOUTS;
						}
						case NORTH: {
							for (int j = 0; j < this.rotation; ++j) {
								layout = layout.rotateRight();
							}
							break LAYOUTS;
						}
						default: {
							break LAYOUTS;
						}
					}
				}
				case SOUTH: {
					switch (facing) {
						case EAST: {
							layout = layout.rotateLeft();
							break LAYOUTS;
						}
						case WEST: {
							layout = layout.rotateRight();
							break LAYOUTS;
						}
						case UP: {
							layout = layout.rotateRight().rotateRight();
							break LAYOUTS;
						}
						case DOWN: {
							layout = layout.flipVertical();
							break LAYOUTS;
						}
						case NORTH: {
							layout = layout.flipHorizontal();
							for (int j = 0; j < this.rotation; ++j) {
								layout = layout.rotateLeft();
							}
							break LAYOUTS;
						}
						case SOUTH: {
							for (int j = 0; j < this.rotation; ++j) {
								layout = layout.rotateRight();
							}
							break LAYOUTS;
						}
						default: {
							break LAYOUTS;
						}
					}
				}
			}
		}
		return layout;
	}

	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getPrimarySprite(IDesignSystem system, EnumFacing facing) {
		ILayout layout = this.getLayout(facing);
		return layout.getPrimarySprite(system);
	}

	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getSecondarySprite(IDesignSystem system, EnumFacing facing) {
		ILayout layout = this.getLayout(facing);
		return layout.getSecondarySprite(system);
	}

	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getSprite(IDesignSystem system, boolean secondary, EnumFacing facing) {
		return secondary ? this.getSecondarySprite(system, facing) : this.getPrimarySprite(system, facing);
	}

	public EnumFacing getFacing() {
		return this.facing;
	}

	public void setFacing(final EnumFacing facing) {
		this.facing = facing;
	}

	public int getRotation() {
		return this.rotation;
	}

	public void rotate(EnumFacing facing, final ItemStack hammer, final EntityPlayer player, final World world, BlockPos pos) {
		final IToolHammer hammerI = (IToolHammer) hammer.getItem();
		if (player.isSneaking()) {
			if (this.panel) {
				EnumFacing newFacing = this.getFacing();
				do {
					newFacing = getNewFacing(newFacing.ordinal() + 1);
				}
				while (newFacing != this.getFacing() && !BlockCarpentryPanel.isValidPanelPlacement(world, pos, newFacing));
				if (newFacing != this.getFacing()) {
					hammerI.onHammerUsed(hammer, player);
				}
				this.setFacing(newFacing);
			} else {
				if (facing != this.getFacing()) {
					hammerI.onHammerUsed(hammer, player);
				}
				this.setFacing(facing);
			}
		} else {
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

	private EnumFacing getNewFacing(int index) {
		if (index >= 0 && index < EnumFacing.VALUES.length) {
			return EnumFacing.VALUES[index];
		}
		return EnumFacing.DOWN;
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
		} else {
			type = this.getPrimaryMaterial().getName();
		}
		return super.toString() + " " + "{" + type + " " + this.getDesign().getName() + " " + (this.panel ? "Panel" : "Tile") + ", Facing:" + this.getFacing() + ", Rotation:" + this.getRotation() + "}";
	}
}
