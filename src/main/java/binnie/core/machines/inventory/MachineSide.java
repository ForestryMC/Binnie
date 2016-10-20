package binnie.core.machines.inventory;

import net.minecraft.util.EnumFacing;

import java.util.Collection;
import java.util.EnumSet;

public class MachineSide {
    private static EnumSet<EnumFacing> All;
    public static EnumSet<EnumFacing> TopAndBottom;
    public static EnumSet<EnumFacing> None;
    public static EnumSet<EnumFacing> Top;
    public static EnumSet<EnumFacing> Bottom;
    public static EnumSet<EnumFacing> Sides;

    public static String asString(final Collection<EnumFacing> sides) {
        if (sides.containsAll(MachineSide.All)) {
            return "Any";
        }
        if (sides.isEmpty()) {
            return "None";
        }
        String text = "";
        if (sides.contains(EnumFacing.UP)) {
            if (sides.size() > 0) {
                text += ", ";
            }
            text += "Up";
        }
        if (sides.contains(EnumFacing.DOWN)) {
            if (sides.size() > 0) {
                text += ", ";
            }
            text += "Down";
        }
        if (sides.containsAll(MachineSide.Sides)) {
            if (sides.size() > 0) {
                text += ", ";
            }
            text += "Sides";
        } else {
            if (sides.contains(EnumFacing.NORTH)) {
                if (sides.size() > 0) {
                    text += ", ";
                }
                text += "North";
            }
            if (sides.contains(EnumFacing.EAST)) {
                if (sides.size() > 0) {
                    text += ", ";
                }
                text += "East";
            }
            if (sides.contains(EnumFacing.SOUTH)) {
                if (sides.size() > 0) {
                    text += ", ";
                }
                text += "South";
            }
            if (sides.contains(EnumFacing.WEST)) {
                if (sides.size() > 0) {
                    text += ", ";
                }
                text += "West";
            }
        }
        return text;
    }

    static {
        MachineSide.All = EnumSet.of(EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST);
        MachineSide.TopAndBottom = EnumSet.of(EnumFacing.UP, EnumFacing.DOWN);
        MachineSide.None = EnumSet.noneOf(EnumFacing.class);
        MachineSide.Top = EnumSet.of(EnumFacing.UP);
        MachineSide.Bottom = EnumSet.of(EnumFacing.DOWN);
        MachineSide.Sides = EnumSet.of(EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST);
    }
}
