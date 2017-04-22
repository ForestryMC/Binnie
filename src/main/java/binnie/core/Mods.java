package binnie.core;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import cpw.mods.fml.common.versioning.Restriction;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Mods {
	public static Mod Forestry;
	public static Mod IC2;
	public static Mod Botania;
	private static boolean WARN;

	private static Item findItem(String modId, String name) {
		Item stack = GameRegistry.findItem(modId, name);
		if (stack == null && Mods.WARN && modId.equals("Forestry")) {
			throw new RuntimeException("Item not found: " + modId + ":" + name);
		}
		return stack;
	}

	private static ItemStack findItemStack(String modId, String name, int stackSize) {
		ItemStack stack = GameRegistry.findItemStack(modId, name, stackSize);
		if (stack == null && Mods.WARN && modId.equals("Forestry")) {
			throw new RuntimeException("Stack not found: " + modId + ":" + name);
		}
		return stack;
	}

	private static Block findBlock(String modId, String name) {
		Block stack = GameRegistry.findBlock(modId, name);
		if (stack == null && Mods.WARN && modId.equals("Forestry")) {
			throw new RuntimeException("Block not found: " + modId + ":" + name);
		}
		return stack;
	}

	static {
		Mods.Forestry = new Mod("Forestry") {
			@Override
			public boolean dev() {
				String forVersion = Loader.instance().getIndexedModList().get("Forestry").getVersion();
				Restriction rest = new Restriction(new DefaultArtifactVersion("3.6"), true, null, false);
				return rest.containsVersion(new DefaultArtifactVersion(forVersion));
			}
		};
		Mods.IC2 = new Mod("IC2");
		Mods.Botania = new Mod("Botania");
		Mods.WARN = true;
	}

	public static class Mod {
		private String id;

		private Mod(String id) {
			this.id = id;
		}

		public Item item(String name) {
			return findItem(this.id, name);
		}

		public Block block(String name) {
			return findBlock(this.id, name);
		}

		public ItemStack stack(String name, int stackSize) {
			return findItemStack(this.id, name, stackSize);
		}

		public ItemStack stack(String name) {
			return this.stack(name, 1);
		}

		public ItemStack stack(String string, int i, int j) {
			return new ItemStack(this.item(string), i, j);
		}

		public boolean active() {
			return Loader.isModLoaded(this.id);
		}

		public boolean dev() {
			return false;
		}
	}
}
