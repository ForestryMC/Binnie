package binnie.botany.flower;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import binnie.core.util.I18N;

enum EnumTubeInsulate {
	Clay(0xa1aacc, "clay"),
	Cobble(0x7b7b7b, "cobblestone"),
	Sand(0xefeab5, "sandstone"),
	HardenedClay(0x935c43, "hardened_clay"),
	Stone(0x6d6d6d, "stone"),
	Sandstone(0xc1b989, "sandstone");

	public static final EnumTubeInsulate[] VALUES = values();
	
	int color;
	String name;

	EnumTubeInsulate(final int color, final String name) {
		this.color = color;
		this.name = name;
	}

	public static EnumTubeInsulate get(int meta) {
		return VALUES[meta / 128 % VALUES.length];
	}

	public int getColor() {
		return this.color;
	}

	public String getDisplayName() {
		return I18N.localise("botany.tube.insulate." + name + ".name");
	}

	public ItemStack getStack() {
		switch (this) {
			case Clay: {
				return new ItemStack(Blocks.CLAY);
			}
			case Cobble: {
				return new ItemStack(Blocks.COBBLESTONE);
			}
			case HardenedClay: {
				return new ItemStack(Blocks.HARDENED_CLAY);
			}
			case Sand: {
				return new ItemStack(Blocks.SAND);
			}
			case Sandstone: {
				return new ItemStack(Blocks.SANDSTONE);
			}
			case Stone: {
				return new ItemStack(Blocks.STONE);
			}
			default: {
				throw new IllegalStateException("Unknown insulated tube type: " + this);
			}
		}
	}
}
