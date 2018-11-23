package binnie.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.oredict.OreDictionary;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class Mods {
	public static final ModNonnull Forestry = new ModNonnull("forestry");
	public static final Mod IC2 = new Mod("ic2");
	public static final Mod Botania = new Mod("botania");

	public static class Mod {
		protected final String id;

		private Mod(String id) {
			this.id = id;
		}

		@Nullable
		public Item item(String name) {
			ResourceLocation key = new ResourceLocation(this.id, name);
			if (!ForgeRegistries.ITEMS.containsKey(key)) {
				return null;
			}
			return ForgeRegistries.ITEMS.getValue(key);
		}

		@Nullable
		public Block block(String name) {
			ResourceLocation key = new ResourceLocation(this.id, name);
			if (!ForgeRegistries.BLOCKS.containsKey(key)) {
				throw new RuntimeException("Block not found: " + key);
			}
			return ForgeRegistries.BLOCKS.getValue(key);
		}

		public ItemStack stackWildcard(String name, int amount) {
			return stack(name, amount, OreDictionary.WILDCARD_VALUE);
		}

		public ItemStack stackWildcard(String name) {
			return stack(name, 1, OreDictionary.WILDCARD_VALUE);
		}

		public ItemStack stack(String name) {
			Item item = this.item(name);
			if (item == null) {
				return ItemStack.EMPTY;
			}
			return new ItemStack(item);
		}

		public ItemStack stack(String name, int amount) {
			Item item = this.item(name);
			if (item == null) {
				return ItemStack.EMPTY;
			}
			return new ItemStack(item, amount);
		}

		public ItemStack stack(String name, int amount, int meta) {
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
		private ModNonnull(String id) {
			super(id);
		}

		@Override
		@Nonnull
		public Item item(String name) {
			Item item = super.item(name);
			if (item == null) {
				throw new RuntimeException("Item not found: " + this.id + ':' + name);
			}
			return item;
		}

		@Override
		@Nonnull
		public Block block(String name) {
			Block block = super.block(name);
			if (block == null) {
				throw new RuntimeException("Block not found: " + this.id + ':' + name);
			}
			return block;
		}

		@Override
		@Nonnull
		public ItemStack stack(String name) {
			Item item = this.item(name);
			return new ItemStack(item);
		}

		@Override
		@Nonnull
		public ItemStack stack(String name, int amount) {
			Item item = this.item(name);
			return new ItemStack(item, amount);
		}

		@Override
		@Nonnull
		public ItemStack stack(String name, int amount, int meta) {
			Item item = this.item(name);
			return new ItemStack(item, amount, meta);
		}
	}
}
