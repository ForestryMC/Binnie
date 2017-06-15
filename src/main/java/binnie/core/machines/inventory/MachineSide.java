package binnie.core.machines.inventory;

import java.util.Collection;
import java.util.EnumSet;

import net.minecraft.util.EnumFacing;

public class MachineSide {
	public static EnumSet<EnumFacing> TopAndBottom = EnumSet.of(EnumFacing.UP, EnumFacing.DOWN);
	public static EnumSet<EnumFacing> None = EnumSet.noneOf(EnumFacing.class);
	public static EnumSet<EnumFacing> Top = EnumSet.of(EnumFacing.UP);
	public static EnumSet<EnumFacing> Bottom = EnumSet.of(EnumFacing.DOWN);
	public static EnumSet<EnumFacing> Sides = EnumSet.of(EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST);
	private static EnumSet<EnumFacing> All = EnumSet.of(EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST);

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
}
