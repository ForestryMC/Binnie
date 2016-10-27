package binnie.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;
import net.minecraftforge.fml.common.versioning.Restriction;

import java.util.Objects;

public class Mods {
	public static Mod Forestry = new Mod("forestry") {
		@Override
		public boolean dev() {
			final String forVersion = Loader.instance().getIndexedModList().get("forestry").getVersion();
			final Restriction rest = new Restriction(new DefaultArtifactVersion("3.6"), true, null, false);
			return rest.containsVersion(new DefaultArtifactVersion(forVersion));
		}
	};
	public static Mod IC2 = new Mod("IC2");
	public static Mod Botania = new Mod("Botania");
	private static boolean WARN = true;

	private static Item findItem(final String modId, final String name) {
		final Item stack = Item.REGISTRY.getObject(new ResourceLocation(modId, name));
		if (stack == null && Mods.WARN && Objects.equals(modId, "forestry")) {
			//throw new RuntimeException("Item not found: " + modId + ":" + name);
		}

		return stack;
	}

	private static ItemStack findItemStack(final String modId, final String name, final int stackSize) {
		final ItemStack stack = GameRegistry.makeItemStack(modId + ":" + name, 0, stackSize, null);
		if (stack == null && Mods.WARN && Objects.equals(modId, "forestry")) {
			//throw new RuntimeException("Stack not found: " + modId + ":" + name);
		}
		return stack;
	}

	private static Block findBlock(final String modId, final String name) {
		final Block stack = Block.REGISTRY.getObject(new ResourceLocation(modId + ":" + name));
		if (stack == null && Mods.WARN && Objects.equals(modId, "forestry")) {
			//throw new RuntimeException("Block not found: " + modId + ":" + name);
		}
		return stack;
	}

	public static class Mod {
		private String id;

		private Mod(final String id) {
			this.id = id;
		}

		public Item item(final String name) {
			return findItem(this.id, name);
		}

		public Block block(final String name) {
			return findBlock(this.id, name);
		}

		public ItemStack stack(final String name, final int stackSize) {
			return findItemStack(this.id, name, stackSize);
		}

		public ItemStack stack(final String name) {
			return this.stack(name, 1);
		}

		public ItemStack stack(final String string, final int amount, final int meta) {
			return new ItemStack(this.item(string), amount, meta);
		}

		public boolean active() {
			return Loader.isModLoaded(this.id);
		}

		public boolean dev() {
			return false;
		}
	}
}
