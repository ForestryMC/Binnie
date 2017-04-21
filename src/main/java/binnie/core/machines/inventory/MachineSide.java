package binnie.core.machines.inventory;

import net.minecraftforge.common.util.ForgeDirection;

import java.util.Collection;
import java.util.EnumSet;

public class MachineSide {
	private static EnumSet<ForgeDirection> All = EnumSet.of(ForgeDirection.UP, ForgeDirection.DOWN, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST);
	public static EnumSet<ForgeDirection> TopAndBottom = EnumSet.of(ForgeDirection.UP, ForgeDirection.DOWN);
	public static EnumSet<ForgeDirection> None = EnumSet.noneOf(ForgeDirection.class);
	public static EnumSet<ForgeDirection> Top = EnumSet.of(ForgeDirection.UP);
	public static EnumSet<ForgeDirection> Bottom = EnumSet.of(ForgeDirection.DOWN);
	public static EnumSet<ForgeDirection> Sides = EnumSet.of(ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST);

	public static String asString(Collection<ForgeDirection> sides) {
		if (sides.containsAll(MachineSide.All)) {
			return "Any";
		}
		if (sides.isEmpty()) {
			return "None";
		}

		String text = "";
		if (sides.contains(ForgeDirection.UP)) {
			if (sides.size() > 0) {
				text += ", ";
			}
			text += "Up";
		}

		if (sides.contains(ForgeDirection.DOWN)) {
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
			if (sides.contains(ForgeDirection.NORTH)) {
				if (sides.size() > 0) {
					text += ", ";
				}
				text += "North";
			}

			if (sides.contains(ForgeDirection.EAST)) {
				if (sides.size() > 0) {
					text += ", ";
				}
				text += "East";
			}

			if (sides.contains(ForgeDirection.SOUTH)) {
				if (sides.size() > 0) {
					text += ", ";
				}
				text += "South";
			}

			if (sides.contains(ForgeDirection.WEST)) {
				if (sides.size() > 0) {
					text += ", ";
				}
				text += "West";
			}
		}
		return text;
	}
}
