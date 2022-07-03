package binnie.extratrees.carpentry;

import binnie.core.util.I18N;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.ILayout;
import binnie.extratrees.api.IToolHammer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class DesignBlock {
    protected IDesign design;
    protected IDesignMaterial primaryMaterial;
    protected IDesignMaterial secondaryMaterial;
    protected int rotation;
    protected ForgeDirection facing;
    protected boolean panel;

    DesignBlock(
            IDesignSystem system,
            IDesignMaterial primaryWood,
            IDesignMaterial secondaryWood,
            IDesign design,
            int rotation,
            ForgeDirection dir) {
        this.rotation = 0;
        this.design = design;
        this.rotation = rotation;
        facing = ForgeDirection.UP;
        panel = false;
        primaryMaterial = primaryWood;
        secondaryMaterial = secondaryWood;
        facing = dir;

        if (design == null) {
            this.design = EnumDesign.Blank;
        }
        if (primaryWood == null) {
            primaryMaterial = system.getDefaultMaterial();
        }
        if (secondaryWood == null) {
            secondaryMaterial = system.getDefaultMaterial();
        }
        if (rotation > 3 || rotation < 0) {
            this.rotation = 0;
        }
        if (facing == null || facing == ForgeDirection.UNKNOWN) {
            facing = ForgeDirection.UP;
        }
    }

    @Override
    public String toString() {
        return super.toString() + " { design:" + design + " }, { primary:" + primaryMaterial + " }, { secondary:"
                + secondaryMaterial + " }, { rotation:" + rotation + " }, { facing:" + facing + " }";
    }

    public IDesign getDesign() {
        return design;
    }

    public IDesignMaterial getPrimaryMaterial() {
        return primaryMaterial;
    }

    public IDesignMaterial getSecondaryMaterial() {
        return secondaryMaterial;
    }

    public int getPrimaryColour() {
        return getPrimaryMaterial().getColor();
    }

    public int getSecondaryColour() {
        return getSecondaryMaterial().getColor();
    }

    public ILayout getLayout(ForgeDirection dir) {
        ForgeDirection adjustedDir;
        dir = (adjustedDir = dir.getRotation(ForgeDirection.DOWN));
        switch (getFacing()) {
            case DOWN:
                adjustedDir = adjustedDir.getRotation(ForgeDirection.EAST);
                adjustedDir = adjustedDir.getRotation(ForgeDirection.EAST);
                break;

            case EAST:
                adjustedDir = adjustedDir.getRotation(ForgeDirection.EAST);
                adjustedDir = adjustedDir.getRotation(ForgeDirection.NORTH);
                break;

            case NORTH:
                adjustedDir = adjustedDir.getRotation(ForgeDirection.EAST);
                adjustedDir = adjustedDir.getRotation(ForgeDirection.SOUTH);
                adjustedDir = adjustedDir.getRotation(ForgeDirection.SOUTH);
                break;

            case SOUTH:
                adjustedDir = adjustedDir.getRotation(ForgeDirection.EAST);
                break;

            case WEST:
                adjustedDir = adjustedDir.getRotation(ForgeDirection.EAST);
                adjustedDir = adjustedDir.getRotation(ForgeDirection.SOUTH);
                break;
        }

        for (int i = 0; i < rotation; ++i) {
            adjustedDir = adjustedDir.getRotation(ForgeDirection.DOWN);
        }

        ILayout layout;
        switch (adjustedDir) {
            case EAST:
                layout = getDesign().getEastPattern();
                break;

            case NORTH:
                layout = getDesign().getNorthPattern();
                break;

            case SOUTH:
                layout = getDesign().getSouthPattern();
                break;

            case WEST:
                layout = getDesign().getWestPattern();
                break;

            case DOWN:
                layout = getDesign().getBottomPattern();
                break;

            default:
                layout = getDesign().getTopPattern();
        }

        // TODO remove label
        Label_1107:
        {
            switch (getFacing()) {
                case UP:
                    if (dir == ForgeDirection.DOWN || dir == ForgeDirection.UP) {
                        for (int j = 0; j < rotation; ++j) {
                            layout = layout.rotateRight();
                        }
                        break;
                    }
                    break;

                case DOWN:
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
                        for (int j = 0; j < rotation; ++j) {
                            layout = layout.rotateLeft();
                        }
                        break;
                    }
                    break;

                case EAST:
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
                        for (int j = 0; j < rotation; ++j) {
                            layout = layout.rotateRight();
                        }
                    }
                    if (dir == ForgeDirection.WEST) {
                        for (int j = 0; j < rotation; ++j) {
                            layout = layout.rotateLeft();
                        }
                        break;
                    }
                    break;

                case WEST:
                    switch (dir) {
                        case NORTH:
                            layout = layout.rotateRight();
                            break;

                        case SOUTH:
                        case UP:
                            layout = layout.rotateLeft();
                            break;

                        case DOWN:
                            layout = layout.rotateLeft().flipVertical();
                            break;

                        case EAST:
                            layout = layout.flipHorizontal();
                            for (int j = 0; j < rotation; ++j) {
                                layout = layout.rotateLeft();
                            }
                            break;

                        case WEST:
                            for (int j = 0; j < rotation; ++j) {
                                layout = layout.rotateRight();
                            }
                            break;
                    }
                    break;

                case NORTH:
                    switch (dir) {
                        case WEST:
                            layout = layout.rotateLeft();
                            break Label_1107;

                        case EAST:
                            layout = layout.rotateRight();
                            break Label_1107;

                        case DOWN:
                            layout = layout.flipHorizontal();
                            break Label_1107;

                        case SOUTH:
                            layout = layout.flipHorizontal();
                            for (int j = 0; j < rotation; ++j) {
                                layout = layout.rotateLeft();
                            }
                            break Label_1107;

                        case NORTH:
                            for (int j = 0; j < rotation; ++j) {
                                layout = layout.rotateRight();
                            }
                            break Label_1107;

                        default:
                            break Label_1107;
                    }

                case SOUTH:
                    switch (dir) {
                        case EAST:
                            layout = layout.rotateLeft();
                            break Label_1107;

                        case WEST:
                            layout = layout.rotateRight();
                            break Label_1107;

                        case UP:
                            layout = layout.rotateRight().rotateRight();
                            break Label_1107;

                        case DOWN:
                            layout = layout.flipVertical();
                            break Label_1107;

                        case NORTH:
                            layout = layout.flipHorizontal();
                            for (int j = 0; j < rotation; ++j) {
                                layout = layout.rotateLeft();
                            }
                            break Label_1107;

                        case SOUTH:
                            for (int j = 0; j < rotation; ++j) {
                                layout = layout.rotateRight();
                            }
                            break Label_1107;

                        default:
                            break Label_1107;
                    }
            }
        }
        return layout;
    }

    public IIcon getPrimaryIcon(IDesignSystem system, ForgeDirection dir) {
        ILayout layout = getLayout(dir);
        return (layout == null) ? null : layout.getPrimaryIcon(system);
    }

    public IIcon getSecondaryIcon(IDesignSystem system, ForgeDirection dir) {
        ILayout layout = getLayout(dir);
        return (layout == null) ? null : layout.getSecondaryIcon(system);
    }

    public IIcon getIcon(IDesignSystem system, boolean secondary, ForgeDirection dir) {
        return secondary ? getSecondaryIcon(system, dir) : getPrimaryIcon(system, dir);
    }

    public ForgeDirection getFacing() {
        return facing;
    }

    public void setFacing(ForgeDirection facing) {
        this.facing = facing;
    }

    public int getRotation() {
        return rotation;
    }

    public void rotate(int face, ItemStack hammer, EntityPlayer player, World world, int x, int y, int z) {
        ForgeDirection dir = ForgeDirection.getOrientation(face);
        IToolHammer hammerI = (IToolHammer) hammer.getItem();
        if (player.isSneaking()) {
            if (panel) {
                ForgeDirection newFacing = getFacing();
                do {
                    newFacing = ForgeDirection.getOrientation(newFacing.ordinal() + 1);
                    if (newFacing == ForgeDirection.UNKNOWN) {
                        newFacing = ForgeDirection.DOWN;
                    }
                } while (newFacing != getFacing()
                        && !BlockCarpentryPanel.isValidPanelPlacement(world, x, y, z, newFacing));

                if (newFacing != getFacing()) {
                    hammerI.onHammerUsed(hammer, player);
                }
                setFacing(newFacing);
            } else {
                if (dir != getFacing()) {
                    hammerI.onHammerUsed(hammer, player);
                }
                setFacing(dir);
            }
        } else {
            rotation++;
            hammerI.onHammerUsed(hammer, player);
        }

        if (rotation > 3) {
            rotation = 0;
        } else if (rotation < 0) {
            rotation = 3;
        }
    }

    public int getBlockMetadata(IDesignSystem system) {
        return ModuleCarpentry.getBlockMetadata(system, this);
    }

    public int getItemMetadata(IDesignSystem system) {
        return ModuleCarpentry.getItemMetadata(system, this);
    }

    public void setPanel() {
        panel = true;
    }

    public String getString() {
        String type;
        if (getPrimaryMaterial() != getSecondaryMaterial()) {
            type = I18N.localise(
                    "extratrees.block.tooltip.twoMaterials",
                    getPrimaryMaterial().getName(),
                    getSecondaryMaterial().getName());
        } else {
            type = getPrimaryMaterial().getName();
        }
        return super.toString() + " " + "{" + type + " " + getDesign().getName() + " " + (panel ? "Panel" : "Tile")
                + ", Facing:" + getFacing().toString() + ", Rotation:" + getRotation() + "}";
    }
}
