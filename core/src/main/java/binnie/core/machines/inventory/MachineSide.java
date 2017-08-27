package binnie.core.machines.inventory;

import java.util.Collection;
import java.util.EnumSet;

import net.minecraft.util.EnumFacing;

public class MachineSide {
	public static final EnumSet<EnumFacing> TOP_AND_BOTTOM = EnumSet.of(EnumFacing.UP, EnumFacing.DOWN);
	public static final EnumSet<EnumFacing> NONE = EnumSet.noneOf(EnumFacing.class);
	public static final EnumSet<EnumFacing> TOP = EnumSet.of(EnumFacing.UP);
	public static final EnumSet<EnumFacing> BOTTOM = EnumSet.of(EnumFacing.DOWN);
	public static final EnumSet<EnumFacing> SIDES = EnumSet.of(EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST);
	private static final EnumSet<EnumFacing> ALL = EnumSet.of(EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST);

	public static String asString(final Collection<EnumFacing> sides) {
		if (sides.containsAll(MachineSide.ALL)) {
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
		if (sides.containsAll(MachineSide.SIDES)) {
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
}
