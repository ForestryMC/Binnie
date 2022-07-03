package binnie.core.machines.inventory;

import binnie.core.util.I18N;
import java.util.Collection;
import java.util.EnumSet;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineSide {
    public static final EnumSet<ForgeDirection> TOP_AND_BOTTOM = EnumSet.of(ForgeDirection.UP, ForgeDirection.DOWN);
    public static final EnumSet<ForgeDirection> NONE = EnumSet.noneOf(ForgeDirection.class);
    public static final EnumSet<ForgeDirection> SIDES =
            EnumSet.of(ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST);

    private static final EnumSet<ForgeDirection> ALL = EnumSet.of(
            ForgeDirection.UP,
            ForgeDirection.DOWN,
            ForgeDirection.NORTH,
            ForgeDirection.SOUTH,
            ForgeDirection.EAST,
            ForgeDirection.WEST);

    public static String asString(Collection<ForgeDirection> sides) {
        if (sides.containsAll(MachineSide.ALL)) {
            return I18N.localise("binniecore.gui.side.any");
        }
        if (sides.isEmpty()) {
            return I18N.localise("binniecore.gui.side.none");
        }

        String text = "";
        if (sides.contains(ForgeDirection.UP)) {
            if (sides.size() > 0) {
                text += ", ";
            }
            text += I18N.localise("binniecore.gui.side.up");
        }

        if (sides.contains(ForgeDirection.DOWN)) {
            if (sides.size() > 0) {
                text += ", ";
            }
            text += I18N.localise("binniecore.gui.side.down");
        }

        if (sides.containsAll(MachineSide.SIDES)) {
            if (sides.size() > 0) {
                text += ", ";
            }
            text += I18N.localise("binniecore.gui.side.sides");
        } else {
            if (sides.contains(ForgeDirection.NORTH)) {
                if (sides.size() > 0) {
                    text += ", ";
                }
                text += I18N.localise("binniecore.gui.side.north");
            }

            if (sides.contains(ForgeDirection.EAST)) {
                if (sides.size() > 0) {
                    text += ", ";
                }
                text += I18N.localise("binniecore.gui.side.east");
            }

            if (sides.contains(ForgeDirection.SOUTH)) {
                if (sides.size() > 0) {
                    text += ", ";
                }
                text += I18N.localise("binniecore.gui.side.south");
            }

            if (sides.contains(ForgeDirection.WEST)) {
                if (sides.size() > 0) {
                    text += ", ";
                }
                text += I18N.localise("binniecore.gui.side.west");
            }
        }
        return text;
    }
}
