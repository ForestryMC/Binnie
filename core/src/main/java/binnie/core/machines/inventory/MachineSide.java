package binnie.core.machines.inventory;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import net.minecraft.util.EnumFacing;

import binnie.core.ModId;
import binnie.core.util.I18N;

public class MachineSide {
	public static final EnumSet<EnumFacing> TOP_AND_BOTTOM = EnumSet.of(EnumFacing.UP, EnumFacing.DOWN);
	public static final EnumSet<EnumFacing> NONE = EnumSet.noneOf(EnumFacing.class);
	public static final EnumSet<EnumFacing> TOP = EnumSet.of(EnumFacing.UP);
	public static final EnumSet<EnumFacing> BOTTOM = EnumSet.of(EnumFacing.DOWN);
	public static final EnumSet<EnumFacing> SIDES = EnumSet.of(EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST);
	private static final List<EnumFacing> ALL = ImmutableList.of(EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST, EnumFacing.WEST);

	public static String asString(final Collection<EnumFacing> sides) {
		if (sides.containsAll(MachineSide.ALL)) {
			return I18N.localise(ModId.CORE, "side.any");
		}
		if (sides.isEmpty()) {
			return I18N.localise(ModId.CORE, "side.none");
		}
		if (sides.containsAll(SIDES) && SIDES.containsAll(sides)) {
			return I18N.localise(ModId.CORE, "side.sides");
		}

		StringBuilder text = new StringBuilder();
		boolean firstSide = true;
		List<EnumFacing> sortedSides = new ArrayList<>(sides);
		sortedSides.sort(Comparator.comparing(ALL::indexOf));
		for (EnumFacing side : sortedSides) {
			String localized = I18N.localise(ModId.CORE, "side." + side.getName());
			if (firstSide) {
				firstSide = false;
			} else {
				text.append(", ");
			}
			text.append(localized);
		}
		return text.toString();
	}
}
