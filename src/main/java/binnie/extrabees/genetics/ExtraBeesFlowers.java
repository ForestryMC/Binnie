// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.genetics;

import forestry.api.apiculture.FlowerManager;
import forestry.api.genetics.IFlower;

import net.minecraft.item.Item;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import binnie.core.Mods;
import forestry.api.genetics.IFruitBearer;

import net.minecraft.world.IBlockAccess;
import net.minecraft.block.material.Material;

import forestry.api.genetics.IIndividual;

import java.util.EnumSet;

import net.minecraftforge.common.EnumPlantType;

import forestry.api.genetics.IPollinatable;

import net.minecraft.world.World;
import net.minecraft.init.Items;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import binnie.extrabees.ExtraBees;
import forestry.api.genetics.IAlleleFlowers;
import forestry.api.genetics.IFlowerProvider;

public enum ExtraBeesFlowers implements IFlowerProvider, IAlleleFlowers, IChromosomeType
{
	WATER,
	SUGAR,
	ROCK,
	BOOK,
	DEAD,
	REDSTONE,
	WOOD,
	LEAVES,
	Sapling,
	Fruit,
	Mystical;

	boolean dominant;

	private ExtraBeesFlowers() {
		this.dominant = true;
	}

	@Override
	public String getUID() {
		return "extrabees.flower." + this.toString().toLowerCase();
	}

	@Override
	public boolean isDominant() {
		return this.dominant;
	}

	@Override
	public IFlowerProvider getProvider() {
		return this;
	}

	@Override
	public String getDescription() {
		return ExtraBees.proxy.localise("flowers." + this.name().toString().toLowerCase() + ".name");
	}

	public void register() {
		// AlleleManager.alleleRegistry.registerAllele((IAllele)this);

		for (ItemStack stack : getItemStacks()) {
			FlowerManager.flowerRegistry.registerAcceptableFlower(Block.getBlockFromItem(stack.getItem()), getUID());
		}

		AlleleManager.alleleRegistry.registerAllele(this, this);
	}

	public static void doInit() {
		for (final ExtraBeesFlowers effect : values()) {
			effect.register();
		}
	}

	public ItemStack[] getItemStacks() {
		switch (this) {
		case WATER: {
			return new ItemStack[] { new ItemStack(Blocks.waterlily) };
		}
		case SUGAR: {
			return new ItemStack[] { new ItemStack(Blocks.reeds) };
		}
		case ROCK: {
			return new ItemStack[] { new ItemStack(Blocks.cobblestone) };
		}
		case BOOK: {
			return new ItemStack[] { new ItemStack(Blocks.bookshelf) };
		}
		case REDSTONE: {
			return new ItemStack[] { new ItemStack(Blocks.redstone_torch) };
		}
		case DEAD: {
			return new ItemStack[] { new ItemStack(Blocks.deadbush) };
		}
		case Fruit: {
			return new ItemStack[] { new ItemStack(Items.apple) };
		}
		case LEAVES: {
			return new ItemStack[] { new ItemStack(Blocks.leaves) };
		}
		case Sapling: {
			return new ItemStack[] { new ItemStack(Blocks.sapling) };
		}
		case WOOD: {
			return new ItemStack[] { new ItemStack(Blocks.log) };
		}
		default: {
			return new ItemStack[0];
		}
		}
	}

	@Override
	public boolean isAcceptedPollinatable(final World world, final IPollinatable pollinatable) {
		final EnumSet<EnumPlantType> types = pollinatable.getPlantType();
		return types.size() > 1 || !types.contains(EnumPlantType.Nether);
	}

	public boolean isAcceptedFlower(final World world, final IIndividual individual, final int x, final int y, final int z) {
		final Block block = world.getBlock(x, y, z);
		if (block == null) {
			return false;
		}
		switch (this) {
		case WATER: {
			return block == Blocks.waterlily;
		}
		case ROCK: {
			return block.getMaterial() == Material.rock;
		}
		case SUGAR: {
			return block == Blocks.reeds;
		}
		case BOOK: {
			return block == Blocks.bookshelf;
		}
		case REDSTONE: {
			return block == Blocks.redstone_torch;
		}
		case DEAD: {
			return block == Blocks.deadbush;
		}
		case WOOD: {
			return block.isWood(world, x, y, z);
		}
		case Fruit: {
			return world.getTileEntity(x, y, z) instanceof IFruitBearer;
		}
		case LEAVES: {
			return block.isLeaves(world, x, y, z);
		}
		case Sapling: {
			return block.getClass().getName().toLowerCase().contains("sapling");
		}
		case Mystical: {
			return block == Mods.Botania.block("flower");
		}
		default: {
			return false;
		}
		}
	}

	@Override
	public boolean growFlower(final World world, final IIndividual individual, final int x, final int y, final int z) {
		switch (this) {
		case WATER: {
			return world.isAirBlock(x, y, z) && world.getBlock(x, y - 1, z) == Blocks.water && world.setBlock(x, y, z, Blocks.waterlily, 0, 2);
		}
		case SUGAR: {
			return world.getBlock(x, y - 1, z) == Blocks.reeds && world.isAirBlock(x, y, z) && world.setBlock(x, y, z, Blocks.reeds, 0, 0);
		}
		default: {
			return false;
		}
		}
	}

	@Override
	public ItemStack[] affectProducts(final World world, final IIndividual individual, final int x, final int y, final int z, final ItemStack[] products) {
		if (this == ExtraBeesFlowers.Mystical) {
			final List<ItemStack> prods = new ArrayList<ItemStack>();
			for (final ItemStack stack : products) {
				prods.add(stack);
			}
			for (int k = 0; k < 50; ++k) {
				final int tX = 7;
				final int tY = 7;
				final int tZ = 3;
				final int x2 = x - tX + world.rand.nextInt(1 + 2 * tX);
				final int y2 = y - tY + world.rand.nextInt(1 + 2 * tY);
				final int z2 = z - tZ + world.rand.nextInt(1 + 2 * tZ);
				final Block block = world.getBlock(x2, y2, z2);
				if (block != null) {
					if (block == Mods.Botania.block("flower")) {
						final int meta = world.getBlockMetadata(x2, y2, z2);
						final Item item = Mods.Botania.item("petal");
						if (item != null) {
							prods.add(new ItemStack(item, 1, meta));
						}
					}
				}
			}
			return prods.toArray(new ItemStack[0]);
		}
		return products;
	}

	@Override
	public String getName() {
		return this.getDescription();
	}

	@Override
	public String getUnlocalizedName() {
		return this.getUID();
	}

	@Override
	public String getFlowerType() {
		return this.getUID();
	}

	@Override
	public Set<IFlower> getFlowers() {
		return new HashSet<IFlower>();
	}

	@Override
	public Class<? extends IAllele> getAlleleClass() {
		return getClass();
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return AlleleManager.alleleRegistry.getSpeciesRoot(getUID());
	}
}
