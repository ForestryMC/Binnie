package binnie.botany.items;

import binnie.core.util.I18N;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public enum EnumTubeInsulate {
	CLAY(0xa1aacc, "clay"),
	COBBLE(0x7b7b7b, "cobblestone"),
	SAND(0xefeab5, "sandstone"),
	HARDENED_CLAY(0x935c43, "hardened_clay"),
	STONE(0x6d6d6d, "stone"),
	SANDSTONE(0xc1b989, "sandstone");

	public static final EnumTubeInsulate[] VALUES = values();

	private final int color;
	private final String uid;

	EnumTubeInsulate(int color, String uid) {
		this.color = color;
		this.uid = uid;
	}

	public static EnumTubeInsulate get(int meta) {
		return VALUES[meta / 128 % VALUES.length];
	}

	public int getColor() {
		return color;
	}

	public String getDisplayName() {
		return I18N.localise("botany.tube.insulate." + uid + ".name");
	}

	public String getUid() {
		return uid;
	}

	public ItemStack getStack() {
		switch (this) {
			case CLAY:
				return new ItemStack(Blocks.CLAY);

			case COBBLE:
				return new ItemStack(Blocks.COBBLESTONE);

			case HARDENED_CLAY:
				return new ItemStack(Blocks.HARDENED_CLAY);

			case SAND:
				return new ItemStack(Blocks.SAND);

			case SANDSTONE:
				return new ItemStack(Blocks.SANDSTONE);

			case STONE:
				return new ItemStack(Blocks.STONE);
		}
		throw new IllegalStateException("Unknown insulated tube type: " + this);
	}
}
