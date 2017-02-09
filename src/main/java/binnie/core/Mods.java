package binnie.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Mods {
	public static ModNonnull Forestry = new ModNonnull("forestry");
	public static Mod IC2 = new Mod("IC2");
	public static Mod Botania = new Mod("Botania");

	public static class Mod {
		protected final String id;

		private Mod(final String id) {
			this.id = id;
		}

		@Nullable
		public Item item(final String name) {
			ResourceLocation key = new ResourceLocation(this.id, name);
			if (!ForgeRegistries.ITEMS.containsKey(key)) {
				return null;
			}
			return ForgeRegistries.ITEMS.getValue(key);
		}

		@Nullable
		public Block block(final String name) {
			ResourceLocation key = new ResourceLocation(this.id, name);
			if (!ForgeRegistries.BLOCKS.containsKey(key)) {
				throw new RuntimeException("Block not found: " + key);
			}
			return ForgeRegistries.BLOCKS.getValue(key);
		}

		public ItemStack stack(final String name) {
			Item item = this.item(name);
			if (item == null) {
				return ItemStack.EMPTY;
			}
			return new ItemStack(item);
		}

		public ItemStack stack(final String name, final int amount) {
			Item item = this.item(name);
			if (item == null) {
				return ItemStack.EMPTY;
			}
			return new ItemStack(item, amount);
		}

		public ItemStack stack(final String name, final int amount, final int meta) {
			Item item = this.item(name);
			if (item == null) {
				return ItemStack.EMPTY;
			}
			return new ItemStack(item, amount, meta);
		}

		public boolean active() {
			return Loader.isModLoaded(this.id);
		}
	}

	public static class ModNonnull extends Mod {
		private ModNonnull(final String id) {
			super(id);
		}

		@Override
		@Nonnull
		public Item item(final String name) {
			Item item = super.item(name);
			if (item == null) {
				throw new RuntimeException("Item not found: " + this.id + ":" + name);
			}
			return item;
		}

		@Override
		@Nonnull
		public Block block(final String name) {
			Block block = super.block(name);
			if (block == null) {
				throw new RuntimeException("Block not found: " + this.id + ":" + name);
			}
			return block;
		}

		@Override
		@Nonnull
		public ItemStack stack(final String name) {
			Item item = this.item(name);
			return new ItemStack(item);
		}

		@Override
		@Nonnull
		public ItemStack stack(final String name, final int amount) {
			Item item = this.item(name);
			return new ItemStack(item, amount);
		}

		@Override
		@Nonnull
		public ItemStack stack(final String name, final int amount, final int meta) {
			Item item = this.item(name);
			return new ItemStack(item, amount, meta);
		}
	}
}
