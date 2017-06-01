package binnie.botany.flower;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

enum EnumTubeInsulate {
	Clay(0xa1aacc, "Clay"),
	Cobble(0x7b7b7b, "Cobblestone"),
	Sand(0xefeab5, "Sand"),
	HardenedClay(0x935c43, "Hardened Clay"),
	Stone(0x6d6d6d, "Smooth Stone"),
	Sandstone(0xc1b989, "Sandstone");

	int color;
	String name;

	EnumTubeInsulate(final int color, final String name) {
		this.color = color;
		this.name = name;
	}

	public static EnumTubeInsulate get(final int i) {
		return values()[i / 128 % values().length];
	}

	public int getColor() {
		return this.color;
	}

	public String getName() {
		return this.name;
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
